package ReactorEE.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ReactorEE.simulator.GUIRefresher;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;
import ReactorEE.simulator.StepLooper;
import ReactorEE.swing.MainGUI;
import ReactorEE.swing.MultiplayerMainGUI;

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
		
		Thread.sleep(slooper.getWaitPeriod() * 10);
		
		controller.togglePaused();
		
		assertEquals(10, controller.getPlant().getTimeStepsUsed());
	
	}
	
	/**
	 * Tests whether Sabotages for player two in multi player are correctly given and used during gameplay.
	 * @throws InterruptedException
	 */
	@Test
	public void testSabotageLimit() throws InterruptedException{
		gui = new MultiplayerMainGUI(controller, "127.0.0.1");
		GUIRefresher guir = new GUIRefresher(controller, gui);
		controller.setStepLooper(guir);
		assertEquals(0,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(1,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(2,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(3,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(3,guir.getNumberOfAvailableSabotages());
		
		guir.useSabo();
		assertEquals(2,guir.getNumberOfAvailableSabotages());
		
		guir.useSabo();
		assertEquals(1,guir.getNumberOfAvailableSabotages());
		
		guir.useSabo();
		assertEquals(0,guir.getNumberOfAvailableSabotages());
		
		guir.useSabo();
		assertEquals(0,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(1,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(2,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(3,guir.getNumberOfAvailableSabotages());
		
		controller.step(10);
		assertEquals(3,guir.getNumberOfAvailableSabotages());
	}

}
