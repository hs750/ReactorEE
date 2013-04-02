package ReactorEE.simulator;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;

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
		 try {
		        while (true) {
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
		        			throw new IOException("Unable to Connect to player two");
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
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
		 
	}

}
