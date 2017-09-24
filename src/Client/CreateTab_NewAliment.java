/**
 * 
 */
package Client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Valentin
 *
 */
public class CreateTab_NewAliment extends JPanel implements ActionListener{
	
	private JButton valider;
	private JTextField champ;
	private Editor tab;
	private Client client;
	private JLabel flag_icon;
	public boolean boolean_flag;
	public String aliment;
	private Font font = new Font("Verdana",Font.ITALIC,17);
	
	
	public CreateTab_NewAliment(Editor tab, Client client) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.tab = tab;
		
		aliment = new String("");
		
		boolean_flag = false;
		
		flag_icon = new JLabel();
		//flag_icon.setPreferredSize(new Dimension(25, 25));
		flag_icon.setIcon(new ImageIcon(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\ImageIcon\\R.gif").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		add(flag_icon);
		
		champ = new JTextField(10);
		champ.setFont(font);
		add(champ);
		
		
		
		valider = new JButton("Valider");
		valider.setFont(font);
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
			String food = champ.getText();

			client.sendRequest(3,food);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			boolean flag = client.getFoodFoundFlag();
			if(flag)
			{
				//add aliment to apercu
				boolean_flag = true;
				aliment = food;
				flag_icon.setIcon(new ImageIcon(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\ImageIcon\\G.gif").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
			}
			else
			{
				tab.newAliment(true, true, true, true, true, true, true, true, true);
				boolean_flag = false;
				aliment = "";
				flag_icon.setIcon(new ImageIcon(new ImageIcon(Paths.get(".").toAbsolutePath().normalize().toString()+"\\ImageIcon\\R.gif").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
				
			}
		}
		
	}
	
	

}
