package ReactorEE.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import ReactorEE.Networking.HandshakeListener;
import ReactorEE.Networking.HandshakeRequest;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.swing.MultiplayerSelectionGUI;

public class HandshakeTest {

	/**
	 * Tests whether the handshaking protocol for starting a multiplier game completes successfully. 
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testHandshake() throws UnknownHostException, IOException, InterruptedException {
		int count = 0;
		while(SocketUtil.portTaken(SocketUtil.HANDSHAKE_PORT_NO)){
			count++;
			if(count > Integer.MAX_VALUE)
				fail();
		}
		
		HandshakeListener hsl = new HandshakeListener(new MultiplayerSelectionGUI());
		HandshakeRequest hsr = new HandshakeRequest(new MultiplayerSelectionGUI(), InetAddress.getLocalHost().getHostAddress());
		
		hsl.start();
		hsr.start();
		
		count = 0;
		while(hsl.isAlive()|| hsr.isAlive()){
			count++;
			if(count > Integer.MAX_VALUE)
				fail();
		}
		
		//After some time the listener is nolonger active.
		assertTrue("" + !hsl.isAlive() + " " + !hsr.isAlive(),!hsl.isAlive() && !hsr.isAlive());
	}

}
