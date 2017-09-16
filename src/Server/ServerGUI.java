/**
 * 
 */
package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Sebastien
 *
 */
public class ServerGUI extends JPanel implements ActionListener {

	private SingleServer server;
	private JButton connectButton;
	private boolean status;
	
	ServerGUI(SingleServer server){
		this.server = server;
		//Server initially stopped
		this.status = false;
		//Button to stop/run the server
		connectButton = new JButton("Run");
		add(connectButton);
		connectButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==connectButton) {
			if(!status) {		// If stopped
				server.startServer();
				connectButton.setText("Stop");
				status = !status;
			}
			else {
				server.stopServer();
				connectButton.setText("Run");
				status = !status;
			}
		}
		
	}
}
