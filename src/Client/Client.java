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
 * @author Valentin and Sébastien
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
			// If the user wants to upload an image
			if(req == 6) {
				// The server is warned that text and picture are being sent
				// sendRequest(6,NomDuPlat,Description,filePath)
				String[] parts = data.split("\t");
				String title = parts[0];
				String description = parts[1];
				String filePath = parts[2];
				String newData = title+"\t"+description;
				out.writeUTF(newData);
				try {
					BufferedImage image = ImageIO.read(new File(filePath));
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
			else {
				out.writeUTF(data);
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
			//DEMO sendRequest(6,"Mon titre !!! "+"\t"+"Rick's favorite food."+"\t"+"C:\\Users\\Public\\Pictures\\greenLed.png");
			//DEMO sendRequest(3,"coca");
			//DEMO sendRequest(3,"blubliblu");
			gui.setVisible(true);
			break;
		case 40 :
			System.out.println("Modification accepted");
			break;
		case 41 :
			System.out.println("Modification refused");
			break;
		case 30:
			// The food the user is looking for doesn't exist in the database
			System.out.println("[-] This food doesn't exist in our database.");
			break;
		case 31:
			// Some food has been found !
			String[] parts = data.split("\t");
			String numLine = parts[0];
			String code = parts[1];
			String type_de_produit = parts[2];
			String nom = parts[3];
			String marque = parts[4];
			String categorie = parts[5];
			String score = parts[6];
			String valeur_energetique = parts[7];
			String acides_gras_satures = parts[8];
			String sucres = parts[9];
			String proteines = parts[10];
			String fibres = parts[11];
			String sel_ou_sodium = parts[12];
			String teneur_fruits_legumes = parts[13];
			System.out.println(data);
			break;
		case 60 :
			System.out.println("[x] Error when uploading data on the server.");
			break;
		case 61 :
			System.out.println("[+] Data has been uploaded on the server.");
			break;
		case 8 :
			// Reception de text et d'image (login, title, description, date, imageName)
			break;
		default :
			break;
		}
	}
	
	public void displayPanel(String data, BufferedImage img) {
		// TODO: Display on the WALL !
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
