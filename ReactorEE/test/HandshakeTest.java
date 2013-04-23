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
		HandshakeRequest hsr = new HandshakeRequest(new MultiplayerSelectionGUI());
		
		hsl.start();
		hsr.run(InetAddress.getLocalHost().getHostAddress());
		Thread.sleep(50);
		
		assertTrue(!hsl.isAlive());
	}

}
