/**
 * 
 */
package Server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

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
	private InputStream inputStream;

	
	ListWorker(Worker worker, Socket socket){
		this.worker = worker;
		this.socket = socket;
		this.run = true;	//Le listener �coute en boucle le client d�s sa cr�ation
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
			inputStream = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(run) {
			try {
				req = in.readInt();
				data = in.readUTF();
				if(req == 7) {
					// An image is uploaded
					byte[] sizeAr = new byte[4];
			        inputStream.read(sizeAr);
			        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
			        byte[] imageAr = new byte[size];
			        inputStream.read(imageAr);
			        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
			        // Send the image to the worker
			        worker.storeImage(image);
				}
				else {
					// Analyzing string data
					worker.analyzeReq(req, data);
				}
			} catch (IOException e) {
				System.out.println("[x] Client aborted");
				worker.deconnection();
			}
		}
	}

	
	public void stop() {
		run = false;
		try {
			// Closing image stream
			inputStream.close();
			// Closing data stream
			in.close();
			//imageIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = null;
	}
	
}
