/**
 * 
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Sebastien and Valentin
 *
 */
public class Listener implements Runnable {

	final static int MAXUSER = 30;
	final static int PORT = 3456;
	
	private SingleServer server;
	private ServerSocket gestSock;
	private Thread th;
	private boolean run = true; //Boucle d'écoute des clients
	private Socket socket;
	private int nbUsers;
	
	Listener(SingleServer server) {
		this.server = server;
		//Initialy, no client
		this.nbUsers = 0;
		//Running the thread to listen to the connections
		th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		System.out.println("[+] Opening port "+PORT);
		try {
			gestSock = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Erreur ouverture du port"+PORT);
			e.printStackTrace();
		}
		
		while(run) {
			try {
				socket = null;
				//Listening connections
				socket = gestSock.accept();
				//Provide a worker for each connected client
				Worker worker = new Worker(server, socket);
				server.addWorker(worker);
				nbUsers++;
				while(nbUsers>=MAXUSER) {
					//Wait here until a client release its place.
				}
			} catch (IOException e) {
				System.out.println("Erreur lors de l'acceptation de connexion");
				e.printStackTrace();
			}
		}
	}
	
	public void stopListener() {
		run = false;
		th = null;
	}

	public void removeUser() {
		nbUsers--;
	}
}
