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
 * @author Valentin
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
			inputStream = sock.getInputStream()
;		}
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
				if( req != 8)
				{
					client.analyseReq( req, data);
				}
				else
				{
					try
					{
						byte[] sizeAr = new byte[4];
						inputStream.read(sizeAr);
						int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
						byte[] imageAr = new byte[size];
						inputStream.read(imageAr);
						BufferedImage Image = ImageIO.read(new ByteArrayInputStream(imageAr));
						
						newRecette(data, Image);

						
					}	
					catch( IOException e)
					{
						e.printStackTrace();
					}
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
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
		thread = null;
	}
	
	public void newRecette(String string, BufferedImage image)
	{
		Recette recette = new Recette();

		
		Wall wall = client.gui.getWall();
		wall.addRecette(recette);
		
		
	}

}
