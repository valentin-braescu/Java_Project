/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
	
	private BufferedImage photo;
	private String titre;
	private String description;
	private String score;
	public int ID;
	private int nb_aliments;
	private JPanel aliments_panel;
	public String date;
	public String username;
	private JPanel image_panel;
	private Font font= new Font("Verdana",Font.ITALIC,17);

	
	private List<String> aliment_list;
	
	Recette(int nb_aliment, String titre, String description, String username, String date, BufferedImage image, String score, String ID)
	{
		this.titre= titre;
		nb_aliments = nb_aliment;
		this.description=description;
		aliment_list = new ArrayList<String>();
		this.photo = image;
		this.username=username;
		this.date = date;
		this.score=score;
		this.ID = Integer.valueOf(ID);
		
		
		
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
		setOpaque(false);
		
		//Image part of the panel
		image_panel = new JPanel();
		add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new BorderLayout(0, 0));
		
		if( image != null)
		{
			JLabel image_label = new JLabel(new ImageIcon(photo));

			Image scaled = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			image_label.setIcon( new ImageIcon(scaled));
			image_panel.add(image_label);
		}
		

		
		//Text area of the panel
		JPanel text_panel = new JPanel();
		text_panel.setOpaque(false);
		add(text_panel, BorderLayout.CENTER);
		text_panel.setLayout(new BorderLayout(0, 0));

		JPanel titre_panel = new JPanel();
		titre_panel.setOpaque(false);
		text_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel(titre);
		titre_label.setFont(font);
		titre_panel.add(titre_label);
		
		JLabel date_label = new JLabel(date);
		date_label.setFont(font);
		titre_panel.add(date_label);
		
		aliments_panel = new JPanel();
		text_panel.add(aliments_panel, BorderLayout.SOUTH);
		aliments_panel.setLayout(new GridLayout(0, 1, nb_aliments , 0));
		JLabel aliment_header = new JLabel("<html><u>Aliments :</u></html>");
		aliment_header.setFont(font);
		aliments_panel.add(aliment_header);
		

		
		JPanel description_panel = new JPanel();
		description_panel.setOpaque(false);
		text_panel.add(description_panel, BorderLayout.CENTER);
		description_panel.setLayout(new BorderLayout(0, 0));
		
		if(score.equals("null"))
		{
			JLabel label_note = new JLabel("Pas d'aliment");
		}
		else
		{
			JLabel label_note = new JLabel(score);
		}
		JLabel label_note = new JLabel(score);
		label_note.setFont(font);
		description_panel.add(label_note, BorderLayout.SOUTH);
		
		JLabel label_description = new JLabel(description);
		label_description.setFont(font);
		description_panel.add(label_description, BorderLayout.CENTER);
		
		JLabel label_user = new JLabel(username);
		label_user.setFont(font);
		description_panel.add(label_user, BorderLayout.NORTH);
		
	}
	public void addAliment(String aliment)
	{
		aliment_list.add(aliment);
		JLabel New = new JLabel(aliment);
		New.setFont(font);
		aliments_panel.add(New);
		updateGUI();
	}
	
	public void updateGUI()
	{
		
	}
	


}
