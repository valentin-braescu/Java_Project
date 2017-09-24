/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.sun.javafx.tk.Toolkit;

import javafx.scene.layout.Border;

/**
 * @author Valentin
 *
 */
public class Wall extends JPanel implements ActionListener{

	private JButton actualiser;
	private LinkedList<Recette> fil; 
	private GUI gui;
	private Client client;
	private JPanel panel_list;
	private JScrollPane scroll_pane;
	
	Wall(GUI gui, Client client)
	{
		//Members init
		this.gui= gui;
		this.client = client;
		fil = new LinkedList<Recette>();
		
		//General GUI init
		setLayout(new BorderLayout(0,0));
		setOpaque(false);
		
		JPanel actualiser_panel = new JPanel();
		add(actualiser_panel, BorderLayout.NORTH);
		
		//Actualiser button
		actualiser = new JButton("Actualiser");
		actualiser.addActionListener(this);
		actualiser.setFont(new Font("Arial",Font.ITALIC|Font.BOLD,18));
		actualiser_panel.add(actualiser);
		
		
		
		//Panel with a scroll pane
		scroll_pane = new JScrollPane();
		scroll_pane.setOpaque(false);
		scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//scroll_pane.setPreferredSize(new Dimension(100, 100));
		
		add(scroll_pane, BorderLayout.CENTER);
		
		
		panel_list = new JPanel();
		panel_list.setOpaque(false);
		scroll_pane.setViewportView(panel_list);
		scroll_pane.getViewport().setOpaque(false);
		panel_list.setLayout(new GridLayout(10, 1,0,0));
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s=e.getSource();
		if( s == actualiser)
		{
			/*Recette recette = new Recette(2,"Recette example", "Miam une recette qu'elle est bonne");
			System.out.println("actualiser clicked");*/
			
			client.sendRequest(5,"");
			//panel_list.add(recette);
			//gui.pack();
			gui.revalidate();
			gui.repaint();
			
		}
	} 
	
	private void afficher()
	{
		panel_list.removeAll();
		int size= fil.size();
		if( size < 10)
		{
			for( int i = 0; i < size; i++)
			{
				panel_list.add( fil.get(i));
			}
		}
		else
		{
			for(int i=0; i < 10; i++)
			{
				panel_list.add( fil.get(i));
			}
		}
	}
	
	
	public void addRecette(Recette recette)
	{
		fil.addFirst(recette);
		afficher();
	}
	

    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\wall.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
	
	
}
