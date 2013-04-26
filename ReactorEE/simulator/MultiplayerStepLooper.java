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
		boolean gameOver = false;
		int connectionAttempts = 0;
		
		class GameConnectionException extends IOException{
			private static final long serialVersionUID = 1L;
			public GameConnectionException(String string) {
				super(string);
			}}
		
		 try {
		        while (running) {
		        	if(!controller.getPlant().isPaused() & !controller.getPlant().isGameOver()){
		        		controller.step(1);
		        		GUI.updateGUI();
		        	}
		        	if(listenerIP.startsWith("/"))
	        			listenerIP = listenerIP.substring(1, listenerIP.length());
		        	
		        	try{
		        		new Message().run(SocketUtil.toBypeArray(controller.getPlant()), listenerIP, SocketUtil.GAMESTATE_LISTENER_PORT_NO);
		        		connectionAttempts = 0;
		        	}catch(IOException ce){
		        		connectionAttempts++;
		        		if(connectionAttempts > 2)
		        			throw new GameConnectionException("Unable to Connect to Saboteur");
		        	}
		        	
		        	if(controller.getPlant().isGameOver()){
		        		if(!gameOver){
		        			GUI.endGameHandler();
		        			gameOver = true;
		        		}
		        		
		        	}
		        	if(gameOver && !controller.getPlant().isGameOver()){
	        			gameOver = false;
	        		}
	        		Thread.sleep(waitPeriod);
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
			} catch (GameConnectionException gce){
				gce.printStackTrace();
				JOptionPane.showMessageDialog(GUI.getFrame(), "Unable to connect to Saboteur", "Connection Error", JOptionPane.ERROR_MESSAGE);
			}
		 
	}

}
