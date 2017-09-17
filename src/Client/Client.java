/**
 * 
 */
package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Valentin
 *
 */
public class Client {
	
	
	private Socket sock;
	public GUI gui;
	private ClientListener listener;
	private DataOutputStream out;
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
		// TODO Auto-generated method stub
		new Client();
	}
	
	public void startClient(int req,String id)
	{
		/*try
		{
			sock = new Socket("localhost", 3456);
			//sock = new Socket("192.168.43.4", 3456);
			out = new DataOutputStream(sock.getOutputStream());
			
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
		//sendRequest(0,"");
		
		listener.close();
		
		try
		{
			out.close();
			sock.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.exit(0);
		
		
		
	}
	
	public void sendRequest(int req, String data)
	{
		System.out.println(data);
		try {
			out.writeInt(req);
			out.writeUTF(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void analyseReq(int req, String data)
	{
		switch(req)
		{
		case 10 :
			System.out.println("Creation failed");
			break;
		case 11 :
			System.out.println("Creation ok");
			sendRequest(2, user_id+'\t'+user_password);
			break;
		case 20 :
			System.out.println("Connection failed");
			break;
		case 21 :
			System.out.println("Connection ok");
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
