package ReactorEE.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JButton;

import ReactorEE.Networking.HandshakeListener;
import ReactorEE.Networking.HandshakeRequest;
import ReactorEE.simulator.PlantController;
import ReactorEE.simulator.ReactorUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JTextField;

public class MultiplayerSelectionGUI {

	private JFrame frame;
	private JTextField txtOperatorIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultiplayerSelectionGUI window = new MultiplayerSelectionGUI();
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
	public MultiplayerSelectionGUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		JButton btnOperator = new JButton("Operator");
		btnOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				new HandshakeListener().run(new PlantController(new ReactorUtils()));
				}catch(Exception e1){
					e1.printStackTrace();}
			}
		});
		btnOperator.setBounds(310, 66, 117, 29);
		layeredPane.add(btnOperator);
		
		JButton btnSabateur = new JButton("Sabateur");
		btnSabateur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String suppliedIP = txtOperatorIP.getText();
					if(ReactorEE.Networking.SocketUtil.validateIP(suppliedIP) == true)
					{
						new HandshakeRequest().run(suppliedIP, new PlantController(new ReactorUtils()));
					}
					else 
					{
						JOptionPane.showMessageDialog(frame, suppliedIP + " is an invalid IP Address. Please enter a valid IPv4 address.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSabateur.setBounds(310, 124, 117, 29);
		layeredPane.add(btnSabateur);
		
		txtOperatorIP = new JTextField();
		try {
			txtOperatorIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			txtOperatorIP.setText("OpIP");
		}
		txtOperatorIP.setBounds(164, 123, 134, 28);
		layeredPane.add(txtOperatorIP);
		txtOperatorIP.setColumns(10);
	}
}
