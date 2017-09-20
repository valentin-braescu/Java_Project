/**
 * 
 */
package Client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

/**
 * @author Valentin and S�bastien 
 *
 */
public class ClientListener implements Runnable {

	private Thread thread;
	private DataInputStream entree;
	private InputStream inputStream;
	private Socket sock	;
	private Client client;
	
	ClientListener(Socket sock, Client client)
	{
		this.sock= sock;
		this.client= client;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			entree = new DataInputStream(sock.getInputStream());
			//Creating an input image stream to download pictures
			inputStream = sock.getInputStream();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		while( thread != null)
		{
			try
			{
				int req = entree.readInt();
				String data = entree.readUTF();
				if(req == 8) {
					System.out.println("Request 8 recieved");
					// The server is sending text and pictures to display on the wall
					//byte[] sizeAr = new byte[4];
			        //inputStream.read(sizeAr);
			        //int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
			        //byte[] imageAr = new byte[size];
			        //inputStream.read(imageAr);
			        BufferedImage image = ImageIO.read(sock.getInputStream());
			        // Wait until the image is downloaded
			        // Send the image to the worker
			        System.out.println("coucou6");
			        newRecette(data, image);
			        client.displayPanel(data, image);
				}
				else {
					// the other requests are treated normally
					client.analyseReq( req, data);
				}
			}
			catch(IOException e)
			{
				
			}
		}
	}
	
	public void close()
	{
		try
		{
			entree.close();
			inputStream.close();
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
		thread = null;
	}
	
	public void newRecette(String data, BufferedImage image)
	{
		//Parsing of the string data
		System.out.println(data);
		String[] parts = data.split("\t");
		String username = parts[0];
		String titre = parts[1];
		String description = parts[2];
		String date = parts[4];
		int nb_aliments = Integer.parseInt(parts[5]);
		
		Recette recette = new Recette(nb_aliments, titre, description, username, date, image);
		
		//Detection of the aliments in the recipe
		for( int i = 0; i < nb_aliments; i++)
		{
			recette.addAliment(parts[i+6]);
		}
		
		Wall wall = client.gui.getWall();
		wall.addRecette(recette);
		
		
	}

}
