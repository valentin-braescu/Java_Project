/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Valentin and Sébastien 
 *
 */
public class Recette extends JPanel{
	
	private Image photo;
	private String titre;
	private String description;
	private String note;
	
	Recette()
	{
		titre = new String("Hé, une recette !");
	}
	
	public void Recette_GUI()
	{
		//setBounds(100,100,450,300);
		
		//Photo
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
		add(panel_titre, BorderLayout.NORTH);
		
		setVisible(true);
		
		
	}

}
