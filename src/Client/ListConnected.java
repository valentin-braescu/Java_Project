/**
 * 
 */
package Client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Valentin
 *
 */
public class ListConnected extends JPanel{
	

	ListConnected()
	{
		setPreferredSize(new Dimension(150, 100));
	}
	
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\connected.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
}
