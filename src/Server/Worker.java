/**
 * 
 */
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Sebastien and Valentin
 *
 */
public class Worker implements Runnable{

	private SingleServer server;
	private Socket socket;
	private Thread th;
	private ListWorker listWorker;
	private DataOutputStream out;
	
	Worker(SingleServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		th = new Thread(this);
		th.start();
		
	}
	
	@Override
	public void run() {
		//Creating a Listener for each client
		listWorker = new ListWorker(this, socket);
		//Creating a stream from the worker to the client to send the responses
		try {
			out = new DataOutputStream(socket.getOutputStream());
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
			deconnection();
			break;
		case 2:
			//Connecting to an account
			boolean conn;
			conn = server.conCompte(data);
			if(conn) {
				// Connection accepted
				sendResponse(21,"");
			}
			else {
				// Connection refused
				sendResponse(20,"");
				deconnection();
			}
			break;
		case 3:
			//Looking for a food in the BDD
			break;
		case 4:
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
			//Refresh the wall
			break;
		case 6:
			//Client uploading Text
			break;
		case 7:
			//Client uploading a picture
			break;
		default:
			//The request's id is unknown, just skip the request
		}
	}
	
	public void sendResponse(int req, String data) {
		try {
			out.writeInt(req);
			out.writeUTF(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deconnection() {
		listWorker.stop();
		server.delWorker(this);
		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = null;
	}

}
