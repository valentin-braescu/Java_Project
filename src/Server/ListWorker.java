/**
 * 
 */
package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Sebastien and Valentin 
 *
 */
public class ListWorker implements Runnable {

	private Worker worker;
	private Socket socket;
	private Thread th;
	private boolean run;
	private DataInputStream in;
	
	ListWorker(Worker worker, Socket socket){
		this.worker = worker;
		this.socket = socket;
		this.run = true;	//Le listener écoute en boucle le client dès sa création
		th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		//Start listening the client
		int req = 0;
		String data ="";
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(run) {
			try {
				req = in.readInt();
				data = in.readUTF();
			} catch (IOException e) {
				System.out.println("Client aborted");
				worker.deconnection();
			}
			worker.analyzeReq(req, data);
		}
	}

	
	public void stop() {
		run = false;
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = null;
	}
	
}
