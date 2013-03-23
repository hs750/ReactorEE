package ReactorEE.Networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketUtil 
{	
	public static final int GAMESTATE_LISTENER_PORT_NO = 9004;
	public static final int SABOTAGE_LISTENER_PORT_NO = 9003;
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
		byte[] bytes = new byte[10000];
		socket.getInputStream().read(bytes);
		return bytes;
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
	    ObjectOutputStream os;
	    try{
	    os= new ObjectOutputStream(bos);
	    os.writeObject(object);
	    os.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return bos.toByteArray();
	}
	/**
	 * Unserialises an object from an array of bytes.
	 * @param object Byte array of object
	 * @return	Unserialised Object
	 */
	public static Object fromByteArray(byte[] object){
	    ByteArrayInputStream bis = new ByteArrayInputStream(object);
	    Object o = null;
	    try{
	    ObjectInputStream oInputStream = new ObjectInputStream(bis);
	    oInputStream.close();
	    o = oInputStream.readObject();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
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
