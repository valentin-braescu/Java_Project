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

/**
 * @author Valentin
 *
 */
public class ListChat extends JPanel implements ActionListener{

	private JButton send_message;
	
	ListChat()
	{
		setOpaque(false);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150,100));
		/*JPanel chat_messages_panel = new JPanel();
		chat_messages_panel.setOpaque(false);
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(chat_messages_panel);
		scrollpane.setOpaque(false);
		
		add(scrollpane, BorderLayout.CENTER);*/
		
		
		send_message = new JButton("Envoyer message");
		send_message.addActionListener(this);
		add( send_message, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon("D:\\ISMIN\\S5\\Advanced_Java\\Java_Project\\chat.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
	
}
