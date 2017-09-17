/**
 * 
 */
package Server;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

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
	private ImageInputStream imageIn;
	
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
		String data = "";
		try {
			//Creating an input data stream to listen to the requests
			in = new DataInputStream(socket.getInputStream());
			//Creating an input image stream to download pictures
			imageIn = ImageIO.createImageInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(run) {
			try {
				req = in.readInt();
				data = in.readUTF();
				if(req == 7) {
					// Analyzing image
					BufferedImage img=ImageIO.read(imageIn);
					worker.storeImage(img);
				}
				else {
					// Analyzing string data
					worker.analyzeReq(req, data);
				}
			} catch (IOException e) {
				System.out.println("Client aborted");
				worker.deconnection();
			}
		}
	}

	
	public void stop() {
		run = false;
		try {
			in.close();
			imageIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = null;
	}
	
}
