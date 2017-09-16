package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CreateTab extends JPanel implements ActionListener{

	private JButton button_envoie;
	
	CreateTab()
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s = e.getSource();
		if( s == button_envoie)
		{
			
		}
	}
}
