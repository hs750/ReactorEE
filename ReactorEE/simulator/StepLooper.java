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
	protected int waitPeriod = 500;
	protected boolean running = true;
	
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
		        while (running) {
		        	if(!controller.getPlant().isPaused() & !controller.getPlant().isGameOver()){
		        		controller.step(1);
		        		GUI.updateGUI();
		        		
		        	}
		        	if(controller.getPlant().isGameOver()){
		        		GUI.endGameHandler();
		        		break;
		        	}
	        		Thread.sleep(waitPeriod);
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		 
	}

	/**
	 * @return The GUI that the step looper is running with.
	 */
	public MainGUI getGUI() {
		return GUI;
	}

	/**
	 * @return The amount of time the loop is asleep between iterations
	 */
	public int getWaitPeriod() {
		return waitPeriod;
	}
	
	/**
	 * Stop the step looper from running it's loop.
	 */
	public void stopLoop(){
		running = false;
	}

}
