package Networking;

import java.net.InetAddress;
import java.net.Socket;

public class HandshakeRequest 
{
	/**
	 * Handles the initial handshaking procedure with the other player's computer.
	 * Sends an expected message and waits for a reply. If the reply is also expected, 
	 * the game state listener is started and provided with the IP of the other player.
	 * The rest of the game is then initialised, and the connection closed. 
	 * @param HostIP The IP of the other computer, supplied from the user in the GUI.
	 */
	public void run(String HostIP) throws Exception 				
	{
		Socket socket = new Socket(InetAddress.getByName(HostIP), 9002);
		SocketUtil.write(socket, "ANCHOVY");							
		
		if(SocketUtil.read(socket).equalsIgnoreCase("ANCHOVY FREE"))	
		{
			Thread listen = new Thread(new GamestateListener(HostIP));
			listen.start();												
			//TODO Initialise Sabotage Classes (GUI etc)
			//TODO Pass HostIP as argument to set method in class responsible for calling SabotageRequest()
		}
		socket.close();												
	}	
}