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
import java.util.concurrent.TimeUnit;

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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Valentin and S�bastien 
 *
 */
public class GUI extends JFrame implements ActionListener{
	
	private Client client;
	private Wall wall;
	public Editor createTab;
	public List list;
	
	
	
	private Font font;
	private Font font_menu;
	private JMenu deco;
	private JMenuItem deconnexion;
	private JMenuItem modifier_compte;
	private JMenuItem change_user;
	private JMenu mon_compte;
	private JButton connect;
	private JButton inscription;
	private JFrame frame_accueil;
	private JPanel panel_wall;
	private JPanel panel_create;
	private JPanel panel_list;
	public JPanel main_panel;
	private JButton button_wall;
	private JButton button_list;
	private JButton button_create;
	private JButton send_message;
	
	public JPanel chat_panel;
	public JPanel chat_messages_panel;
	
	
	public GUI(Client client)
	{
		System.out.println("creationGUI");
		this.client= client;
		font = new Font("Arial",Font.ITALIC|Font.BOLD,18);
		font_menu = new Font("Arial",Font.BOLD, 18);
		
		wall = new Wall(this, client);
		createTab = new Editor(client,this);
		list = new List(client);
		
		initialize();
		accueil();
	}
	
	public void initialize()
	{
		new JFrame();	
		
		JPanel panel = new JPanel();
	
		
		//Menu panel
		JMenuBar menu_bar = new JMenuBar();
		
		mon_compte = new JMenu("Mon Compte");
		mon_compte.setFont(font_menu);
		mon_compte.addActionListener(this);
		modifier_compte = new JMenuItem("Modifier compte");
		modifier_compte.addActionListener(this);
		change_user = new JMenuItem("Changer utilisateur");
		change_user.addActionListener(this);
		mon_compte.add(modifier_compte);
		mon_compte.add(change_user);

		menu_bar.add(mon_compte);
		deco = new JMenu("Deconnexion");
		deco.setFont(font_menu);
		deconnexion = new JMenuItem("Deconnexion");
		deconnexion.addActionListener(this);
		deco.add(deconnexion);
		menu_bar.add(deco);
		
		setJMenuBar(menu_bar);
		
		
		//Button panel
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
		
		
		
		//Chat panel
		
		chat_panel = new JPanel();
		chat_panel.setLayout(new BorderLayout());
		chat_messages_panel = new JPanel();
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(chat_messages_panel);
		
		chat_panel.add(scrollpane, BorderLayout.CENTER);
		
		
		send_message = new JButton("Envoyer message");
		send_message.addActionListener(this);
		chat_messages_panel.add( send_message, BorderLayout.SOUTH);
		
		
		
		
		//frame configuration
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		getContentPane().add(panel, BorderLayout.NORTH);
		getContentPane().add(chat_panel, BorderLayout.EAST);
		setTitle("Bouffe");
		setVisible(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//init of main-panel
		main_panel = new JPanel();
		main_panel.add(new JLabel("Veuillez choisir un onglet"));
		main_panel.add(new JLabel("Wall : affiche l'ensemble des recettes publiees par les autres internautes"));
		main_panel.add(new JLabel("List : rechercher un aliment"));
		main_panel.add(new JLabel("Create : publiez une recette"));

		
		add(main_panel, BorderLayout.CENTER);
	}
	
	public void setGUIVisible(boolean flag)
	{
		setVisible(flag);
	}
	
	
	public void accueil()
	{
		
		int flag = client.lireFichierLogin();
		
		if( flag == -1)
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
		
		if( flag == 1)
		{
			client.startClient(2, client.getIDUser()+'\t'+client.getIDPassoword() );
		}
		else
		{
			System.exit(0);
		}
		
		

	}
	
	// NOTE : les fonctions "connexion" et "inscription" se ressemblent de ouf. Rassembler tout �a en une serait mieux.
	
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

	public void switchUser()
	{
		System.out.println("start switch user");
		client.deleteFileLogin();
		
		//ask for new user IDs
		client.stopClient( 1);
		//
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("accueil");
		accueil();
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
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Object s=e.getSource()	;	
		if( s== deconnexion)
		{
			client.stopClient(0);
			
		}
		if(s == mon_compte)
		{
			System.out.println("compte");
		}
		if( s == button_wall)
		{
			
			remove(main_panel);
			main_panel = wall;
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
			main_panel = list;
			add(main_panel);
			revalidate();
			repaint();
		}
		if( s == modifier_compte)
		{
			modifierCompte(this);
		}
		if ( s == change_user)
		{
			switchUser();
		}
	}
	
	public void refreshList() {
		remove(main_panel);
		main_panel = list;
		add(main_panel);
		revalidate();
		repaint();
	}
	
	public List getList() {
		return list;
	}
	
	public Wall getWall()
	{
		return wall;
	}
	
}
