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
import java.nio.file.Paths;

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
			deconnection();
			break;
		case 1:
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
				server.broadcastUsernameCo(clientLogin);
				server.sendUserList(this);
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
					// Format: numLine, code, type_de_produit, nom, marque, categorie, score, valeur_energetique, acides_gras_satures, sucres, proteines, fibres, sel_ou_sodium, teneur_fruits_legumes
					sendResponse(31,String.valueOf(nbLines-i)+"\t"+info);
				}
			}
			break;
		case 4:
			//Modify infos about the account
			String[] parts = data.split("\t");
			boolean modif;
			modif = server.modifCompte(data);
			if(modif) {
				// Modifications accepted
				sendResponse(41,parts[2]+"\t"+parts[3]);
				clientLogin = parts[2];
				server.broadcastUsernameCo(parts[2]+"\t"+parts[0]);
			}
			else {
				// Modifications refused
				sendResponse(40,"");
			}
			break;
		case 5:
			// Refresh the wall
			// Send the last 10 descriptions/images stored on the server
			boolean loop = true;
			int i=0;
			String text = "";
			while(loop && i<10) {
				// Get the 10 last lines stored on the server
				// Return a line with: "username,title,description,imageName,date,nbAliments,aliment1,aliment2,..."
				text = server.sendPostText(i+1);
				if(text.equals("")) loop = false;
				else {
					sendResponse(8,text);
				}
				i++;
			}
			break;
		case 7:
			// The food doesn't exist. The user wants to add it in the database
			boolean added = server.addNewFood(data);
			if(added) {
				sendResponse(71,"");
			}
			else {
				sendResponse(70,"");
			}
			break;
		case 12:
			// Message to send to someone on the Chat
			server.sendChat(data, clientLogin);
			break;
		default:
			System.out.println("Request default");
			//The id of the request is unknown, just skip the request
		}
	}
	
	public void storeInfos(String data,BufferedImage img, String date) {
		// Client uploading Text
		int upload = 0;
		upload = server.upload(data, img, clientLogin, clientId, date);
		if(upload != 0) {
			// Data uploaded accepted
			sendResponse(61,String.valueOf(upload));
		}
		else {
			// Data uploaded refused
			sendResponse(60,"0");
		}
	}
	
	
	public void sendResponse(int req, String data) {
		try {
			out.writeInt(req);
			out.writeUTF(data);
			if(req == 8) {
				
				
				
				String[] parts = data.split("\t");
				String imageName = parts[3];
				
				if( !imageName.equals(""))
				{
					
					// Sending the image to the client
					BufferedImage image = ImageIO.read(new File(Paths.get(".").toAbsolutePath().normalize().toString()+"\\"+imageName+".png"));
			        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        ImageIO.write(image, "jpg", byteArrayOutputStream);
			        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			        out.write(size);
			        out.write(byteArrayOutputStream.toByteArray());
			        out.flush();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getLogin() {
		return clientLogin;
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
