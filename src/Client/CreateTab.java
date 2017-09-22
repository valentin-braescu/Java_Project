package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreateTab extends JPanel implements ActionListener, DocumentListener{

	private JButton button_envoie;
	private JTextField textField_titre;
	private JTextField textField_description;
	private JLabel label_titre_apercu;
	private JLabel label_description_apercu ;
	private JButton button_select_image;
	private JLabel label_image_apercu;
	private JPanel panel_image_apercu;
	private Client client;
	private GUI gui;
	private BufferedImage image;
	private JButton button_add_aliment;
	private JButton button_remove_aliment;
	private JPanel panel_list_aliment;
	private JPanel panel_aliment_paercu;
	private LinkedList<CreateTab_NewAliment> list_aliments;
	
	public LinkedList<String> aliments;
	
	CreateTab(Client client, GUI gui)
	{
		this.gui = gui;
		this.client = client;
		list_aliments = new LinkedList<CreateTab_NewAliment>();
		aliments = new LinkedList<String>();
		setLayout(new BorderLayout(0,0));
		
		// Panel affichant sur la gauche de l'écran editeur et apercu
		JPanel panel_nom_vue = new JPanel();
		panel_nom_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel_nom_vue, BorderLayout.WEST);
		panel_nom_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		JLabel label_editeur = new JLabel("Editeur");
		label_editeur.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_editeur.setPreferredSize(new Dimension(200, 200));
		label_editeur.setHorizontalAlignment(SwingConstants.CENTER);
		panel_nom_vue.add(label_editeur);
		
		JLabel label_apercu = new JLabel("Aper\u00E7u");
		label_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_apercu.setPreferredSize(new Dimension(100, 100));
		label_apercu.setHorizontalAlignment(SwingConstants.CENTER);
		panel_nom_vue.add(label_apercu);
		
		//Panel contenant l'aperçu et le penel d'édition
		JPanel panel_vue = new JPanel();
		add(panel_vue, BorderLayout.CENTER);
		panel_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		//Panel edition init
		JPanel panel_edition = new JPanel();
		panel_edition.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_edition);
		panel_edition.setLayout(new BorderLayout(0, 0));
		
		//Panel image
		JPanel image_panel = new JPanel();
		panel_edition.add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new BorderLayout(0, 0));
		
		button_select_image = new JButton("Sélectionner une image");
		image_panel.add(button_select_image);
		button_select_image.addActionListener(this);
		
		
		
		//Global panel for the all text edition (every thing except the image)
		JPanel text_panel = new JPanel();
		panel_edition.add(text_panel, BorderLayout.CENTER);
		text_panel.setLayout(new GridLayout(0, 2,0,0));
	
		//Titre, description
		JPanel non_aliment_panel = new JPanel();
		text_panel.add(non_aliment_panel);
		non_aliment_panel.setLayout(new BorderLayout(0,0));
		
		JPanel titre_panel = new JPanel();
		non_aliment_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel("Titre");
		titre_panel.add(titre_label);
		
		textField_titre = new JTextField();
		titre_panel.add(textField_titre);
		textField_titre.setColumns(10);
		
		//Panel filled with the aliments
		JPanel aliments_panel = new JPanel();
		aliments_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		non_aliment_panel.add(aliments_panel , BorderLayout.SOUTH);
		aliments_panel.setLayout(new BorderLayout(0,0));
		
		JPanel button_aliment_panel = new JPanel();
		button_add_aliment = new JButton("Ajouter un nouvel aliment");
		button_remove_aliment = new JButton("Retirer dernier aliment");
		button_add_aliment.addActionListener(this);
		button_remove_aliment.addActionListener(this);
		button_aliment_panel.add(button_add_aliment);
		button_aliment_panel.add(button_remove_aliment);
		aliments_panel.add(button_aliment_panel, BorderLayout.NORTH);
		
		JScrollPane scrollpan = new JScrollPane();
		aliments_panel.add(scrollpan, BorderLayout.CENTER);
		panel_list_aliment = new JPanel();
		panel_list_aliment.setLayout(new GridLayout(20, 1, 0, 0));
		scrollpan.setViewportView(panel_list_aliment);
		scrollpan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Description panel of the recipe
		JPanel description_panel = new JPanel();
		non_aliment_panel.add(description_panel, BorderLayout.CENTER);
		description_panel.setLayout(new BorderLayout(0, 0));
		
		JLabel label_description = new JLabel("Description");
		description_panel.add(label_description, BorderLayout.WEST);
		
		textField_description = new JTextField();
		description_panel.add(textField_description, BorderLayout.CENTER);
		textField_description.setColumns(10);
		
		JPanel panel_score = new JPanel();
		description_panel.add(panel_score, BorderLayout.SOUTH);
		
		JLabel label_score_nutri = new JLabel("Score Nutritionnnel : ");
		panel_score.add(label_score_nutri);
		
		JLabel label_score = new JLabel("C");
		label_score.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_score.add(label_score);
		
		JPanel aliment_panel = new JPanel();
		text_panel.add(aliments_panel);
		
		//Init panel percu
		JPanel panel_apercu = new JPanel();
		panel_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_apercu);
		panel_apercu.setLayout(new BorderLayout(0, 0));
		
		//Image in the panel_apercu
		panel_image_apercu = new JPanel();
		panel_apercu.add(panel_image_apercu, BorderLayout.WEST);
		label_image_apercu = new JLabel("Image");
		panel_image_apercu.setLayout(new BorderLayout(0,0));
		panel_image_apercu.add(label_image_apercu, BorderLayout.CENTER);
		panel_image_apercu.setPreferredSize(new Dimension(400, 200));
		panel_image_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		//Text part of the apercu panel
		JPanel panel_text_apercu = new JPanel();
		panel_apercu.add(panel_text_apercu, BorderLayout.CENTER);
		panel_text_apercu.setLayout(new BorderLayout(0,0));
		
		JPanel panel_titre_paercu = new JPanel();
		panel_text_apercu.add(panel_titre_paercu, BorderLayout.NORTH);
		
		label_titre_apercu = new JLabel("titre");
		panel_titre_paercu.add(label_titre_apercu);
		
		panel_aliment_paercu = new JPanel();
		panel_text_apercu.add(panel_aliment_paercu, BorderLayout.SOUTH);
		
		
		JPanel panel_description_apercu = new JPanel();
		panel_text_apercu.add(panel_description_apercu, BorderLayout.CENTER);
		panel_description_apercu.setLayout(new BorderLayout(0, 0));
		
		JLabel label_score_apercu = new JLabel("Note nutri");
		panel_description_apercu.add(label_score_apercu, BorderLayout.SOUTH);
		
		label_description_apercu = new JLabel("Description");
		panel_description_apercu.add(label_description_apercu, BorderLayout.CENTER);
		
		//Panel envoie
		JPanel panel_envoie = new JPanel();
		add(panel_envoie, BorderLayout.SOUTH);
		
		button_envoie = new JButton("Envoyer");
		panel_envoie.add(button_envoie);
		
		//Ajout de ActionListener sur les JTextField
		textField_titre.addActionListener(this);
		textField_titre.getDocument().addDocumentListener(this);
		textField_description.addActionListener(this);
		textField_description.getDocument().addDocumentListener(this);
		
	}
	
	private void selectionnerImage()
	{
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("image","jpg","png","jpeg");
		JFileChooser choix = new JFileChooser();
		choix.setFileFilter(filter);
		int retour=choix.showOpenDialog(this);
		if(retour==JFileChooser.APPROVE_OPTION){
		   // un fichier a été choisi (sortie par OK)
		   // nom du fichier  choisi 
		   choix.getSelectedFile().getName();
		   // chemin absolu du fichier choisi
		   choix.getSelectedFile().
		          getAbsolutePath();
		}// pas de fichier choisi
		BufferedImage newImage = null;
		try {
			newImage = ImageIO.read(new File(choix.getSelectedFile().getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = newImage;

	}
	
	private void sendRecette()
	{
		String data = textField_titre.getText()+'\t'+textField_description.getText()+'\t';
		String aliment_string = String.valueOf(aliments.size()) + '\t';
		for( int i=0; i < aliments.size() ; i++ )
		{
			if( i != (aliments.size() -1))
			{
				aliment_string = aliment_string + aliments.get(i) +'\t';
			}
			else
			{
				aliment_string = aliment_string + aliments.get(i);
			}
		}
		data = data + aliment_string ;
		client.sendRequest(6,data);
		//sendRequest(6,"Mon titre !!! "+"\t"+"Rick's favorite food."+"\t"+"3"+"\t"+"prince"+"\t"+"petit beurre"+"\t"+"tresor"+"\t"+"C:\\Users\\Public\\Pictures\\greenLed.png");
	}
	
	private void afficherImage()
	{
		//Update the panel panel_image_apercu with the just loaded image
		panel_image_apercu.removeAll();
		label_image_apercu = new JLabel();
		label_image_apercu.setIcon( new ImageIcon(image));
		panel_image_apercu.add(label_image_apercu);
		revalidate();
		repaint();
	}

	public void newAliment(boolean nomField,boolean marqueField,boolean valeurField,boolean acideField,boolean sucresField,boolean proteinesField,boolean fibresField,boolean selField,boolean teneurField)
	{
        JPanel p = new JPanel(new BorderLayout(5,5));
	       
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Vous pouvez ajouter un nouvel aliment, ou vous rendre sur l'onglet List faire une recherche"));
        labels.add(new JLabel("Type de produit", SwingConstants.RIGHT));
        labels.add(new JLabel("Nom", SwingConstants.RIGHT));
        labels.add(new JLabel("Marque", SwingConstants.RIGHT));
        labels.add(new JLabel("Categorie", SwingConstants.RIGHT));
        labels.add(new JLabel("Valeur énergétique", SwingConstants.RIGHT));
        labels.add(new JLabel("Acides gras saturés", SwingConstants.RIGHT));
        labels.add(new JLabel("Sucres", SwingConstants.RIGHT));
        labels.add(new JLabel("Protéines", SwingConstants.RIGHT));
        labels.add(new JLabel("Fibres", SwingConstants.RIGHT));
        labels.add(new JLabel("Sel ou sodium", SwingConstants.RIGHT));
        labels.add(new JLabel("Teneur en fruits et légumes (%)", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);
        

        
        JPanel controls = new JPanel(new GridLayout(0,1,11,2));
        Object[] list_type_produit = new Object[]{"autres","boissons"};
        JComboBox type_de_produit = new JComboBox(list_type_produit);
        controls.add(type_de_produit);
        
        JTextField nom = new JTextField();
        controls.add(nom);
        
        JTextField marque = new JTextField();
        controls.add(marque);

        Object[] list_categorie = new Object[]{"aperitif", "biscuits", "cereales","boissons", "charcuterie","desserts","plat prepare","produits de la mer", "sauce","snacking"};
        JComboBox categorie = new JComboBox(list_categorie);
        controls.add(type_de_produit);
        
        JTextField valeur_energetique = new JTextField();
        controls.add(valeur_energetique);
        
        JTextField acides_grs_satures = new JTextField();
        controls.add(acides_grs_satures);
        
        JTextField sucres = new JTextField();
        controls.add(sucres);
        
        JTextField proteines = new JTextField();
        controls.add(proteines);
        

        JTextField fibres = new JTextField();
        controls.add(fibres);
        
        JTextField sel = new JTextField();
        controls.add(sel);
        
        JTextField teneur = new JTextField();
        controls.add(teneur);
        
        
        p.add(controls, BorderLayout.CENTER);


        // Dialog : OK to send the credentials, or CANCEL the operation
        int connexionBox = JOptionPane.showConfirmDialog( this, p, "Nouvel aliment", JOptionPane.OK_CANCEL_OPTION);

        
        if(connexionBox == JOptionPane.OK_OPTION) {


            if( nom.getText().isEmpty() || marque.getText().isEmpty() || valeur_energetique.getText().isEmpty() || 
            		acides_grs_satures.getText().isEmpty() || sucres.getText().isEmpty()
            		|| proteines.getText().isEmpty() || fibres.getText().isEmpty() || sel.getText().isEmpty() || teneur.getText().isEmpty()) {
            	// One of the fields is empty
            	// By default, the fields are considered filled
            	nomField = true;
            	marqueField = true;
            	valeurField = true;
            	acideField = true;
            	sucresField = true;
            	proteinesField = true;
            	fibresField = true;
            	selField =true; 
            	teneurField = true;
            	
            	if(nom.getText().isEmpty() ) nomField = false;
            	if( marque.getText().isEmpty()) marqueField = false;
            	if( valeur_energetique.getText().isEmpty() ) valeurField= false;
            	if( acides_grs_satures.getText().isEmpty() ) acideField = false;
            	if( sucres.getText().isEmpty() ) sucresField = false;
            	if(  proteines.getText().isEmpty()) proteinesField = false;
            	if( fibres.getText().isEmpty()) fibresField =false;
            	if( sel.getText().isEmpty()) selField = false;
            	if( teneur.getText().isEmpty()) teneurField = false;
            	
            	newAliment(nomField ,marqueField ,valeurField ,acideField ,sucresField ,proteinesField ,fibresField ,selField , teneurField );
            }
            else {
            	String data = String.valueOf(type_de_produit.getSelectedItem()) +'\t'+
            			nom.getText()+'\t'+marque.getText()+'\t'+ categorie.getSelectedItem()+
            			valeur_energetique.getText()+'\t'+acides_grs_satures.getText()+'\t'+sucres.getText()+'\t'+
            			proteines.getText()+'\t'+fibres.getText()+'\t'+sel.getText()+'\t'+teneur.getText();
    	        client.sendRequest(7, data);
            }
        }
        else if(connexionBox == JOptionPane.CANCEL_OPTION) {
        	//si lutilisateur appui sur annuler
        }

	}
	
	public void updateAlimentsApercu()
	{
		panel_aliment_paercu.removeAll();
		for( int i=0; i < aliments.size(); i++)
		{
			new JLabel(aliments.get(i));
		}
		gui.revalidate();
		gui.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == button_envoie)
		{
			sendRecette();
		}
		if( s == textField_titre)
		{
			label_titre_apercu.setText( textField_titre.getText());
		}
		if(s == textField_description)
		{
			label_description_apercu.setText( textField_description.getText());
		}
		if (s == button_select_image)
		{
			selectionnerImage();
			afficherImage();
		}
		if( s == button_add_aliment)
		{
			CreateTab_NewAliment newAliment = new CreateTab_NewAliment(this, client);
			list_aliments.addLast(newAliment);
			panel_list_aliment.add(newAliment);
			gui.revalidate();
			gui.repaint();
		}
		if( s == button_remove_aliment)
		{
			list_aliments.removeLast();
			panel_list_aliment.removeAll();
			for(int i = 0; i < list_aliments.size(); i++)
			{
				panel_list_aliment.add(list_aliments.get(i));
			}
			gui.revalidate();
			gui.repaint();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
		label_description_apercu.setText( textField_description.getText());
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
		label_description_apercu.setText( textField_description.getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
		label_description_apercu.setText( textField_description.getText());
	}
}
