/**
 * 
 */

package Server;

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
 * @author Sebastien and Valentin
 *
 */
public class Worker implements Runnable {

	private SingleServer server;
	private Socket socket;
	private Thread th;
	private ListWorker listWorker;
	private DataOutputStream out;
	private OutputStream outputStream;
	private String clientLogin;
	private int clientId;
	
	Worker(SingleServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		System.out.println("[+] Worker created");
		//Creating a Listener for each client
		listWorker = new ListWorker(this, socket);
		//Creating an output data stream to send the responses
		try {
			out = new DataOutputStream(socket.getOutputStream());
			outputStream = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void analyzeReq(int req, String data) {
		switch(req) {
		case 0:
			System.out.println("Request 0");
			deconnection();
			break;
		case 1:
			System.out.println("Request 1");
			// Creating a new account
			boolean crea;
			crea = server.creaCompte(data);
			if(crea) {
				// Creation accepted
				sendResponse(11,"");
			}
			else {
				// Creation refused
				sendResponse(10,"");
			}
			//deconnection();
			break;
		case 2:
			System.out.println("Request 2");
			//Connecting to an account
			int conn;
			// The server return the id of the connected user. If he's not connected, return 0.
			conn = server.conCompte(data);
			if(conn != 0) {
				// Connection accepted
				sendResponse(21,"");
				clientId = conn;
				String[] parts = data.split("\t");
				clientLogin = parts[0];
			}
			else {
				// Connection refused
				sendResponse(20,"");
				deconnection();
			}
			break;
		case 3:
			// The user is looking for information about the food
			int nbLines = server.searchFoodLines(data);
			String info = "";
			if(nbLines==0) {
				// Nothing found
				sendResponse(30,"");
			}
			else {
				// Send the infos
				for(int i = 0; i<nbLines;i++) {
					info = server.searchFoodInfos(data, nbLines-i);
					// The line number is sent so that the client knows when to stop
					sendResponse(31,String.valueOf(nbLines-i)+"\t"+info);
				}
			}
			break;
		case 4:
			System.out.println("Request 4");
			//Modify infos about the account
			boolean modif;
			modif = server.modifCompte(data);
			if(modif) {
				// Modifications accepted
				sendResponse(41,"");
			}
			else {
				// Modifications refused
				sendResponse(40,"");
			}
			break;
		case 5:
			System.out.println("Request 5");
			// Refresh the wall
			// Send the last 10 descriptions/images stored on the server
			boolean loop = true;
			int i=0;
			String text = "";
			while(loop && i<10) {
				// Get the 10 last lines stored on the server
				// Return a line with: "username,title,description,nutriScore,date,imageName"
				text = server.getUploadedText(i+1);
				if(text == "") loop = false;
				else {
					//String[] parts = text.split("\t");
					//String login = parts[0];
					//String title = parts[1];
					//String description = parts[2];
					//String date = parts[3];
					//String imageName = parts[4];
					sendResponse(8,text);
				}
				i++;
			}
			break;
		default:
			System.out.println("Request default");
			//The id of the request is unknown, just skip the request
		}
	}
	
	public void storeInfos(String data,BufferedImage img, String date) {
		System.out.println("Request 6");
		// Client uploading Text
		boolean upload = false;
		upload = server.upload(data, img, clientLogin, clientId, date);
		if(upload) {
			// Data uploaded accepted
			sendResponse(61,"");
		}
		else {
			// Data uploaded refused
			sendResponse(60,"");
		}
	}
	
	public void storeImage(BufferedImage img, String date) {
	}
	
	public void sendResponse(int req, String data) {
		try {
			out.writeInt(req);
			out.writeUTF(data);
			if(req == 8) {
				String[] parts = data.split("\t");
				//String login = parts[0];
				//String title = parts[1];
				//String description = parts[2];
				//String date = parts[3];
				String imageName = parts[4];
				BufferedImage image = ImageIO.read(new File("C:\\Users\\Sébastien\\Desktop\\Cours\\3A\\Java\\JavaProject\\Java_Project\\images\\"+imageName+".png"));
		        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		        // Need to check the extension of the file uploaded (by now, the default is PNG)
		        ImageIO.write(image, "png", byteArrayOutputStream);
		        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
		        outputStream.write(size);
		        outputStream.write(byteArrayOutputStream.toByteArray());
		        outputStream.flush();
		        byteArrayOutputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deconnection() {
		listWorker.stop();
		server.delWorker(this);
		try {
			out.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("[x] Worker aborted");
		}
		System.out.println("[-] Worker deleted");
		th = null;
	}

}
