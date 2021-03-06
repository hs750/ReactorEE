package ReactorEE.test;


import java.io.File;
import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;


import org.junit.Before;

import org.junit.Test;

import ReactorEE.model.*;
import ReactorEE.pcomponents.*;
import ReactorEE.simulator.*;
import ReactorEE.simulator.PlantController.falableComponents;


public class PlantControllerTest {
	
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;

	@Before
	public void setUp() {
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		presenter.newGame("Bob");
		plant = presenter.getPlant();
	}
	
	@Test
	public void testLoadGame() {
		
		File f = new File("save.ser");
		if(f.exists()) {
			f.delete();
		}
		
		// no saved game file so should return false
		assertEquals("Result", false, presenter.loadGame());
		
		presenter.saveGame();
		
		assertEquals("Result", true, presenter.loadGame());
		
	}
	
	@Test
	public void testOperatorName() {
		
		assertEquals("Result", "Bob", this.plant.getOperatorName());
		
	}
	
	@Test
	public void testTogglePaused() {
		
		boolean isPaused = plant.isPaused();
		
		presenter.togglePaused();
		
		assertEquals("Result", !isPaused, plant.isPaused());
		
	}
	
	@Test
	public void testAddHighScore() {
		
		plant.setHighScores(new ArrayList<HighScore>());
		
		HighScore newHighScore = new HighScore("Bob", 2000);
		
		presenter.addHighScore(newHighScore);
		
		List<HighScore> highScores = plant.getHighScores();
		
		//expected
		List<HighScore> expected = new ArrayList<HighScore>();
		expected.add(newHighScore);
		
		assertEquals("Result", expected, highScores);
		
	}
	
	@Test
	public void testSetValve() {
		
		presenter.setValve(1, false);
		
		List<Valve> valves = plant.getValves();
		
		assertEquals("Result", false, valves.get(0).isOpen());
		
	}
	
	@Test
	public void testSetPumpOnOff() {
		
		presenter.setPumpOnOff(1, false);
		
		List<Pump> pumps = plant.getPumps();
		
		assertEquals("Result", false, pumps.get(0).isOn());
		
	}
	
	@Test
	public void testSetPumpRpm() {
		
		presenter.setPumpRpm(1, 127);
		
		List<Pump> pumps = plant.getPumps();
		
		assertEquals("Result", 127, pumps.get(0).getRpm());
		
	}
	
	@Test
	public void testSetControlRods() {
		
		presenter.setControlRods(57);
		
		assertEquals("Result", 57, plant.getReactor().getPercentageLowered());
		
	}
	//4.4 4.8
	@Test
	public void testRepairTurbine() {
		
		assertEquals("Result", false, presenter.repairTurbine()); // the turbine hasn't failed so repairTurbine() should return false
		
		// break the turbine
		List<PlantComponent> failedComponents = plant.getFailedComponents();
		failedComponents.add(plant.getTurbine());
		
		assertEquals("Result", true, presenter.repairTurbine()); // the turbine is now broken so repairTurbine() should return true
		
		assertEquals("Result", false, presenter.repairTurbine()); // the turbine is already being repaired so repairTurbine() should return false again
		
	}
	//4.4 4.8
	@Test
	public void testRepairPump() {
		
		assertEquals("Result", false, presenter.repairPump(1));
		
		// break the pump
		List<PlantComponent> failedComponents = plant.getFailedComponents();
		failedComponents.add(plant.getPumps().get(0));
		
		assertEquals("Result", true, presenter.repairPump(1));
		
		assertEquals("Result", false, presenter.repairPump(1));
		
	}
	
	//------------------- Tests of A4 added functionality ------------------------
	/**
	 * Tests whether setting each breakable component within the power plant has the affect of adding it to the list of broken components and that it's isOperational() returns false.
	 */
	@Test
	public void testSetComponentFailed(){
		//Test Operator Software manual break
		presenter.setComponentFailed(falableComponents.OperatorSoftare);
		assertTrue(plant.getFailedComponents().contains(plant.getOperatingSoftware()));
		assertTrue(!plant.getOperatingSoftware().isOperational());

		//Test Pump1 manual break
		presenter.setComponentFailed(falableComponents.Pump1);
		assertTrue(plant.getFailedComponents().contains(plant.getPumps().get(0)));
		assertTrue(!plant.getPumps().get(0).isOperational());

		//Test Pump2 manual break
		presenter.setComponentFailed(falableComponents.Pump2);
		assertTrue(plant.getFailedComponents().contains(plant.getPumps().get(1)));
		assertTrue(!plant.getPumps().get(1).isOperational());

		//Test Pump3 manual break
		presenter.setComponentFailed(falableComponents.Pump3);
		assertTrue(plant.getFailedComponents().contains(plant.getPumps().get(2)));
		assertTrue(!plant.getPumps().get(2).isOperational());

		//Test Turbine manual break
		presenter.setComponentFailed(falableComponents.Turbine);
		assertTrue(plant.getFailedComponents().contains(plant.getTurbine()));
		assertTrue(!plant.getTurbine().isOperational());
	}
	/**
	 * Tests whether parseSabotageCommand() correctly returns the component to sabotage from the given string containing the name of the component.
	 */
	@Test
	public void testParseSabotageCommand(){
		assertEquals(falableComponents.Pump1, presenter.parseSabotageCommand("pump1"));
		assertEquals(falableComponents.Pump2, presenter.parseSabotageCommand("pump2"));
		assertEquals(falableComponents.Pump3, presenter.parseSabotageCommand("pump3"));
		assertEquals(falableComponents.Turbine, presenter.parseSabotageCommand("Turbine"));
		assertEquals(falableComponents.OperatorSoftare, presenter.parseSabotageCommand("Operator Software"));
		assertEquals(falableComponents.other, presenter.parseSabotageCommand("asefawg24gawg23gq24gq"));
		
	}
}
