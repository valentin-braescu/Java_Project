/**
 * 
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Valentin
 *
 */
public class Wall extends JPanel implements ActionListener{

	private JButton actualiser;
	private LinkedList<Recette> fil; 
	
	Wall()
	{
		actualiser = new JButton("Actualiser");
		actualiser.addActionListener(this);
		add(actualiser);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object s=e.getSource();
		if( s == actualiser)
		{
			
		}
	} 
	
	private void affciher()
	{
		
	}
	
	
}
