package ReactorEE.Networking;

import java.io.StreamCorruptedException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import ReactorEE.model.Plant;
import ReactorEE.simulator.PlantController;

public class GamestateListener extends Thread
{
	private String consumerIP;
	private PlantController plantController;
	/**
	 * Constructor Method storing the IP of the second machine for later validation of incoming messages.
	 * @param hostIP The IP to accept messages from.
	 * @param pc PlantController of the game, needed for interaction with the rest of the game, allows plant sent across network to be saved into the game.
	 */
	public GamestateListener(String hostIP, PlantController pc)
	{
		this.consumerIP = hostIP;
		this.plantController = pc;
	}
	
	/**
	 * Listens to the allocated port for incoming messages describing the game state to forward for resolution. 
	 * Messages from unexpected IPs are ignored. If the message reads "ANCHOVY KILL" and is from a valid IP, the
	 * listening loop is exited. This frees the port and allows for new two player games.
	 */
	public void run() 
	{
		try 
		{
			boolean close = false;
			ServerSocket serverSocket = new ServerSocket(SocketUtil.GAMESTATE_LISTENER_PORT_NO);
			while (close == false) 																
			{
				Socket socket = serverSocket.accept();
				if(consumerIP.equalsIgnoreCase(socket.getInetAddress().toString().substring(1)) || socket.getInetAddress().toString().substring(1).equals(Inet4Address.getLocalHost().getHostAddress())) 	
				{
					byte[] message = SocketUtil.readBytes(socket);	
					if(new String(message).trim().equalsIgnoreCase("ANCHOVY KILL"))
					{
						close = true;
					}
					else
					{
						Plant p = null;
						try{
							p = (Plant) SocketUtil.fromByteArray(message);
						}catch(StreamCorruptedException sce){
							sce.printStackTrace();
						}
						plantController.setPlant(p);
					}
				}
				else
				{
					SocketUtil.write(socket, "REJECTED");		
				}
			}
			serverSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
