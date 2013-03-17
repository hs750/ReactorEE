package ReactorEE.simulator;

import ReactorEE.swing.MainGUI;

/**
 * Step Looper provides the automatic stepping functionality to the game.
 * When started it will act as if the player is stepping though the game manually. 
 * 
 * @author Harrison
 */
public class StepLooper extends Thread{
	protected PlantController controller;
	protected MainGUI GUI;
	
	/**
	 * Initialise the the step loop with the objects needed to control the loop. 
	 * @param controller	The plantController for the current instance of the game.
	 * @param GUI			The GUI for the current instance of the game. 
	 */
	public StepLooper(PlantController controller, MainGUI GUI){
		this.controller = controller;
		this.GUI = GUI;
		GUI.updateGUI();
	}
	
	/**
	 * {@inheritDoc}
	 * Steps through the game every 500 milliseconds. If the isPaused() variable in the PlantController is set to true, the game will not step.
	 * If isGameOver() is true in the PlantController then the game loop will break and initiate the game over routine.
	 */
	public void run(){
		 try {
		        while (true) {
		        	if(!controller.getPlant().isPaused() & !controller.getPlant().isGameOver()){
		        		controller.step(1);
		        		GUI.updateGUI();
		        		
		        	}
		        	if(controller.getPlant().isGameOver()){
		        		GUI.endGameHandler();
		        		break;
		        	}
	        		Thread.sleep(500);
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		 
	}


	public MainGUI getGUI() {
		return GUI;
	}
}
