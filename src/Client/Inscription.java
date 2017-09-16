/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Valentin
 *
 */
public class Inscription implements ActionListener{

	private Client client;
	private GUI gui;
	private JFrame frame;
	private JTextField text_field_user;
	private JTextField text_field_password;
	private JButton button_valider;
	private Font font;
	
	Inscription(Client client, GUI gui)
	{
		this.client = client;
		this.gui=gui;
		font = new Font("Arial", Font.BOLD, 24);
		initialize();
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Inscription");
		//frame.setBounds(100, 100, 450, 300);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(panel);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new GridLayout(3, 0,0,0));
		
		JLabel bienvenu = new JLabel("Bienvenu sur le reseau social XXXXXX");
		bienvenu.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(bienvenu);
		JLabel instruction_1 = new JLabel("Pour vous inscrire veuillez remplir les champs 'ID' et 'Mot de passe'");
		instruction_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(instruction_1);
		JLabel instruction_2 = new JLabel("Cliquez ensuite sur 'Valider' pour finaliser l'inscription");
		instruction_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(instruction_2);
		
		
		
		
		
		panel.add(panel_6, BorderLayout.NORTH);

		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_4.add(panel_3);
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2);
		
		JLabel label_user = new JLabel("User :");
		label_user.setHorizontalAlignment(SwingConstants.CENTER);
		
		text_field_user = new JTextField();
		text_field_user.setColumns(10);
		
		JLabel label_password = new JLabel("Password");
		label_password.setHorizontalAlignment(SwingConstants.CENTER);
		
		text_field_password = new JTextField();
		text_field_password.setColumns(10);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		panel_2.add(label_user);
		panel_2.add(text_field_user);
		panel_2.add(label_password);
		panel_2.add(text_field_password);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		button_valider = new JButton("Valider");
		button_valider.addActionListener(this);
		panel_5.add(button_valider);
		
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s=e.getSource();
		if(s==button_valider)
		{
			client.startClient(1, text_field_user.getText()+'\t'+text_field_password.getText());
			gui.setVisible(true);
		}
	}
}
