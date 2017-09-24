/**
 * 
 */
package Server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

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
	private MsgParser parser;
	
	ListWorker(Worker worker, Socket socket){
		this.worker = worker;
		this.socket = socket;
		this.run = true;	//Le listener écoute en boucle le client dès sa création
		parser = new MsgParser();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(run) {
			try {
				req = in.readInt();
				data = in.readUTF();
				// Get the date-time of the upload
		        Date date = new Date();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String currentTime = sdf.format(date);
		        String[] parts = data.split("\t");
		        // First Step, parsing the data
		        boolean parsValid = true;
		        for(int i=0;i<parts.length;i++) {
		        	parsValid = parsValid && parser.sqlParser(parts[i]) && parser.symbolParser(parts[i]);
		        }
		        // Send the request to the worker if the parser is valid
		        if(parsValid) {
		        	if(req == 6) {
			        	// Request : 6,NomDuPlat,Description, nombre_aliments [string], aliment1 [string], aliment2, ...,alimentN, ImageOrNot,idPost
						if( parts[parts.length-2] !="null"){
							{
					        	// Receiving the image
					            byte[] sizeAr = new byte[4];
					            in.read(sizeAr);
					            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
					            byte[] imageAr = new byte[size];
					            in.read(imageAr);
					            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
								worker.storeInfos(data, image, currentTime);
							}
						}
						else
						{
							BufferedImage image = null;
							worker.storeInfos(data, image , currentTime);
						}

			        }
					else {
						// Analyzing string data
						worker.analyzeReq(req, data);
					}
		        }
			} catch (IOException e) {
				System.out.println("[x] Client aborted");
				e.printStackTrace();
				worker.deconnection();
			}
		}
	}

	
	public void stop() {
		run = false;
		try {
			// Closing data stream
			in.close();
			//imageIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = null;
	}
	
}
