package ReactorEE.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import ReactorEE.Networking.HandshakeListener;
import ReactorEE.Networking.HandshakeRequest;
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
		HandshakeListener hsl = new HandshakeListener(new MultiplayerSelectionGUI());
		HandshakeRequest hsr = new HandshakeRequest(new MultiplayerSelectionGUI(), InetAddress.getLocalHost().getHostAddress());
		
		hsl.start();
		Thread.sleep(500);
		hsr.start();
		Thread.sleep(500);
		
		//After some time the listener is nolonger active.
		assertTrue(!hsl.isAlive());
	}

}
