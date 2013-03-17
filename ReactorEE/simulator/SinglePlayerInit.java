package ReactorEE.simulator;

import ReactorEE.swing.MainGUI;

/**
 * Initializes all the features needed to play a single player game.
 * @author Harrison
 *
 */
public class SinglePlayerInit {

	private PlantController controller;
	private ReactorUtils utils;
	private MainGUI view;
	
	public SinglePlayerInit() {
		utils = new ReactorUtils();
		controller = new PlantController(utils);
		view = new MainGUI(controller);
		
		//Give the game the step looper to enable the game to automatically step. Must be done like this as gui and controller are needed to initialise StepLooper.
		controller.setStepLooper(new StepLooper(controller, view));
	}
}
