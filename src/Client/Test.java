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

public class Test {

	private JFrame frame;

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
		
		JPanel image_panel = new JPanel();
		frame.getContentPane().add(image_panel, BorderLayout.WEST);
		image_panel.setLayout(new BorderLayout(0, 0));
		
		JEditorPane image = new JEditorPane();
		image_panel.add(image);
		
		JPanel text_panel = new JPanel();
		frame.getContentPane().add(text_panel, BorderLayout.CENTER);
		text_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel titre_panel = new JPanel();
		text_panel.add(titre_panel, BorderLayout.NORTH);
		
		JLabel titre_label = new JLabel("\u00B5Titre");
		titre_panel.add(titre_label);
		
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
		
		JLabel label_note = new JLabel("Note nutritionnelle");
		description_panel.add(label_note, BorderLayout.SOUTH);
		
		JLabel label_description = new JLabel("Description");
		description_panel.add(label_description, BorderLayout.CENTER);
	}

}

