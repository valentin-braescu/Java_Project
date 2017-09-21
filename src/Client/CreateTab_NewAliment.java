/**
 * 
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Valentin
 *
 */
public class CreateTab_NewAliment extends JPanel implements ActionListener{
	
	private JButton valider;
	private JTextField champ;
	private CreateTab tab;
	private Client client;
	
	
	public CreateTab_NewAliment(CreateTab tab, Client client) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.tab = tab;
		
		champ = new JTextField(10);
		add(champ);
		
		valider = new JButton("Valider");
		valider.addActionListener(this);
		add(valider);
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == valider)
		{
			// Search food on the server
			client.sendRequest(3,champ.getText());
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean flag = client.getFoodFoundFlag();
			
			if(flag)
			{
				//add aliment to apercu
				tab.aliments.addLast(champ.getText());
			}
			else
			{
				tab.newAliment(true, true, true, true, true, true, true, true, true);
			}
		}
		
	}
	
	

}
