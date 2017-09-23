/**
 * 
 */
package Client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import jdk.jfr.events.FileWriteEvent;

/**
 * @author Valentin and S�bastien
 *
 */
public class Client {
	
	
	private Socket sock;
	public GUI gui;
	private ClientListener listener;
	private DataOutputStream out;
	private String user_id;
	private String user_password;
	private boolean foodFound = true;
	private int idPost = 0;
	
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
	
	public int lireFichierLogin()
	{
		int returnedInt = 0;
		String line = null;
		String fileName = "D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\login.txt";
		File file = new File(fileName);
		
		if( file.exists())
		{
			FileReader fileReader;
			try {
				fileReader = new FileReader(fileName);
				BufferedReader bufferReader = new BufferedReader(fileReader);
				
				if( (line = bufferReader.readLine()) != null)
					user_id = line;
				System.out.println(user_id);
				if( (line = bufferReader.readLine()) != null)
					user_password = line;
				System.out.println(user_password);
				bufferReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				returnedInt = -1;
				System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");   
			}
			catch( IOException e)
			{
				returnedInt = -2;
				System.out.println(
		                "Error reading file '" 
		                + fileName + "'");    
			}
			returnedInt = 1;
		}
		else
		{
			returnedInt = -1;
		}
		
		return returnedInt;
	}
	
	public void writeFileLogin()
	{
		String fileName = "D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\login.txt";
		File file = new File(fileName);
		
		if( !file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(user_id);
			bufferedWriter.newLine();
			bufferedWriter.write(user_password);
			bufferedWriter.newLine();
			
			bufferedWriter.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			 System.out.println(
		                "Error writing to file '"
		                + fileName + "'");
			e.printStackTrace();
		}

	}
	
	public void deleteFileLogin()
	{
		String fileName = "D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\login.txt";
		File file = new File(fileName);
		
		file.delete();
	}
	
	public void startClient(int req,String id)
	{
		try
		{
			sock = new Socket("localhost", 3456);
			//sock = new Socket("192.168.127.222", 3456);
			out = new DataOutputStream(sock.getOutputStream());
			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		listener = new ClientListener(sock, this);
		
		sendRequest( req, id);
		
	}
	
	public void stopClient(int mode)
	{
		
		if( mode == 0)
		{
			sendRequest(0,"");
			listener.close();
			try
			{
				// Closing data stream
				out.close();
				// Closing the socket
				sock.close();
			}
			catch(IOException e)
			{
				System.out.println("[x] Client aborted");
			}
			System.exit(0);	
		}
		if( mode == 1)
		{			
			sendRequest(0,"");
			listener.close();
			try
			{
				// Closing data stream
				out.close();
				// Closing the socket
				sock.close();
			}
			catch(IOException e)
			{
				System.out.println("[x] Client aborted");
			}
		}
		System.out.println("Client stopped");
	}
	
	public void sendRequest(int req, String data)
	{
		try {
			out.writeInt(req);
			// If the user wants to upload an image
			if(req == 6) {
				boolean uploadImage = false;
				// Request : 6,NomDuPlat,Description, nombre_aliments [string], aliment1 [string], aliment2, ..., filePath
				String[] parts = data.split("\t");
				// We extract the filePath from the data
				String newData = parts[0]+"\t"+parts[1]+"\t"+parts[2];
				int nbFood = Integer.valueOf(parts[2]);
				String filepath = parts[3+nbFood];
				for(int i=0; i< nbFood;i++) {
					newData += "\t"+parts[3+i];
				}
				// The filepath can be "null" -> no image is uploaded. Send the id of the post (to work on the same post).
				if(filepath != "null") {
					newData+="\t"+"notNull";
					uploadImage = true;
				}
				else {
					newData+="\t"+"null";
					uploadImage = false;
				}
				newData+="\t"+String.valueOf(idPost);
				// Request : 6,NomDuPlat,Description, nombre_aliments [string], aliment1 [string], aliment2, ...,alimentN,imageOrNot, idPost
				out.writeUTF(newData);
				if(uploadImage) {
					try {
						// Sending image to the server
				        BufferedImage image = ImageIO.read(new File(filepath));
				        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				        ImageIO.write(image, "jpg", byteArrayOutputStream);
				        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
				        out.write(size);
				        out.write(byteArrayOutputStream.toByteArray());
				        out.flush();
					} catch (IOException e) {
						System.out.println("[x] Error when uploading an image.");
						e.printStackTrace();
					}
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
			new JOptionPane().showMessageDialog(null, "Message informatif", "Information", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 21 :
			System.out.println("[+] Connection ok");
			//sendRequest(6,"Mon titre !!! "+"\t"+"Rick's favorite food."+"\t"+"3"+"\t"+"prince"+"\t"+"petit beurre"+"\t"+"tresor"+"\t"+"C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\listWallpaper.jpg");
			gui.setVisible(true);
			break;
		case 40 :
			System.out.println("[+] Modification accepted");
			break;
		case 41 :
			System.out.println("[-] Modification refused");
			break;
		case 30:
			// The food the user is looking for doesn't exist in the database
			// System.out.println("[-] This food doesn't exist in our database.");
			foodFound = false;
			break;
		case 31:
			if(gui.main_panel == gui.list) {
				gui.getList().addFoodToList(data);
				gui.refreshList();
			}
			else {
				foodFound = true;
			}
			break;
		case 60 :
			System.out.println("[x] Error when uploading data on the server.");
			break;
		case 61 :
			System.out.println("[+] Data has been uploaded on the server.");
			break;
		case 8 :
			// Reception de texte et d'image (login, title, description, date, imageName)
			break;
		case 91 :
			//new user connected
			gui.updateConnectedList(data, 1);
			break;
		case 90 :
			//user deconncted
			gui.updateConnectedList(data, 2);
			break;
		default :
			break;
		}
	}
	
	
	public void displayPanel(String data, BufferedImage img) {
		System.out.println("Display Panel function.");
		System.out.println("Text re�u :"+data);
		// Save the image
		String[] parts = data.split("\t");
		String imageName = parts[3];
		if( img != null)
		{
			 try {
		        	File outputFile = new File("C:\\Users\\S�bastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\imagesClient\\"+imageName+".png");
					if (outputFile.createNewFile()){
						System.out.println("File is created!");
					}else{
						System.out.println("File already exists.");
					}
					ImageIO.write(img, "png", outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
       
	}
	
	public void setIDs(String id, String password)
	{
		user_id = id;
		user_password=password;
		
		writeFileLogin();
		
	}
	
	public String getIDUser()
	{
		return user_id;
	}
	
	public String getIDPassoword()
	{
		return user_password;
	}

	public boolean getFoodFoundFlag() {
		return foodFound;
	}
	
}
