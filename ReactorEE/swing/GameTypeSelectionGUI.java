package ReactorEE.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;

import ReactorEE.simulator.SinglePlayerInit;

public class GameTypeSelectionGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTypeSelectionGUI window = new GameTypeSelectionGUI();
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
	public GameTypeSelectionGUI() {
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
		
		JButton btnSinglePlayer = new JButton("Single Player");
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SinglePlayerInit();
				
			}
		});
		btnSinglePlayer.setBounds(143, 93, 117, 29);
		layeredPane.add(btnSinglePlayer);
		
		JButton btnMultiplayer = new JButton("Multiplayer");
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MultiplayerSelectionGUI();
			}
		});
		btnMultiplayer.setBounds(143, 154, 117, 29);
		layeredPane.add(btnMultiplayer);
	}
}
