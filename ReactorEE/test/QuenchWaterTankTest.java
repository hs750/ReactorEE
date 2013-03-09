package ReactorEE.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ReactorEE.pcomponents.Reactor;

public class QuenchWaterTankTest {
	Reactor reactor;

	@Before
	public void setUp() throws Exception {
		reactor = new Reactor();
	}

	/**
	 * Tests whether the isQuenchable() value is true when it should be.
	 * More precisely that it is true when the temperature of the reactor is at the max reactor temperature, and that it is not true at other values.
	 */
	@Test
	public void testQuenchable() {
		reactor.setTemperature(100);
		assertTrue(!reactor.isQuenchable());
		
		reactor.setTemperature(reactor.getMaxTemperature());
		assertTrue(reactor.isQuenchable());
	}
	
	/**
	 * Tests whether quenching the reactor has the desired affect of cooling it down and increasing its water level.
	 * Also tests that the reactor cant be quenched again after it has already been used once.
	 */
	@Test
	public void testQuench(){
		reactor.setTemperature(reactor.getMaxTemperature());
		int currentWaterVol = reactor.getWaterVolume();
		reactor.quench();
		
		assertEquals(50, reactor.getTemperature());
		assertEquals(currentWaterVol + 1000, reactor.getWaterVolume());
		assertTrue(!reactor.isQuenchable());
		
		reactor.setTemperature(reactor.getMaxTemperature());
		assertTrue(!reactor.isQuenchable());
		
	}

}
