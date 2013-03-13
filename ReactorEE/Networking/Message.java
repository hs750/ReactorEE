package ReactorEE.Networking;

import java.net.InetAddress;
import java.net.Socket;

public class Message 
{
	/**
	 * Sends a string message to the described port.
	 * @param message The String to be passed expressing the hardware failure to be incurred.
	 * @param listenerIP The IP address of the other player's computer.
	 * @param portNo The port number to connect to.
	 */
	public void run(String message, String listenerIP, int portNo) throws Exception 
	{
		Socket socket = new Socket(InetAddress.getByName(listenerIP), portNo);
		SocketUtil.write(socket, message);			
		socket.close();												
	}
}
