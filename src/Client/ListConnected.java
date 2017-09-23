/**
 * 
 */
package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.Messaging.SyncScopeHelper;

/**
 * @author Valentin
 *
 */
public class ListConnected extends JPanel{
	
	private LinkedList<String> users;
	private GUI gui;
	private JLabel header = new JLabel("Liste des utilisateurs connectés");

	ListConnected(GUI gui)
	{
		this.gui=gui;
		users = new LinkedList<String>();
		setPreferredSize(new Dimension(150, 100));
		setLayout(new GridLayout(40, 1));
		add(header);
		
		header.setForeground(Color.white);
	}
	
	
	public void addUser(String name)
	{
		System.out.println("CONNECTED : "+name);
		
		boolean New = true;
		for( int i =0; i < users.size(); i++)
		{
			if( users.get(i).equals(name))
				New = false;
		}
		if( New)
		{
			users.addLast(name);
			JLabel newUser = new JLabel(name);
			newUser.setForeground(Color.white);
			add(newUser);
			
			gui.revalidate();
			gui.repaint();
		}

	}
	
	public void removeUser(String name)
	{
		
		int index = -1;
		for( int i = 0; i < users.size(); i++)
		{
			if( users.get(i).equals(name))
			{
				index = i;
			}
		}
		if( index != -1)
		{
			users.remove(index);
			System.out.println("DISCONNECTED : "+name);
			updateConnectedGUI();
		}
		
	}
	
	private void updateConnectedGUI()
	{
		removeAll();
		add(header);
		JLabel user ;
		for(int i=0; i < users.size(); i++)
		{
			user = new JLabel(users.get(i));
			user.setForeground(Color.white);
			add(user);
		}
		gui.revalidate();
		gui.repaint();
	}
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\connected.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
}
