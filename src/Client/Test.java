package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;

public class Test {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_description;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_nom_vue = new JPanel();
		panel_nom_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_nom_vue, BorderLayout.WEST);
		panel_nom_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		JLabel label_editeur = new JLabel("Editeur");
		label_editeur.setHorizontalAlignment(SwingConstants.CENTER);
		label_editeur.setPreferredSize(new Dimension(100, 100));
		label_editeur.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_nom_vue.add(label_editeur);
		
		JLabel label_apercu = new JLabel("Aper\u00E7u");
		label_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_nom_vue.add(label_apercu);
		
		JPanel panel_vue = new JPanel();
		panel_vue.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_vue, BorderLayout.CENTER);
		panel_vue.setLayout(new GridLayout(0, 1, 2, 0));
		
		JPanel panel_edition = new JPanel();
		panel_edition.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_edition);
		panel_edition.setLayout(new BorderLayout(0, 0));
		
		JPanel image_panel = new JPanel();
		panel_edition.add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new MigLayout("", "[97px]", "[123px]"));
		
		JButton btnNewButton = new JButton("New button");
		image_panel.add(btnNewButton, "cell 0 0,alignx left,growy");
		
		JPanel text_panel = new JPanel();
		panel_edition.add(text_panel, BorderLayout.CENTER);
		text_panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel non_aliment_panel = new JPanel();
		text_panel.add(non_aliment_panel);
		non_aliment_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel titre_panel = new JPanel();
		non_aliment_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel("Titre");
		titre_panel.add(titre_label);
		
		textField = new JTextField();
		titre_panel.add(textField);
		textField.setColumns(10);
		
		JPanel aliments_panel = new JPanel();
		non_aliment_panel.add(aliments_panel, BorderLayout.SOUTH);
		aliments_panel.setLayout(new GridLayout(0, 1, 2, 0));
		
		JPanel panel = new JPanel();
		aliments_panel.add(panel);
		
		JPanel panel_1 = new JPanel();
		aliments_panel.add(panel_1);
		
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
		text_panel.add(aliment_panel);
		aliment_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		aliment_panel.add(panel_2, BorderLayout.NORTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_2.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		aliment_panel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		scrollPane.setViewportView(panel_3);
		
		JPanel panel_apercu = new JPanel();
		panel_apercu.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_vue.add(panel_apercu);
		panel_apercu.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_image_apercu = new JPanel();
		panel_image_apercu.setPreferredSize(new Dimension(20, 20));
		panel_apercu.add(panel_image_apercu, BorderLayout.WEST);
		panel_image_apercu.setLayout(new MigLayout("", "[36px]", "[16px]"));
		
		JLabel label_image_apercu = new JLabel("Image");
		panel_image_apercu.add(label_image_apercu, "cell 0 0,alignx left,aligny top");
		
		JPanel panel_text_apercu = new JPanel();
		panel_apercu.add(panel_text_apercu, BorderLayout.CENTER);
		panel_text_apercu.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_titre_paercu = new JPanel();
		panel_text_apercu.add(panel_titre_paercu, BorderLayout.NORTH);
		
		JLabel label_titre_apercu = new JLabel("titre");
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
		
		JLabel label_description_apercu = new JLabel("Description");
		panel_description_apercu.add(label_description_apercu, BorderLayout.CENTER);
		
		
		

	}

}

