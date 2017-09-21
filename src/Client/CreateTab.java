package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CreateTab extends JPanel implements ActionListener, DocumentListener{

	private JButton button_envoie;
	private JTextField textField_titre;
	private JTextField textField_description;
	private JLabel label_titre_apercu;
	private JLabel label_description_apercu ;
	
	CreateTab()
	{
		setLayout(new BorderLayout(0,0));
		
		// Panel affichant sur la gauche de l'écran editeur et apercu
		JPanel panel_nom_vue = new JPanel();
		panel_nom_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel_nom_vue, BorderLayout.WEST);
		panel_nom_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		JLabel label_editeur = new JLabel("Editeur");
		label_editeur.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_nom_vue.add(label_editeur);
		
		JLabel label_apercu = new JLabel("Aper\u00E7u");
		label_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_nom_vue.add(label_apercu);
		
		//Panel contenant l'aperçu et la penel d'édition
		JPanel panel_vue = new JPanel();
		add(panel_vue, BorderLayout.CENTER);
		panel_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		JPanel panel_edition = new JPanel();
		panel_edition.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_edition);
		panel_edition.setLayout(new BorderLayout(0, 0));
		
		JPanel image_panel = new JPanel();
		panel_edition.add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new BorderLayout(0, 0));
		
		JEditorPane image = new JEditorPane();
		image_panel.add(image);
		
		JPanel text_panel = new JPanel();
		panel_edition.add(text_panel);
		text_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel titre_panel = new JPanel();
		text_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel("Titre");
		titre_panel.add(titre_label);
		
		textField_titre = new JTextField();
		titre_panel.add(textField_titre);
		textField_titre.setColumns(10);
		
		JPanel aliments_panel = new JPanel();
		text_panel.add(aliments_panel, BorderLayout.SOUTH);
		aliments_panel.setLayout(new GridLayout(0, 1, 2, 0));
		
		JPanel panel = new JPanel();
		aliments_panel.add(panel);
		
		JPanel panel_1 = new JPanel();
		aliments_panel.add(panel_1);
		
		JPanel description_panel = new JPanel();
		text_panel.add(description_panel, BorderLayout.CENTER);
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
		
		JPanel panel_apercu = new JPanel();
		panel_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_apercu);
		panel_apercu.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_image_apercu = new JPanel();
		panel_apercu.add(panel_image_apercu, BorderLayout.WEST);
		
		JLabel label_image_apercu = new JLabel("Image");
		panel_image_apercu.add(label_image_apercu);
		
		JPanel panel_text_apercu = new JPanel();
		panel_apercu.add(panel_text_apercu, BorderLayout.CENTER);
		panel_text_apercu.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_titre_paercu = new JPanel();
		panel_text_apercu.add(panel_titre_paercu, BorderLayout.NORTH);
		
		label_titre_apercu = new JLabel("titre");
		panel_titre_paercu.add(label_titre_apercu);
		
		JPanel panel_aliment_paercu = new JPanel();
		panel_text_apercu.add(panel_aliment_paercu, BorderLayout.SOUTH);
		
		JLabel lblAliments = new JLabel("Aliments");
		panel_aliment_paercu.add(lblAliments);
		
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
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == button_envoie)
		{
			
		}
		if( s == textField_titre)
		{
			label_titre_apercu.setText( textField_titre.getText());
		}
		if(s == textField_description)
		{
			
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		label_titre_apercu.setText( textField_titre.getText());
	}
}
