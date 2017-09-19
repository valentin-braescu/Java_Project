/**
 * 
 */
package Client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

/**
 * @author Valentin
 *
 */
public class Client {
	
	
	private Socket sock;
	public GUI gui;
	private ClientListener listener;
	private DataOutputStream out;
	private OutputStream outputStream;
	private String user_id;
	private String user_password;

	
	Client()
	{
		gui = new GUI(this);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();
	}
	
	public void startClient(int req,String id)
	{
		/*try
		{
			sock = new Socket("localhost", 3456);
			//sock = new Socket("192.168.43.4", 3456);
			out = new DataOutputStream(sock.getOutputStream());
			outputStream = sock.getOutputStream();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		listener = new ClientListener(sock, this);
		
		sendRequest( req, id);*/
		
	}
	
	public void stopClient()
	{
		sendRequest(0,"");
		listener.close();
		
		try
		{
			// Closing data stream
			out.close();
			// Closing Image stream
			outputStream.close();
			// Closing the socket
			sock.close();
		}
		catch(IOException e)
		{
			System.out.println("[x] Client aborted");
		}
		
		System.exit(0);
		
		
		
	}
	
	public void sendRequest(int req, String data)
	{
		System.out.println(data);
		try {
			out.writeInt(req);
			out.writeUTF(data);
			// If the user wants to upload an image
			if(req == 7) {
				// The server is warned that a picture is being sent: sendRequest(7,filePath)
				try {
					BufferedImage image = ImageIO.read(new File(data));
			        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        // Need to check the extension of the file uploaded (by now, the default is JPG)
			        ImageIO.write(image, "jpg", byteArrayOutputStream);
			        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			        outputStream.write(size);
			        outputStream.write(byteArrayOutputStream.toByteArray());
			        outputStream.flush();
			        byteArrayOutputStream.close();
				} catch (IOException e) {
					System.out.println("[x] Error when uploading an image.");
				}
			}
		} catch (IOException e) {
			System.out.println("[x] Server down");
		}
		
	}
	
	public void analyseReq(int req, String data)
	{
		switch(req)
		{
		case 10 :
			System.out.println("Creation failed");
			// Reset login and password
			setIDs("","");
			gui.connexion(false,false,"",1);
			break;
		case 11 :
			System.out.println("Creation ok");
			sendRequest(2, user_id+'\t'+user_password);
			break;
		case 20 :
			System.out.println("Connection failed");
			// Reset login and password
			setIDs("","");
			gui.connexion(false,false,"",2);
			break;
		case 21 :
			System.out.println("Connection ok");
			gui.setVisible(true);
			sendRequest(7,"C:\\Users\\Public\\Pictures\\greenLed.png");
			break;
		case 40 :
			System.out.println("Modification accepted");
			break;
		case 41 :
			System.out.println("Modification refused");
			break;
		case 30 :
			break;
		case 31 :
			break;
		case 60 :
			break;
		case 61 :
			break;
		case 70 :
			break;
		case 71 :
			break;
		case 8 :
			break;
		case 9 :
			break;
		default :
			break;
		}
	}
	
	public void setIDs(String id, String password)
	{
		user_id = id;
		user_password=password;
	}
	
	public String getIDUser()
	{
		return user_id;
	}
	
	public String getIDPassoword()
	{
		return user_password;
	}

}
