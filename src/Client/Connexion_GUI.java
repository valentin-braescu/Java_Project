package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Connexion_GUI implements ActionListener {

	private Client client;
	private GUI gui;
	private JFrame frame;
	
	private JButton button_connexion;
	private JButton button_sinscrire;
	
	private JTextField text_field_user;
	private JTextField text_field_password;
	
	
	
	Connexion_GUI(Client client, GUI gui)
	{
		this.client = client;
		this.gui = gui;
		
		initialize();
		frame.setVisible(true);
	}
	
	public void initialize()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Connexion");
		frame.setBounds(100, 100, 450, 300);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(panel);

		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
		button_sinscrire = new JButton("S'inscrire");
		button_sinscrire.addActionListener(this);
		panel_1.add(button_sinscrire);
		
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
		button_connexion = new JButton("Connexion");
		button_connexion.addActionListener(this);
		panel_5.add(button_connexion);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s=e.getSource()	;	
		if( s == button_connexion)
		{

			
			client.startClient(text_field_user.getText()+"\t"+text_field_password.getText());
			
			frame.setVisible(false);
			gui.setVisible(true);
		}
		if( s == button_sinscrire)
		{
			
		}
	}
}
