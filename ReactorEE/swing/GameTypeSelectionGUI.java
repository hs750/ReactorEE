package ReactorEE.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;

import ReactorEE.simulator.SinglePlayerInit;
import ReactorEE.sound.Sound;

public class GameTypeSelectionGUI {

	private JFrame frmErr;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTypeSelectionGUI window = new GameTypeSelectionGUI();
					window.frmErr.setVisible(true);
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
		frmErr.setVisible(true);
	}

	public void setVisible(boolean visible)
	{
		frmErr.setVisible(visible);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmErr = new JFrame();
		frmErr.setUndecorated(true);
		frmErr.setTitle("Game type selection");
		int windowHight = 300;
		int windowWidth = 450;
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frmErr.setBounds(screenSize.width/2-windowWidth/2, screenSize.height/2-windowHight/2, windowWidth, windowHight);
		frmErr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmErr.setResizable(false);
		JLayeredPane layeredPane = new JLayeredPane();
		frmErr.getContentPane().add(layeredPane, BorderLayout.CENTER);

        Sound.play("menu");

		java.net.URL imageURL = this.getClass().getClassLoader().getResource("ReactorEE/graphics/plantBackground.png");
        ImageIcon backgroundImageIcon = new ImageIcon(imageURL);
        
		JButton btnSinglePlayer = new JButton("Single Player");
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
				new SinglePlayerInit();
				frmErr.dispose();
			}
		});
		btnSinglePlayer.setBounds(161, 90, 117, 29);
		layeredPane.add(btnSinglePlayer);
        
        JButton btnMultiplayer = new JButton("Multiplayer");
        btnMultiplayer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
        		new MultiplayerSelectionGUI();
        		frmErr.dispose();
        	}
        });
        btnMultiplayer.setBounds(161, 155, 117, 29);
        layeredPane.add(btnMultiplayer);
        JLabel backgroundImageLabel = new JLabel(backgroundImageIcon);
        backgroundImageLabel.setBackground(new Color(0, 153, 0));
        backgroundImageLabel.setBounds(0, 0, 450, 300);
        layeredPane.add(backgroundImageLabel);
        
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        layeredPane.setLayer(btnExit, 1);
        btnExit.setBounds(161, 243, 117, 29);
        layeredPane.add(btnExit);
	}
}
