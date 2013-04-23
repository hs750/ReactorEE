package ReactorEE.Networking;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import ReactorEE.simulator.PlantController;

public class SabotageListener extends Thread
{
	private String consumerIP;
	private PlantController plantController;
	
	/**
	 * Constructor Method storing the IP of the second machine for later validation of incoming messages.
	 * @param clientIP The IP to accept messages from.
	 * @param pc PlantController allowing interaction with the rest of the game, allows manual breaking of components to happen.
	 */
	public SabotageListener(String clientIP, PlantController pc)
	{
		this.consumerIP = clientIP;
		this.plantController = pc;
	}
	
	/**
	 * Listens to the allocated port for incoming messages regarding sabotage to forward for resolution. 
	 * Messages from unexpected IPs are ignored. If the message reads "ANCHOVY KILL" and is from a valid IP, the
	 * listening loop is exited. This frees the port and allows for new two player games.
	 */
	public void run()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(SocketUtil.SABOTAGE_LISTENER_PORT_NO);
			boolean close = false;
			
			while (close == false) 
			{
				Socket socket = serverSocket.accept();								 
				if (consumerIP.equalsIgnoreCase(socket.getInetAddress().toString()) || socket.getInetAddress().toString().substring(1).equals(Inet4Address.getLocalHost().getHostAddress())) 
				{
					String message = SocketUtil.readString(socket);	
					if(message.equalsIgnoreCase("ANCHOVY KILL"))
					{
						close = true;
					}
					else
					{
						plantController.setComponentFailed(plantController.parseSabotageCommand(message));
					}
				}
				else
				{
					SocketUtil.write(socket, "REJECTED");
				}
			}
			serverSocket.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
