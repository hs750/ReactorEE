package ReactorEE.simulator;

import java.io.IOException;

import javax.swing.JOptionPane;

import ReactorEE.Networking.Message;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.swing.MainGUI;

public class MultiplayerStepLooper extends StepLooper {
	private String listenerIP;
	
	
	public MultiplayerStepLooper(PlantController controller, MainGUI GUI, String listenerIP) {
		super(controller, GUI);
		this.listenerIP = listenerIP;
	}
	
	/**
	 * {@inheritDoc}
	 * Additionally sends a packet containing the plant over the network on every iteration.
	 */
	@Override
	public void run(){
		boolean gameOver = false;	//indicates whether gameover has occured recently - used to stop game over routine being run more than once at the end of a game.
		int connectionAttempts = 0;
		
		/**
		 * exception used when the connection to the second player is lost. 
		 */
		class GameConnectionException extends IOException{
			private static final long serialVersionUID = 1L;
			public GameConnectionException(String string) {
				super(string);
			}}
		
		 try {
		        while (running) {
		        	//Step the game forward one step
		        	if(!controller.getPlant().isPaused() & !controller.getPlant().isGameOver()){
		        		controller.step(1);
		        		GUI.updateGUI();
		        	}
		        	// remove leading / which is part of IP.
		        	if(listenerIP.startsWith("/"))
	        			listenerIP = listenerIP.substring(1, listenerIP.length());
		        	
		        	try{
		        		//Try to send byte array of plant over the network to player 2.
		        		new Message().run(SocketUtil.toBypeArray(controller.getPlant()), listenerIP, SocketUtil.GAMESTATE_LISTENER_PORT_NO);
		        		connectionAttempts = 0;
		        	}catch(IOException ce){
		        		//If was unable to send plant, increase connection attempt counter.
		        		//If 3+ connection attempts, through a connection exception 
		        		connectionAttempts++;
		        		if(connectionAttempts > 2)
		        			throw new GameConnectionException("Unable to Connect to Saboteur");
		        	}
		        	
		        	//End the game if game state == game over
		        	if(controller.getPlant().isGameOver()){
		        		if(!gameOver){
		        			GUI.endGameHandler();
		        			gameOver = true;
		        		}
		        		
		        	}
		        	
		        	//reset game over state.
		        	if(gameOver && !controller.getPlant().isGameOver()){
	        			gameOver = false;
	        		}
	        		Thread.sleep(waitPeriod);
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
			} catch (GameConnectionException gce){
				gce.printStackTrace();
				//Alert user that the connection was lost.
				JOptionPane.showMessageDialog(GUI.getFrame(), "Unable to connect to Saboteur", "Connection Error", JOptionPane.ERROR_MESSAGE);
			}
		 
	}

}
