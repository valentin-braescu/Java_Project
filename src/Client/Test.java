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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Test {

	private JFrame frame;
	private JTextField textField_titre;

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
		frame.setBounds(100, 100, 848, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel image_panel = new JPanel();
		frame.getContentPane().add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel image_panel_useless = new JPanel();
		image_panel.add(image_panel_useless);
		
		JPanel image_panel_container = new JPanel();
		image_panel.add(image_panel_container);
		
		JLabel image_label = new JLabel("New label");
		image_panel_container.add(image_label);
		
		JPanel image_button_panel = new JPanel();
		image_panel.add(image_button_panel);
		
		JButton btnSelectionnezUneImage = new JButton("Selectionnez une image");
		image_button_panel.add(btnSelectionnezUneImage);
		
		JPanel editor_panel = new JPanel();
		frame.getContentPane().add(editor_panel, BorderLayout.CENTER);
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
		GridBagLayout gbl_description_panel = new GridBagLayout();
		gbl_description_panel.columnWidths = new int[]{210, 97, 0};
		gbl_description_panel.rowHeights = new int[]{76, 25, 0, 0};
		gbl_description_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_description_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		description_panel.setLayout(gbl_description_panel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		description_panel.add(btnNewButton, gbc_btnNewButton);
		
		JPanel aliment_panel = new JPanel();
		editor_panel.add(aliment_panel);
		aliment_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel button_panel = new JPanel();
		aliment_panel.add(button_panel, BorderLayout.NORTH);
		
		JButton button_add_aliment = new JButton("Ajouter un aliment");
		button_panel.add(button_add_aliment);
		
		JButton button_remove_aliment = new JButton("Retirer un aliment");
		button_panel.add(button_remove_aliment);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aliment_panel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel aliment_list = new JPanel();
		scrollPane.setViewportView(aliment_list);
		

		

	}

}

