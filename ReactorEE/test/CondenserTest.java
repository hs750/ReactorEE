package ReactorEE.test;

import ReactorEE.simulator.*;
import ReactorEE.model.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import ReactorEE.pcomponents.Condenser;

public class CondenserTest {

	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	private Condenser condenser;

	@Before
	public void setUp() {
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		plant = presenter.getPlant();
		
		condenser = plant.getCondenser();
	}
	
	@Test
	public void testUpdateWaterVolume() {
		
		int currentWaterVolume = condenser.getWaterVolume();
		
		condenser.updateWaterVolume(300);
		
		assertEquals("Result", (currentWaterVolume+300), condenser.getWaterVolume());
		
	}
		
	@Test
	public void testUpdateSteamVolume() {
		
		int currentSteamVolume = condenser.getSteamVolume();
		
		condenser.updateSteamVolume(300);
		
		assertEquals("Result", (currentSteamVolume+300), condenser.getSteamVolume());
		
	}

}
