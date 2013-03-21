package ReactorEE.Networking;

import java.net.InetAddress;
import java.net.Socket;

import ReactorEE.simulator.GUIRefresher;
import ReactorEE.simulator.MultiplayerStepLooper;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;
import ReactorEE.simulator.StepLooper;
import ReactorEE.swing.MainGUI;
import ReactorEE.swing.MultiplayerMainGUI;

public class HandshakeRequest 
{
	/**
	 * Handles the initial handshaking procedure with the other player's computer.
	 * Sends an expected message and waits for a reply. If the reply is also expected, 
	 * the game state listener is started and provided with the IP of the other player.
	 * The rest of the game is then initialised, and the connection closed. 
	 * @param HostIP The IP of the other computer, supplied from the user in the GUI.
	 */
	public void run(String HostIP, PlantController plantController) throws Exception 				
	{
		Socket socket = new Socket(InetAddress.getByName(HostIP), 9002);
		SocketUtil.write(socket, "ANCHOVY");						
		
		if(SocketUtil.readString(socket).equalsIgnoreCase("ANCHOVY FREE"))	
		{
			GamestateListener listen = new GamestateListener(HostIP, plantController);
			listen.start();	
			
			//TODO Initialise Sabotage Classes (GUI etc)
			PlantController controller = new PlantController(new ReactorUtils());
			MultiplayerMainGUI view = new MultiplayerMainGUI(plantController,HostIP);//TODO Pass HostIP as argument to set method in class responsible for calling SabotageRequest()
			//no step looper needed for the second player as stepping is synchronized by the reception of messages containing the plant info.
			controller.setStepLooper(new GUIRefresher(controller, view));
		}
		socket.close();												
	}	
}
