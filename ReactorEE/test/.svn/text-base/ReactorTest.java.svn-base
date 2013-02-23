package ReactorEE.test;

import ReactorEE.simulator.*;
import ReactorEE.model.*;
import ReactorEE.pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReactorTest {

	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	private Reactor reactor;

	@Before
	public void setUp() {
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		plant = presenter.getPlant();
		reactor = plant.getReactor();
	}
	
	@Test
	public void testUpdateWaterVolume() {
		
		int waterVolume = reactor.getWaterVolume();
		
		reactor.updateWaterVolume(300);
		
		assertEquals("Result", waterVolume+300, reactor.getWaterVolume());
		
	}
	
	@Test
	public void testUpdateSteamVolume() {
		
		int steamVolume = reactor.getSteamVolume();
		
		reactor.updateSteamVolume(300);
		
		assertEquals("Result", steamVolume+300, reactor.getSteamVolume());
		
	}
	
	@Test
	public void testUpdatePressure() {
		
		int expectedPressure = (int) Math.round(new Double(reactor.getSteamVolume()) * 0.15);
		
		assertEquals("Result", expectedPressure, reactor.getPressure());
		
	}

}
