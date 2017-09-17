package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateTab extends JPanel implements ActionListener{

	private JButton button_envoie;
	
	CreateTab()
	{
		
		
		JLabel lblNewLabel = new JLabel("Nom de la recette :");
		add(lblNewLabel);
		
		JTextField textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(0, 2, 2, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Photo");
		panel_1.add(lblNewLabel_1);
		
		JEditorPane editorPane = new JEditorPane();
		panel_1.add(editorPane);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.CENTER);
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
