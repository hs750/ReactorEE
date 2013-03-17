package ReactorEE.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import javax.swing.JButton;

import ReactorEE.Networking.HandshakeListener;
import ReactorEE.Networking.HandshakeRequest;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MultiplayerSelectionGUI {

	private JFrame frame;

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
	 * Initialize the contents of the frame.
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
				new HandshakeListener();
			}
		});
		btnOperator.setBounds(310, 66, 117, 29);
		layeredPane.add(btnOperator);
		
		JButton btnSabatur = new JButton("Sabatur");
		btnSabatur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HandshakeRequest();
			}
		});
		btnSabatur.setBounds(310, 124, 117, 29);
		layeredPane.add(btnSabatur);
	}

}