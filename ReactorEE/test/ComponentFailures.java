package ReactorEE.test;

import static org.junit.Assert.*;


import org.junit.Test;


import org.junit.Before;

import ReactorEE.model.*;
import ReactorEE.pcomponents.*;
import ReactorEE.simulator.*;



public class ComponentFailures {

	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	private Turbine turbine;
	private Condenser condenser;
	private Reactor reactor;
	
	
	@Before
	public void setUp() {
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		presenter.newGame("Bob");
		plant = presenter.getPlant();
		reactor = presenter.getPlant().getReactor();
		turbine = presenter.getPlant().getTurbine();
		condenser = presenter.getPlant().getCondenser();
	}
	
	//13.1
	@Test 
	public void reactorFail(){
		reactor.setTemperature(3000);
		reactor.setPressure(2500);
		presenter.step(1);
		assertEquals(90, reactor.getHealth());
	}

	//13.1
	@Test
	public void condenserFail(){
		condenser.setTemperature(3000);
		condenser.setPressure(2500);
		presenter.step(1);
		assertEquals(90, condenser.getHealth());
	}
	
	//6.2
	@Test
	public void turbineFail(){
	   turbine.setRpm(10);	
	   plant.addFailedComponent(turbine);
	   presenter.step(1);
	   assertEquals(0, turbine.getRpm());
	}
	
	//-------------- A4 Tests -----------------------
	
	/**
	 * Tests whether reducing the failure rate of a pump results in it taking more steps for the pump to fail than a standard pump.
	 */
	@Test
	public void testPumpReducedFaulureRate(){
		Pump p1 = presenter.getPlant().getPumps().get(0);
		Pump p2 = presenter.getPlant().getPumps().get(1);
		
		p2.setFailureRate(1);
		p2.setMaxFailureRate(1);
		
		int p1FailTime = 0;
		int p2FailTime = 0;
		
		while(p2.isOperational()){
			if(p1.isOperational())
				p1FailTime++;
			p2FailTime++;
			presenter.step(1);
		}
		
		assertTrue(""+p1FailTime + " /< " + p2FailTime, p1FailTime < p2FailTime);
		
	}
	
	/**
	 * Tests whether reducing the failure rate of a turbine increases the number of steps needed to result in a turbine failing.
	 */
	@Test
	public void testTurbineReducedFaulureRate(){
		Turbine t1 = turbine;
		PlantController pc2 = new PlantController(new ReactorUtils());
		Turbine t2 = pc2.getPlant().getTurbine();
		
		t2.setFailureRate(1);
		t2.setMaxFailureRate(1);
		
		int t1FailTime = 0;
		int t2FailTime = 0;
		
		while(t2.isOperational()){
			if(t1.isOperational())
				t1FailTime++;
			t2FailTime++;
			presenter.step(1);
			pc2.step(1);
		}
		
		assertTrue(""+t1FailTime + " /< " + t2FailTime, t1FailTime < t2FailTime);
		
	}
	
	/**
	 * Tests whether reducing the failure rate of an Operator Software increases the number of steps required for the Operator Software to fail.
	 */
	@Test
	public void testOperatorSoftwareReducedFaulureRate(){
		OperatingSoftware o1 = presenter.getPlant().getOperatingSoftware();
		PlantController pc2 = new PlantController(new ReactorUtils());
		OperatingSoftware o2 = pc2.getPlant().getOperatingSoftware();
		
		o2.setFailureRate(1);
		o2.setMaxFailureRate(1);
		
		int o1FailTime = 0;
		int o2FailTime = 0;
		
		while(o2.isOperational()){
			if(o1.isOperational())
				o1FailTime++;
			o2FailTime++;
			presenter.step(1);
			pc2.step(1);
		}
		
		assertTrue(""+o1FailTime + " /< " + o2FailTime, o1FailTime < o2FailTime);
		
	}
}
