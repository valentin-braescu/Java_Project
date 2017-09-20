/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @author Valentin and Sébastien 
 *
 */
public class Recette extends JPanel{
	
	private Image photo;
	private String titre;
	private String description;
	private String note;
	private int nb_aliments;
	private JPanel aliments_panel;
	
	private List<String> aliment_list;
	
	Recette(int nb_aliment, String titre, String description)
	{
		this.titre= titre;
		nb_aliments = nb_aliment;
		this.description=description;
		aliment_list = new ArrayList<String>();
		
		
		/*########################################### 
		 * Partie graphique
		 * ########################################*/
		/*setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		JPanel panel_photo = new JPanel();
		panel_photo.setLayout(new BorderLayout(0,0));
		add(panel_photo, BorderLayout.WEST);
		
		JEditorPane editorPane = new JEditorPane();
		panel_photo.add(editorPane);
		
		//Description de la recette
		JPanel panel_description= new JPanel();
		add(panel_description, BorderLayout.CENTER);
		panel_description.setLayout(new BorderLayout(0,0));
		
		//Titre de la recette
		JPanel panel_titre = new JPanel();
		JLabel label_titre = new JLabel(titre);
		panel_titre.add(label_titre);
		add(panel_titre, BorderLayout.NORTH);*/
		
		setVisible(true);
		
		//Image part of the panel
		JPanel image_panel = new JPanel();
		add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new BorderLayout(0, 0));
		
		JLabel image = new JLabel(new ImageIcon("index.jpg"));
		image_panel.add(image);
		
		//Text area of the panel
		JPanel text_panel = new JPanel();
		add(text_panel, BorderLayout.CENTER);
		text_panel.setLayout(new BorderLayout(0, 0));

		JPanel titre_panel = new JPanel();
		text_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel("\u00B5Titre");
		titre_panel.add(titre_label);
		
		aliments_panel = new JPanel();
		text_panel.add(aliments_panel, BorderLayout.SOUTH);
		aliments_panel.setLayout(new GridLayout(0, 1, nb_aliments , 0));
		

		
		JPanel description_panel = new JPanel();
		text_panel.add(description_panel, BorderLayout.CENTER);
		description_panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label_note = new JLabel("Note nutritionnelle");
		description_panel.add(label_note, BorderLayout.SOUTH);
		
		JLabel label_description = new JLabel("Description");
		description_panel.add(label_description, BorderLayout.CENTER);
		
	}
	public void addAliment(String aliment)
	{
		aliment_list.add(aliment);
		aliments_panel.add(new JLabel(aliment));
		updateGUI();
	}
	
	public void updateGUI()
	{
		
	}
	


}
