/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

/**
 * @author Valentin
 *
 */
public class Editor extends JPanel implements ActionListener{

	private Client client;
	private GUI gui;
	private String filePath;
	
	
	private JTextField textField_titre;
	private JTextField textField_description;
	private JButton button_select_image;
	private JButton button_add_aliment;
	private JButton button_remove_aliment;
	private JButton button_send;
	private BufferedImage image;
	private JPanel image_panel_container;
	private JPanel aliment_list ;
	
	public LinkedList<CreateTab_NewAliment> list_aliments;
	

	Editor(Client client, GUI gui)
	{
		
		//setPreferredSize(new Dimension(500, 500));
		list_aliments = new LinkedList<CreateTab_NewAliment>();

		this.gui = gui;
		filePath = null;
		
		setLayout(new MigLayout());
		JPanel containt = new JPanel();
		containt.setOpaque(false);
		containt.setMaximumSize(new Dimension( 500, 300));
		containt.setMinimumSize(new Dimension( 500, 300));
		containt.setLayout(new BorderLayout(0,0));
		add(containt,"push, align center"  );
		
			
		JPanel image_panel = new JPanel();
		containt.add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new GridLayout(3, 1, 0, 0));
		image_panel.setOpaque(false);
		
		JPanel image_panel_useless = new JPanel();
		image_panel.add(image_panel_useless);
		
		image_panel_container = new JPanel();
		image_panel.add(image_panel_container);
		
		//JLabel image_label = new JLabel("New label");
		//image_panel_container.add(image_label);
		
		JPanel image_button_panel = new JPanel();
		image_panel.add(image_button_panel);
		
		button_select_image = new JButton("Selectionnez une image");
		button_select_image.addActionListener(this);
		image_button_panel.add(button_select_image);
		
		JPanel editor_panel = new JPanel();
		containt.add(editor_panel, BorderLayout.CENTER);
		editor_panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel text_panel = new JPanel();
		editor_panel.add(text_panel);
		text_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel titre_panel = new JPanel();
		text_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel(" Titre de la recette : ");
		titre_panel.add(titre_label);
		
		textField_titre = new JTextField();
		titre_panel.add(textField_titre);
		textField_titre.setColumns(10);
		
		JPanel description_panel = new JPanel();
		text_panel.add(description_panel, BorderLayout.CENTER);
		description_panel.setLayout(new BorderLayout(0, 0));
		
		textField_description = new JTextField();
		description_panel.add(textField_description);
		textField_description.setColumns(10);
		
		JPanel aliment_panel = new JPanel();
		editor_panel.add(aliment_panel);
		aliment_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel button_panel = new JPanel();
		aliment_panel.add(button_panel, BorderLayout.NORTH);
		
		button_add_aliment = new JButton("Ajouter un aliment");
		button_add_aliment.addActionListener(this);
		button_panel.add(button_add_aliment);
		
		button_remove_aliment = new JButton("Retirer un aliment");
		button_remove_aliment.addActionListener(this);
		button_panel.add(button_remove_aliment);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setOpaque(false);
		aliment_panel.add(scrollPane, BorderLayout.CENTER);
		
		aliment_list = new JPanel();
		aliment_list.setLayout(new GridLayout(25, 1));
		scrollPane.setViewportView(aliment_list);
		aliment_list.setOpaque(false);
		
		JPanel send_panel = new JPanel();
		button_send = new JButton("Envoyer");
		button_send.addActionListener(this);
		send_panel.add(button_send);
		
		containt.add(send_panel, BorderLayout.SOUTH);

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
		filePath = choix.getSelectedFile().getAbsolutePath();
	}
	
	private void afficherImage()
	{
		image_panel_container.removeAll();
		JLabel label_image = new JLabel();
		//Update the panel panel_image_apercu with the just loaded image
		label_image.setIcon( new ImageIcon(image));
		image_panel_container.add(label_image);
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
	
	
	private void sendRecette()
	{
		String data = textField_titre.getText()+'\t'+textField_description.getText()+'\t';
		String aliment_string = "";
		int aliment_length = 0;
		for( int i=0; i < list_aliments.size() ; i++ )
		{
			if( list_aliments.get(i).flag == true )
			{
				aliment_string = aliment_string + list_aliments.get(i).aliment +'\t';
				aliment_length++;
			}
			
		}

		if( filePath != null)
		{
			filePath.replace("\\","\\\\");
			data = data + String.valueOf(aliment_length) + '\t' + aliment_string + filePath ;
			System.out.println(data);
			client.sendRequest(6,data);
		}

		else
		{
			data = data + String.valueOf(aliment_length) + '\t' + aliment_string + "null";
			System.out.println(data);
			client.sendRequest(6,data);
		}
	}
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\editorWallpaper.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == button_add_aliment)
		{
			CreateTab_NewAliment newAliment = new CreateTab_NewAliment(this, client);
			list_aliments.addLast(newAliment);
			aliment_list.add(newAliment);
			gui.revalidate();
			gui.repaint();
		}
		if( s == button_remove_aliment)
		{
			list_aliments.removeLast();
			aliment_list.removeAll();
			for(int i = 0; i < list_aliments.size(); i++)
			{
				aliment_list.add(list_aliments.get(i));
			}
			gui.revalidate();
			gui.repaint();
		}
		if( s  == button_select_image)
		{
			selectionnerImage();
			afficherImage();
		}
		if ( s == button_send)
		{
			sendRecette();
		}
		
		
	}
	
}
