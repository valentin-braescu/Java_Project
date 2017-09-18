/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

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
	JPanel list;
	
	Wall(GUI gui)
	{
		this.gui= gui;
		
		setLayout(new BorderLayout(0,0));
		
		JPanel actualiser_panel = new JPanel();
		add(actualiser_panel, BorderLayout.NORTH);
		
		actualiser = new JButton("Actualiser");
		actualiser.addActionListener(this);
		actualiser.setFont(new Font("Arial",Font.ITALIC|Font.BOLD,18));
		actualiser_panel.add(actualiser);
		
		
		
		
		JScrollBar scroll = new JScrollBar();
		add(scroll, BorderLayout.EAST);
		
		list = new JPanel();
		add(list, BorderLayout.CENTER);
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s=e.getSource();
		if( s == actualiser)
		{
			Recette recette = new Recette();
			System.out.println("actualiser clicked");
			list.add(recette);
			gui.revalidate();
			gui.repaint();
			
		}
	} 
	
	private void afficher()
	{
		int size= fil.size();
		if( size < 10)
		{
			for( int i = 0; i < size; i++)
			{
				list.add( fil.get(i));
			}
		}
		else
		{
			for(int i=0; i < 10; i++)
			{
				list.add( fil.get(i));
			}
		}
	}
	
	
	public void addRecette(Recette recette)
	{
		fil.addFirst(recette);
		afficher();
	}
	

	
	
	
}
