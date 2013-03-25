package ReactorEE.Networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketUtil 
{	
	public static final int GAMESTATE_LISTENER_PORT_NO = 9004;
	public static final int SABOTAGE_LISTENER_PORT_NO = 9003;
	public static final int	HANDSHAKE_PORT_NO = 9002;
	/**
	 * A method to simplify reading messages received over a socket.
	 * @param socket the socket to read the message from.
	 * @return The String sent over the socket.
	 * @throws IOException 
	 */
	public static String readString(Socket socket) throws IOException
	{
		byte[] bytes = new byte[100];
		socket.getInputStream().read(bytes);
		return new String(bytes).trim();
	}
	/**
	 * A method to simplify reading messages received over a socket.
	 * @param socket the socket to read the message from.
	 * @return The byte array sent over the socket.
	 * @throws IOException 
	 */
	public static byte[] readBytes (Socket socket) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = socket.getInputStream();
		byte buffer[] = new byte[1024];
		for(int s; (s=in.read(buffer)) != -1; )
		{
		  baos.write(buffer, 0, s);
		}
		byte result[] = baos.toByteArray();
		return result;
	}
	
	/**
	 * A method to simplify sending messages over a socket.
	 * @param socket the socket to send the message to.
	 * @param message the message to be sent to the socket.
	 * @throws IOException 
	 */
	public static void write(Socket socket, String message) throws IOException
	{
		socket.getOutputStream().write(message.getBytes());
		socket.getOutputStream().flush();
	}
	/**
	 * A method to simplify sending messages over a socket.
	 * @param socket the socket to send the message to.
	 * @param message the message to be sent to the socket.
	 * @throws IOException 
	 */
	public static void write(Socket socket, byte[] message) throws IOException
	{
		socket.getOutputStream().write(message);
		socket.getOutputStream().flush();
	}
	/**
	 * Checks whether a port is available by attempting to open the supplied port. 
	 * Currently unused but may be implemented later if problems are encountered with ports being reserved.
	 * @param portNumber the port number to be checked, supplied as an integer.
	 * @return A boolean value expressing the port's availability.
	 */
	public static boolean portTaken(int portNumber)
	{
		boolean taken = false;
	    ServerSocket socket = null;
	    try 
	    {
	        socket = new ServerSocket(portNumber);
	    } 
	    catch (Exception e) 
	    {
	        taken = true;
	    } 
	    finally 
	    {
	        if (socket != null)
	        {
	            try 
	            {
	                socket.close();
	            } 
	            catch (Exception e) {}
	        }
	    }
	    return(taken);
	}
	
	/**
	 * Serialises object into an array of bytes
	 * @param object To Serialise
	 * @return	Byte array representing object
	 */
	public static byte[] toBypeArray(Serializable object){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		 byte[] bytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(object);
		  bytes = bos.toByteArray();			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		  try {
			out.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  
		}
		return bytes;
	}
	/**
	 * Unserialises an object from an array of bytes.
	 * @param object Byte array of object
	 * @return	Unserialised Object
	 * @throws IOException 
	 * @throws ClassNotFoundException, StreamCorruptedException 
	 */
	public static Object fromByteArray(byte[] object) throws IOException, ClassNotFoundException, StreamCorruptedException {
		ByteArrayInputStream bis = new ByteArrayInputStream(object);
		ObjectInput in = null;
		
		in = new ObjectInputStream(bis);
		Object o = in.readObject(); 
		bis.close();
		in.close();
		return o;
		
	}

	/**
	 * Validates a supplied IP against a regular expression representing the IPv4 format.
	 * @param ip A string that may represent a IPv4.
	 * @return True if ip is a valid IPv4, False otherwise.
	 */
	public static boolean validateIP(String ip)
	{
		Pattern pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
}
