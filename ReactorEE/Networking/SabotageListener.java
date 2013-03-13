package Networking;

import java.net.ServerSocket;
import java.net.Socket;

public class SabotageListener extends Thread
{
	private String consumerIP;
	
	/**
	 * Constructor Method storing the IP of the second machine for later validation of incoming messages.
	 * @param clientIP The IP to accept messages from.
	 */
	public SabotageListener(String clientIP)
	{
		this.consumerIP = clientIP;
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
			ServerSocket serverSocket = new ServerSocket(9003);
			boolean close = false;
			
			while (close == false) 
			{
				Socket socket = serverSocket.accept();								 
				if (consumerIP.equalsIgnoreCase(socket.getInetAddress().toString())) 
				{
					String message = SocketUtil.read(socket);	
					if(message.equalsIgnoreCase("ANCHOVY KILL"))
					{
						close = true;
					}
					else
					{
						//TODO Call sabotage parser with argument (message)
					}
				}
				else
				{
					SocketUtil.write(socket, "REJECTED");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}