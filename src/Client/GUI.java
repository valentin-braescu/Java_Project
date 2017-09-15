/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Valentin
 *
 */
public class GUI extends JFrame implements ActionListener{
	
	private Client client;
	private Font font = new Font("Arial",Font.BOLD,45);
	
	public GUI(Client client)
	{
		this.client= client;
		new Connexion_GUI(client, this);
		initialize();
	}
	
	
	
	public void initialize()
	{
		new JFrame();	
	
		JPanel panel = new JPanel();
		//add(panel, BorderLayout.NORTH);
	
		JMenuBar menu_bar = new JMenuBar();
		
		JMenu mon_compte = new JMenu("Mon Compte");
		menu_bar.add(mon_compte);
		JMenu deco = new JMenu("Déconnexion");
		menu_bar.add(deco);
		
		setJMenuBar(menu_bar);
		
		
		JButton button_wall = new JButton("Wall");
		button_wall.setFont(font);
		button_wall.addActionListener(this);
		panel.add(button_wall);
		
		JButton button_list = new JButton("List");
		button_list.setFont(font);
		button_list.addActionListener(this);
		panel.add(button_list);
		
		JButton button_create = new JButton("Create");
		button_create.setFont(font);
		button_create.addActionListener(this);
		panel.add(button_create);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		getContentPane().add(panel, BorderLayout.NORTH);
		setTitle("Bouffe");
		setVisible(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("start gui");
		
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object s=e.getSource()	;				
	}

}
