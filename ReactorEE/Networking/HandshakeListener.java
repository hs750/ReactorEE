package ReactorEE.Networking;

import java.net.ServerSocket;
import java.net.Socket;

import ReactorEE.simulator.MultiplayerStepLooper;
import ReactorEE.simulator.PlantController;
import ReactorEE.swing.MainGUI;

public class HandshakeListener 
{	
	/**
	 * Handles the initial handshaking procedure with the other player's computer.
	 * Waits for another player to send a message, once a message is received it is validated
	 * against an expected string. If correct, the IP of the connected computer is recorded for future
	 * message validation. The sabotage listener is started and provided with the IP of the other player.
	 *  The rest of the game is then initialised, the listening loop exited and the connection closed.
	 *  @param plantController PlantController for the game, needed once the connection has been established to allow interaction with the rest of the game.
	 */
	public void run(PlantController plantController) throws Exception 
	{
		ServerSocket serverSocket = new ServerSocket(9002);
		String commitedIP;
		boolean close = false;
		
		while (close == false) 
		{
			Socket socket = serverSocket.accept();		
			String message = SocketUtil.readString(socket);
			if(message.equalsIgnoreCase("ANCHOVY"))	
			{
				commitedIP = socket.getInetAddress().toString();		
				Thread listen = new Thread(new SabotageListener(commitedIP, plantController));
				listen.start();								
				SocketUtil.write(socket, "ANCHOVY FREE");	
		
				//TODO Start Operator's GameEngine (gameInit)
				MainGUI view = new MainGUI(plantController);
				plantController.setStepLooper(new MultiplayerStepLooper(plantController, view, commitedIP));
				
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
		serverSocket.close();
	}
}
