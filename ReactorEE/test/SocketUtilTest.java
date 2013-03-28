package ReactorEE.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StreamCorruptedException;

import org.junit.Test;

import ReactorEE.Networking.SocketUtil;
import ReactorEE.model.Plant;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

public class SocketUtilTest {

	/**
	 * Tests whether an instance of a plant is serialized and then unserialized correctly, resulting in the initial and unserialized version of the plant are the same.
	 * 
	 */
	@Test
	public void testSerialisationAndDeserialisationOfPlant() throws StreamCorruptedException, ClassNotFoundException, IOException {
		ReactorUtils r = new ReactorUtils();
		PlantController pc = new PlantController(r);
		Plant p = pc.getPlant();
		
		byte[] ba = SocketUtil.toBypeArray(p);
		
		Plant p2 = (Plant) SocketUtil.fromByteArray(ba);
		
		assertEquals(p, p2);
	}
	
	/**
	 * Tests the validation of ip addresses. Valid and non valid ip addresses are tested.
	 */
	@Test
	public void testValidateIP(){
		boolean IPValid = false;
		IPValid = SocketUtil.validateIP("123.123.123.123");
		assertTrue(IPValid);
		
		IPValid = SocketUtil.validateIP("0.0.0.0");
		assertTrue(IPValid);
		
		IPValid = SocketUtil.validateIP("255.255.255.255");
		assertTrue(IPValid);
		
		IPValid = SocketUtil.validateIP("256.255.255.255");
		assertTrue(!IPValid);
		IPValid = SocketUtil.validateIP("255.256.255.255");
		assertTrue(!IPValid);
		IPValid = SocketUtil.validateIP("255.255.256.255");
		assertTrue(!IPValid);
		IPValid = SocketUtil.validateIP("255.255.255.256");
		assertTrue(!IPValid);
	}
	

}
