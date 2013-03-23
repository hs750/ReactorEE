package ReactorEE.Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Message 
{
	/**
	 * Sends a string message to the described port.
	 * @param message The String to be passed expressing the plant/hardware failure to be incurred.
	 * @param listenerIP The IP address of the other player's computer.
	 * @param portNo The port number to connect to.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void run(String message, String listenerIP, int portNo) throws UnknownHostException, IOException
	{
		Socket socket = new Socket(InetAddress.getByName(listenerIP), portNo);
		SocketUtil.write(socket, message);			
		socket.close();												
	}
	/**
	 * Sends a byte array message to the described port. Intended for serialised Classes.
	 * @param message The byte array to be passed expressing the serialised plant to be reproduced.
	 * @param listenerIP The IP address of the other player's computer.
	 * @param portNo The port number to connect to.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void run(byte[] message, String listenerIP, int portNo) throws UnknownHostException, IOException
	{
		Socket socket = new Socket(InetAddress.getByName(listenerIP), portNo);
		SocketUtil.write(socket, message);			
		socket.close();												
	}
}
