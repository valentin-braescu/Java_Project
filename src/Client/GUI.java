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
	private JMenu deco;
	private JMenu mon_compte;
	private JButton connect;
	private JButton inscription;
	private JFrame frame_accueil;
	
	public GUI(Client client)
	{
		this.client= client;
		font = new Font("Arial",Font.BOLD,22);
		font_menu = new Font("Arial",Font.BOLD, 18);
		//connexion();
		//showLogin(this);
		initialize();
		accueil();
		//new Connexion_GUI(client,this);
		
	}
	
	
	
	public void initialize()
	{
		new JFrame();	
		
		JPanel panel = new JPanel();
		//add(panel, BorderLayout.NORTH);
	
		JMenuBar menu_bar = new JMenuBar();
		
		mon_compte = new JMenu("Mon Compte");
		mon_compte.setFont(font_menu);
		mon_compte.addActionListener(this);
		menu_bar.add(mon_compte);
		deco = new JMenu("Déconnexion");
		deco.setFont(font_menu);
		deco.addActionListener(this);
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


		/*frame_accueil = new JFrame();
		frame_accueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connect = new JButton("Se connecter");
		inscription = new JButton("S'inscrire");
		connect.addActionListener(this);
		inscription.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0,0));
		panel.add(connect, BorderLayout.NORTH);
		panel.add(inscription, BorderLayout.SOUTH);
		
		frame_accueil.add(panel);
		
		frame_accueil.setVisible(true);
		frame_accueil.pack();*/
		
		
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
	        client.startClient(2, username.getText()+'\t'+password.getParent());
	        
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
        client.startClient(2, username.getText()+'\t'+password.getParent());
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

	}
}
