package ReactorEE.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import ReactorEE.Networking.SocketUtil;
import ReactorEE.model.Plant;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

public class SocketUtilTest {
	
	/**
	 * Tests whether a string sent over a socket using SocketUtil.wright() is read from the socket by the method SocketUtil.readString() correctly.
	 * @throws IOException
	 */
	@Test
	public void testWriteAndReadString() throws IOException{
		final String message = "Hello Testing World!";
		ServerSocket ss = new ServerSocket(9000);
		
		class Sender extends Thread{
			public void run(){
				try {	Thread.sleep(10);	} catch (InterruptedException e) {e.printStackTrace();}
				try {
					Socket s1 = new Socket(InetAddress.getLocalHost(), 9000);
					SocketUtil.write(s1, message);
					s1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		new Sender().run();
		Socket s = ss.accept();
		
		assertEquals(message, SocketUtil.readString(s));
		
		s.close();
		ss.close();
		
	}
	
	/**
	 * Tests whether a byte array written to a socket using SocketUitl.write() is read correctly from the socket using SocketUtil.readBytes(). 
	 * @throws IOException
	 */
	@Test
	public void testWriteAndReadBytes() throws IOException{
		final byte[] message = "Hello Testing World!".getBytes();
		ServerSocket ss = new ServerSocket(9000);
		
		class Sender extends Thread{
			public void run(){
				try {	Thread.sleep(10);	} catch (InterruptedException e) {e.printStackTrace();}
				try {
					Socket s1 = new Socket(InetAddress.getLocalHost(), 9000);
					SocketUtil.write(s1, message);
					s1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		new Sender().run();
		Socket s = ss.accept();
		
		byte[] recieved = SocketUtil.readBytes(s);
		for(int i = 0; i < message.length; i++){
			assertEquals(message[i], recieved[i]);
		}
		
		
		s.close();
		ss.close();
		
	}

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
