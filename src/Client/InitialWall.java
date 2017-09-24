package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InitialWall extends JPanel{
	
	private Font font = new Font("Verdana",Font.ITALIC|Font.BOLD,32);

	InitialWall()
	{
		
		setLayout(new GridLayout(1, 2));
		
		JPanel panel_useless = new JPanel();
		panel_useless.setOpaque(false);
		add(panel_useless);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0,0));
		
		JLabel label1 =  new JLabel("<html>Hi ! Bienvenue sur MyFood !<br><br>Pour naviguer sur l'appli, rien de plus simple il te suffit de choisir un des trois onglets !<br><br><u>Wall :</u> affiche l'ensemble des recettes publiées par les autres internautes<br><u>List :</u> recherche un aliment<br><u>Editeur :</u> publie une recette<br><br>Les auteurs : Seb et Val ;)</html>");
		label1.setFont(font);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setHorizontalTextPosition(SwingConstants.CENTER);
		//label1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label1);
		panel.setOpaque(false);
		add(panel);

		

		

	}
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\initial_wallpaper.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
}
