/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
public class List extends JPanel implements ActionListener{

	private JTextField textField_foodSearch;
	private Client client;
	private JPanel panel_list;
	private BufferedImage image;
	
	List(Client client)
	{
		this.client = client;
		
		setLayout(new BorderLayout());
		// Top bar
		JPanel topBar = new JPanel();
		add(topBar, BorderLayout.NORTH);
		topBar.setLayout(new BorderLayout());
			// Panel containing the searching tab
			JPanel searchField = new JPanel();
			topBar.add(searchField, BorderLayout.NORTH);
				textField_foodSearch = new JTextField("food name");
				textField_foodSearch.setColumns(10);
				textField_foodSearch.addActionListener(this);
				searchField.add(textField_foodSearch);
			// Name of each column
			JPanel columnNames = new JPanel();
			columnNames.setLayout(new GridLayout(1,12,0,0));
			topBar.add(columnNames,BorderLayout.CENTER);
			JLabel type = new JLabel("Type");
			columnNames.add(type);
			JLabel nom = new JLabel("Nom");
			columnNames.add(nom);
			JLabel marque = new JLabel("Marque");
			columnNames.add(marque);
			JLabel categorie = new JLabel("Catégorie");
			columnNames.add(categorie);
			JLabel score = new JLabel("Score");
			columnNames.add(score);
			JLabel valEner = new JLabel("Valeur énergétique");
			columnNames.add(valEner);
			JLabel acide = new JLabel("Acides gras saturés");
			columnNames.add(acide);
			JLabel sucre = new JLabel("Sucres");
			columnNames.add(sucre);
			JLabel prot = new JLabel("Protéines");
			columnNames.add(prot);
			JLabel fibre = new JLabel("Fibres");
			columnNames.add(fibre);
			JLabel sel = new JLabel("Sel ou sodium");
			columnNames.add(sel);
			JLabel teneur = new JLabel("Teneur fruits légumes");
			columnNames.add(teneur);
			
		// Panel containing the list of food
		// Panel with a scroll pane
		JScrollPane scroll_pane = new JScrollPane();
		scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_pane.setPreferredSize(new Dimension(100, 100));
		add(scroll_pane, BorderLayout.CENTER);
		
		panel_list = new JPanel();
		scroll_pane.setViewportView(panel_list);
		panel_list.setLayout(new GridLayout(0, 12,1,1));
	}
	
	public void addFoodToList(String food) {
		// Format: numLine, code, type_de_produit, nom, marque, categorie, score, valeur_energetique, acides_gras_satures, sucres, proteines, fibres, sel_ou_sodium, teneur_fruits_legumes
		String[] parts = food.split("\t");
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
		for(int j=0; j<12;j++) {
			panel_list.add(new JLabel(parts[2+j]));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if(s == textField_foodSearch) {
			panel_list.removeAll();
			client.sendRequest(3,textField_foodSearch.getText());
		}
	}
}
