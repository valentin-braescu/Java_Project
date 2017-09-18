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

import javax.swing.BorderFactory;
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
	//private Wall wall;
	private CreateTab createTab;
	
	
	
	private Font font;
	private Font font_menu;
	private JMenu deco;
	private JMenuItem deconnexion;
	private JMenuItem modifier_compte;
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
		
		//wall = new Wall();
		createTab = new CreateTab();

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
		modifier_compte = new JMenuItem("Modifier compte");
		modifier_compte.addActionListener(this);
		mon_compte.add(modifier_compte);

		menu_bar.add(mon_compte);
		deco = new JMenu("Déconnexion");
		deco.setFont(font_menu);
		deconnexion = new JMenuItem("Deconnexion");
		deconnexion.addActionListener(this);
		deco.add(deconnexion);
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
	    	connexion(true,true,"", 2);
	    }
	    else if( choix == 1)
	    {
	    	connexion(true,true,"", 1);
	    }
	}
	
	// NOTE : les fonctions "connexion" et "inscription" se ressemblent de ouf. Rassembler tout ça en une serait mieux.
	
	 public void connexion(boolean loginField, boolean passwordField, String defaultLogin, int mode) {
	        JPanel p = new JPanel(new BorderLayout(5,5));
	       
	        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
	        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
	        labels.add(new JLabel("Password", SwingConstants.RIGHT));
	        p.add(labels, BorderLayout.WEST);

	        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
	        JTextField username = new JTextField(defaultLogin);
	        controls.add(username);
	        // If the login field was empty the last time
	        if(!loginField) username.setBorder(BorderFactory.createLineBorder(Color.RED));
	        
	        JPasswordField password = new JPasswordField();
	        controls.add(password);
	        p.add(controls, BorderLayout.CENTER);
	        // If the password field was empty the last time
	        if(!passwordField) password.setBorder(BorderFactory.createLineBorder(Color.RED));

	        // Dialog : OK to send the credentials, or CANCEL the operation
	        int connexionBox;
	        if( mode == 2)
	        {
	        	connexionBox = JOptionPane.showConfirmDialog( this, p, "Connexion", JOptionPane.OK_CANCEL_OPTION);
	        }
	        else
	        {
	        	connexionBox = JOptionPane.showConfirmDialog( this, p, "Inscription", JOptionPane.OK_CANCEL_OPTION);
	        }
	        
	        if(connexionBox == JOptionPane.OK_OPTION) {
	        	// Parsing the entries login/password
	        	String login = username.getText();
	        	String pass = String.valueOf(password.getPassword());
	            if(login.isEmpty() || pass.isEmpty()) {
	            	// One of the fields is empty
	            	boolean newLoginField, newPasswordField;
	            	// By default, the fields are considered filled
	            	newLoginField = true;
	            	newPasswordField = true;
	            	if(login.isEmpty()) newLoginField = false;
	            	if(pass.isEmpty()) newPasswordField = false;
	            	connexion(newLoginField,newPasswordField,login, mode);
	            }
	            else {
	            	// Saving login and password (temporarily, waiting for the server to acknowledge)
	    	        client.setIDs(login, pass);
	    	        // Starting client - Send login and password to the server
	    	        client.startClient(mode, login+'\t'+pass);
	            }
	        }
	        else if(connexionBox == JOptionPane.CANCEL_OPTION) {
	        	accueil();
	        }
	        else {
	        	System.exit(0);
	        }
	    }
	
	public void modifierCompte(JFrame frame)
	{
        JPanel p = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Password", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        JTextField username = new JTextField(client.getIDUser());
        controls.add(username);
       JTextField password = new JTextField(client.getIDPassoword());
        controls.add(password);
        p.add(controls, BorderLayout.CENTER);

        JOptionPane.showMessageDialog( frame, p, "Modification du compte", JOptionPane.QUESTION_MESSAGE);
        
        setVisible(true);
        client.setIDs(username.getText(), password.getText());
        client.startClient(4, username.getText()+'\t'+password.getText());
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object s=e.getSource()	;	
		if( s== deconnexion)
		{
			client.stopClient();
			
		}
		if(s == mon_compte)
		{
			System.out.println("compte");
		}
		if( s == button_wall)
		{
			
			remove(main_panel);
			//main_panel = wall;
			add(main_panel);
			revalidate();
			repaint();
			
		}
		if( s == button_create)
		{
			
			remove(main_panel);
			main_panel = createTab;
			add(main_panel);
			revalidate();
			repaint();
		}
		if( s == button_list)
		{
			remove(main_panel);
			revalidate();
			repaint();
		}
		if( s == modifier_compte)
		{
			modifierCompte(this);
		}
	}
	
}
