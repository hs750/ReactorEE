package Networking;

import java.net.ServerSocket;
import java.net.Socket;

import Tests2.SabotageListenerThread;

public class HandshakeListener 
{	
	/**
	 * Handles the initial handshaking procedure with the other player's computer.
	 * Waits for another player to send a message, once a message is received it is validated
	 * against an expected string. If correct, the IP of the connected computer is recorded for future
	 * message validation. The sabotage listener is started and provided with the IP of the other player.
	 *  The rest of the game is then initialised, the listening loop exited and the connection closed.
	 */
	public void run() throws Exception 
	{
		ServerSocket serverSocket = new ServerSocket(9002);
		String commitedIP;
		boolean close = false;
		
		while (close == false) 
		{
			Socket socket = serverSocket.accept();		
			String message = SocketUtil.read(socket);
			if(message.equalsIgnoreCase("ANCHOVY"))	
			{
				commitedIP = socket.getInetAddress().toString();		
				Thread listen = new Thread(new SabotageListenerThread(commitedIP));
				listen.start();								
				SocketUtil.write(socket, "ANCHOVY FREE");	
				//TODO Start Operator's GameEngine (gameInit)
				//TODO Pass commitedIP as argument to set method in class responsible for calling HandshakeRequest()
				socket.getOutputStream().close();					
				close = true;				
			}
			else
			{
				SocketUtil.write(socket, "REJECTED");	
				socket.getOutputStream().close();
			}
		}
	}
}
