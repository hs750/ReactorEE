package ReactorEE.Networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import ReactorEE.simulator.GUIRefresher;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;
import ReactorEE.sound.Music;
import ReactorEE.swing.MultiplayerMainGUI;
import ReactorEE.swing.MultiplayerSelectionGUI;

public class HandshakeRequest extends Thread
{
	private MultiplayerSelectionGUI parent = null;
	private String HostIP;
	
	public HandshakeRequest(MultiplayerSelectionGUI parent, String HostIP){
		this.parent = parent;
		this.HostIP = HostIP;
	}
	
	@Override
	/**
	 * Handles the initial handshaking procedure with the other player's computer.
	 * Sends an expected message and waits for a reply. If the reply is also expected, 
	 * the game state listener is started and provided with the IP of the other player.
	 * The rest of the game is then initialised, and the connection closed. 
	 * @param HostIP The IP of the other computer, supplied from the user in the GUI.
	 */
	public void run()				
	{
		try{
			Socket socket = new Socket(InetAddress.getByName(HostIP), SocketUtil.HANDSHAKE_PORT_NO);
			SocketUtil.write(socket, "ANCHOVY");						
			PlantController plantController = new PlantController(new ReactorUtils());

			if(SocketUtil.readString(socket).equalsIgnoreCase("ANCHOVY FREE"))	
			{
				GamestateListener listen = new GamestateListener(HostIP, plantController);
				MultiplayerMainGUI view = new MultiplayerMainGUI(plantController,HostIP);
				//no step looper needed as stepping is synchronized through GamestateListener
				Music.changeGameContext("game");
				(new PlantController(new ReactorUtils())).setStepLooper(new GUIRefresher(plantController, view));
				listen.start();
				parent.close();
			}
			socket.close();	
		} catch (IOException e){
			e.printStackTrace();
		}
	}	
}
