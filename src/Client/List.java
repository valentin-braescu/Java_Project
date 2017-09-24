/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

/**
 * @author Sebastien
 *
 */
public class List extends JPanel implements ActionListener, FocusListener{

	private Client client;
	private JPanel header;
	private JTextField searchBar;
	private JPanel list;
	private int cptFood;
	
	List(Client client)
	{
		this.client = client;
		// Count the number of food sent by the server 
		this.cptFood = 0;
		setOpaque(false);
		setLayout(new BorderLayout(0,0));
		// Header
		header = new JPanel();
		header.setOpaque(false);
		header.setLayout(new BorderLayout(0,0));
	        // Search bar
	        searchBar = new JTextField(15);
	        //searchBar.setOpaque(false);
	        //searchBar.setLayout(new FlowLayout());
			searchBar.setForeground(Color.GRAY);
			Font f = new Font("Verdana",Font.ITALIC,17);
			searchBar.setFont(f);
			searchBar.setText("Search your food !");
			searchBar.addFocusListener(this);
			searchBar.addActionListener(this);
			header.add(searchBar,BorderLayout.CENTER);
			JLabel leftSearch = new JLabel(" ");
			leftSearch.setPreferredSize(new Dimension(350,20));
			header.add(leftSearch,BorderLayout.WEST);
			JLabel rightSearch = new JLabel(" ");
			rightSearch.setPreferredSize(new Dimension(350,20));
			header.add(rightSearch,BorderLayout.EAST);
			// Titles bar
			JPanel titles = new JPanel();
			titles.setBackground(Color.lightGray);
			//titles.setOpaque(false);
			header.add(titles,BorderLayout.SOUTH);
			titles.setLayout(new GridLayout(1,4));
			Font fontHeader = new Font("Express",Font.BOLD,16);
			JLabel nomHeader = new JLabel("Nom");
			nomHeader.setForeground(Color.BLACK);
			nomHeader.setFont(fontHeader);
			nomHeader.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			nomHeader.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
			titles.add(nomHeader);
			JLabel marqueHeader = new JLabel("Marque");
			marqueHeader.setForeground(Color.BLACK);
			marqueHeader.setFont(fontHeader);
			marqueHeader.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			marqueHeader.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
			titles.add(marqueHeader);
			JLabel scoreHeader = new JLabel("Score");
			scoreHeader.setForeground(Color.BLACK);
			scoreHeader.setFont(fontHeader);
			scoreHeader.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			scoreHeader.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
			titles.add(scoreHeader);
			JLabel valeurHeader = new JLabel("Valeur energetique");
			valeurHeader.setForeground(Color.BLACK);
			valeurHeader.setFont(fontHeader);
			valeurHeader.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			valeurHeader.setBorder(BorderFactory.createMatteBorder(1,0,1,0, Color.WHITE));
			titles.add(valeurHeader);

		add(header,BorderLayout.NORTH);
		// List Panel
		list = new JPanel(new GridBagLayout());
		//list.setOpaque(false);
		list.setLayout(new GridLayout(15,1));
		list.setBackground(new Color(0,0,0,80));
		add(list,BorderLayout.CENTER);
	}
	
	public void addFoodToList(String food) {
		if(cptFood<15) {
			cptFood++;
			// Format: numLine, code, type_de_produit, nom, marque, categorie, score, valeur_energetique, acides_gras_satures, sucres, proteines, fibres, sel_ou_sodium, teneur_fruits_legumes
			String[] parts = food.split("\t");
			JPanel line = new JPanel();
			line.setLayout(new GridLayout(1,4));
			line.setOpaque(false);
			//String type_de_produit = parts[2];
			//String nom = parts[3];
			//String marque = parts[4];
			//String categorie = parts[5];
			//String score = parts[6];
			//String valeur_energetique = parts[7];
			//String acides_gras_satures = parts[8];
			//String sucres = parts[9];
			//String proteines = parts[10];
			//String fibres = parts[11];
			//String sel_ou_sodium = parts[12];
			//String teneur_fruits_legumes = parts[13];
			Font fontHeader = new Font("Arial",Font.BOLD,16);
			// Nom
			JLabel nom = new JLabel(parts[3]);
			nom.setForeground(Color.WHITE);
			nom.setFont(fontHeader);
			nom.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			nom.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.WHITE));
			line.add(nom);
			// Marque
			JLabel marque = new JLabel(parts[4]);
			marque.setForeground(Color.WHITE);
			marque.setFont(fontHeader);
			marque.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			marque.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.WHITE));
			line.add(marque);
			// Score
			JLabel score = new JLabel(parts[6]);
			score.setForeground(Color.WHITE);
			score.setFont(fontHeader);
			score.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			score.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.WHITE));
			line.add(score);
			// Valeur énergétique
			JLabel valeur = new JLabel(parts[7]);
			valeur.setForeground(Color.WHITE);
			valeur.setFont(fontHeader);
			valeur.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
			valeur.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.WHITE));
			line.add(valeur);
			
			list.add(line);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if(s == searchBar) {
			list.removeAll();
			// reset the number of food in the list
			cptFood = 0;
			client.sendRequest(3,searchBar.getText());
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		searchBar.setText("");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
	}
	
	@Override
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\listWallpaper.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
}
