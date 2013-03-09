package ReactorEE.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;
import ReactorEE.simulator.StepLooper;
import ReactorEE.swing.MainGUI;

public class StepLooperTest {

	private ReactorUtils utils;
	private PlantController controller;
	private MainGUI gui;
	private StepLooper slooper;
	
	@Before
	public void setUp() throws Exception {
		
		utils = new ReactorUtils();
		controller = new PlantController(utils);
		gui = new MainGUI(controller);
		slooper = new StepLooper(controller, gui);
		controller.setStepLooper(slooper);
	}

	/**
	 * Tests whether the step looper causes the game to step correctly. 
	 * Starts the Step Looper then waits for 5000 milliseconds, then checks the plant to see whether 10 steps have occurred. 
	 */
	@Test
	public void testStepping() throws InterruptedException {
		if(controller.getPlant().isPaused())
			controller.togglePaused();
		
		Thread.sleep(5000);
		
		controller.togglePaused();
		
		assertEquals(10, controller.getPlant().getTimeStepsUsed());
	
	}
	
	

}
