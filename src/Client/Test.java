package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

public class Test {

	private JFrame frame;
	private JTextField text_field_user;
	private JTextField text_field_password;
	private JButton button_valider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Inscription");
		//frame.setBounds(100, 100, 450, 300);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(panel);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_6.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel_6.add(lblNewLabel_2);
		panel.add(panel_6, BorderLayout.NORTH);

		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_4.add(panel_3);
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2);
		
		JLabel label_user = new JLabel("User :");
		label_user.setHorizontalAlignment(SwingConstants.CENTER);
		
		text_field_user = new JTextField();
		text_field_user.setColumns(10);
		
		JLabel label_password = new JLabel("Password");
		label_password.setHorizontalAlignment(SwingConstants.CENTER);
		
		text_field_password = new JTextField();
		text_field_password.setColumns(10);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		panel_2.add(label_user);
		panel_2.add(text_field_user);
		panel_2.add(label_password);
		panel_2.add(text_field_password);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		button_valider = new JButton("Valider");
		panel_5.add(button_valider);
		
		frame.setVisible(true);
		frame.pack();
	}

}
