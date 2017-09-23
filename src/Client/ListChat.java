/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author Valentin
 *
 */
public class ListChat extends JPanel implements ActionListener{

	private JButton send_message;
	private JTextField message;
	private GUI gui;
	
	ListChat(GUI gui)
	{
		this.gui = gui;
		
		//general settings
		setOpaque(false);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,100));
		
		//Container panel
		JPanel chat_messages_panel = new JPanel();
		chat_messages_panel.setOpaque(false);
		
		//Scroll panel with all the messages
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(chat_messages_panel);
		scrollpane.setOpaque(false);
		scrollpane.getViewport().setOpaque(false);
		
		add(scrollpane, BorderLayout.CENTER);
		
		
		//Send panel
		JPanel send_panel = new JPanel();
		send_panel.setLayout(new BorderLayout());
		message = new JTextField(1);
		message.setPreferredSize(new Dimension(200, 100));
		send_message = new JButton("Envoyer message");
		send_message.addActionListener(this);
		send_panel.add(message, BorderLayout.NORTH);
		send_panel.add(send_message, BorderLayout.SOUTH);
		add( send_panel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == send_message)
		{
			
		}
	}
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\chat.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
	
}
