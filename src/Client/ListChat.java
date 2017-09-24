/**
 * 
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private Client client;
	private JScrollPane scrollpane;
	
	ListChat(GUI gui,Client client)
	{
		this.gui = gui;
		this.client = client;
		//general settings
		setOpaque(false);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,100));
		
		//Container panel
		JPanel chat_messages_panel = new JPanel();
		chat_messages_panel.setOpaque(false);
		
		//Scroll panel with all the messages
		scrollpane = new JScrollPane();
		scrollpane.setViewportView(chat_messages_panel);
		scrollpane.setOpaque(false);
		scrollpane.getViewport().setOpaque(false);
		add(scrollpane, BorderLayout.CENTER);
		
		
		//Send panel
		JPanel send_panel = new JPanel();
		send_panel.setLayout(new BorderLayout());
		message = new JTextField(1);
		message.setPreferredSize(new Dimension(200, 60));
		send_message = new JButton("Envoyer message");
		send_message.addActionListener(this);
		send_panel.add(message, BorderLayout.NORTH);
		send_panel.add(send_message, BorderLayout.SOUTH);
		add( send_panel, BorderLayout.SOUTH);
	}
	
	public void messageReceived(String data)
	{
		String[] parts = data.split("\t");
		String sender = parts[0];
		String msg = parts[1];
		String msgToDisplay = "@"+sender+": "+msg;
		System.out.println("Display: "+msgToDisplay);
		JPanel chatPost = new JPanel();
		JLabel post = new JLabel(msgToDisplay);
		chatPost.add(post);
		//chatPost.setOpaque(false);
		scrollpane.add(chatPost);
		gui.revalidate();
		gui.repaint();
	}
	
	public void sendMessage(String data)
	{
		client.sendRequest(12, data);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if( s == send_message)
		{
			String typed = message.getText();
			message.setText("");
			// Look for a recipient thanks to the symbol "@"
			int recipientBegin = typed.indexOf("@");
			int recipientEnd;
			if(recipientBegin != -1) {
				recipientEnd = typed.indexOf(" ",recipientBegin);
				String recipient = typed.substring(recipientBegin+1, recipientEnd);
				String msg = typed.substring(recipientEnd+1);
				sendMessage(recipient+"\t"+msg);
			}
			else {
				sendMessage("\t"+typed);
			}
		}
	}
	
	
    public void paintComponent(Graphics g) {
    	// Add a background image
    	Image bg = new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\chat.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(),this);
    }
	
}
