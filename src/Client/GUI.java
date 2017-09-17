/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Valentin
 *
 */
public class GUI extends JFrame implements ActionListener{
	
	private Client client;
	private Font font;
	private Font font_menu;
	private JMenuItem deco;
	private JMenuItem deconnexion;
	private JMenu mon_compte;
	private JButton connect;
	private JButton inscription;
	private JFrame frame_accueil;
	private JPanel panel_wall;
	private JPanel panel_create;
	private JPanel panel_list;
	private JPanel main_panel;
	private JButton button_wall;
	private JButton button_list;
	private JButton button_create;
	
	
	public GUI(Client client)
	{
		this.client= client;
		font = new Font("Arial",Font.ITALIC|Font.BOLD,18);
		font_menu = new Font("Arial",Font.BOLD, 18);

		initialize();
		accueil();
	}
	
	public void initialize()
	{
		new JFrame();	
		
		JPanel panel = new JPanel();
	
		JMenuBar menu_bar = new JMenuBar();
		
		mon_compte = new JMenu("Mon Compte");
		mon_compte.setFont(font_menu);
		mon_compte.addActionListener(this);

		menu_bar.add(mon_compte);
		deco = new JMenuItem("Déconnexion");
		deco.setFont(font_menu);
		deco.addActionListener(this);
		//deconnexion = new JMenuItem("Déconnexion");
		menu_bar.add(deco);
		
		setJMenuBar(menu_bar);
		
		
		button_wall = new JButton("Wall");
		button_wall.setFont(font);
		button_wall.addActionListener(this);
		panel.add(button_wall);
		
		button_list = new JButton("List");
		button_list.setFont(font);
		button_list.addActionListener(this);
		panel.add(button_list);
		
		button_create = new JButton("Create");
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
		
		main_panel = new JPanel();
		main_panel.add(new JLabel("Veuillez choisir un onglet"));
		main_panel.add(new JLabel("Wall : affiche l'ensemble des recettes publiées par les autres internautes"));
		main_panel.add(new JLabel("List : rechercher un aliment"));
		main_panel.add(new JLabel("Create : publiez une recette"));
		
		add(main_panel, BorderLayout.CENTER);
	}
	
	
	public void accueil()
	{
		String[] options = {"Connexion", "Inscription"};

	    JOptionPane accueil = new JOptionPane();

	    int choix = accueil.showOptionDialog(null, "Que souhaitez vous faire ?","Accueil",JOptionPane.YES_NO_CANCEL_OPTION,
	    		JOptionPane.QUESTION_MESSAGE,null, options,options[0]);

	    if( choix == 0)
	    {
	    	connexion(this);
	    }
	    else if( choix == 1)
	    {
	    	inscription(this);
	    }
	}
	
	 private void connexion(JFrame frame) {
	        JPanel p = new JPanel(new BorderLayout(5,5));
	        

	        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
	        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
	        labels.add(new JLabel("Password", SwingConstants.RIGHT));
	        p.add(labels, BorderLayout.WEST);

	        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
	        JTextField username = new JTextField("");
	        controls.add(username);
	        JPasswordField password = new JPasswordField();
	        controls.add(password);
	        p.add(controls, BorderLayout.CENTER);

	        
	        JOptionPane.showMessageDialog( frame, p, "Connexion", JOptionPane.QUESTION_MESSAGE);
	        setVisible(true);
	        client.setIDs(username.getText(), String.valueOf(password.getPassword()));
	        client.startClient(2, username.getText()+'\t'+String.valueOf(password.getPassword()));
	        
	    }

	
	public void inscription(JFrame frame)
	{
        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Password", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        JTextField username = new JTextField("");
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        p.add(controls, BorderLayout.CENTER);

        //LayoutManager l = new GroupLayout(p);
        //p.setLayout(l);
        JOptionPane.showMessageDialog( frame, p, "Inscription", JOptionPane.QUESTION_MESSAGE);
        
        setVisible(true);
        client.setIDs(username.getText(), String.valueOf(password.getPassword()));
        client.startClient(1, username.getText()+'\t'+String.valueOf(password.getPassword()));
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object s=e.getSource()	;	
		if( s== deco)
		{
			System.out.println("deco");
		}
		if(s == mon_compte)
		{
			System.out.println("compte");
		}
		if( s == button_wall)
		{
			main_panel.removeAll();
			main_panel.updateUI();

		}

	}
}
