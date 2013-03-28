package ReactorEE.simulator;

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
		 try {
		        while (true) {
		        	if(!controller.getPlant().isPaused() & !controller.getPlant().isGameOver()){
		        		controller.step(1);
		        		GUI.updateGUI();
		        		if(listenerIP.startsWith("/"))
		        			listenerIP = listenerIP.substring(1, listenerIP.length());
		        		
		        		new Message().run(SocketUtil.toBypeArray(controller.getPlant()), listenerIP, SocketUtil.GAMESTATE_LISTENER_PORT_NO);
		        	}
		        	if(controller.getPlant().isGameOver()){
		        		GUI.endGameHandler();
		        		break;
		        	}
	        		Thread.sleep(waitPeriod);
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    } catch (Exception e) {
				e.printStackTrace();
			}
		 
	}

}
