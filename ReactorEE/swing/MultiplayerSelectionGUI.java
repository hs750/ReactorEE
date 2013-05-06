package ReactorEE.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JButton;

import ReactorEE.Networking.HandshakeListener;
import ReactorEE.Networking.HandshakeRequest;
import ReactorEE.Networking.Message;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.sound.Sound;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Component;

public class MultiplayerSelectionGUI {

	private JFrame frmMultiplayerConnection;
	private JTextField txtOperatorIP;
	boolean connectionActive = false;

	/**
	 * Create the gui.
	 */
	public MultiplayerSelectionGUI() {
		initialize();
		frmMultiplayerConnection.setVisible(true);
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		//Set properties of the frame
		frmMultiplayerConnection = new JFrame();
		frmMultiplayerConnection.setUndecorated(true);
		frmMultiplayerConnection.setTitle("Multiplayer connection");
		int windowHight = 300;
	    int windowWidth = 450;
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frmMultiplayerConnection.setBounds(screenSize.width/2-windowWidth/2, screenSize.height/2-windowHight/2, windowWidth, windowHight);
		frmMultiplayerConnection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMultiplayerConnection.setAlwaysOnTop(true);
		frmMultiplayerConnection.setResizable(false);
		JLayeredPane layeredPane = new JLayeredPane();
		frmMultiplayerConnection.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		//set background of frame
		java.net.URL imageURL = this.getClass().getClassLoader().getResource("ReactorEE/graphics/plantBackground.png");
        ImageIcon backgroundImageIcon = new ImageIcon(imageURL);
        
		final JButton btnOperator = new JButton("Operator");
		final JButton btnSabateur = new JButton("Saboteur");
		final JButton btnCancel = new JButton("Cancel");
		
		//Create buttons for selecting who to play as. 
		btnOperator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
				try{
				new HandshakeListener(MultiplayerSelectionGUI.this).start();
				btnOperator.setEnabled(false);
				btnSabateur.setEnabled(false);
				btnCancel.setEnabled(true);
				connectionActive = true;
				}catch(Exception e1){
					e1.printStackTrace();}
			}
		});
		btnOperator.setBounds(20, 60, 117, 29);
		layeredPane.add(btnOperator);
        
        
        
        btnSabateur.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnOperator.setEnabled(false);
				btnSabateur.setEnabled(false);
				btnCancel.setEnabled(true);
				Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
        		try {
        			String suppliedIP = txtOperatorIP.getText();
        			if(ReactorEE.Networking.SocketUtil.validateIP(suppliedIP) == true)
        			{
        				btnOperator.setEnabled(false);
        				btnSabateur.setEnabled(false);
        				btnCancel.setEnabled(true);
        				new HandshakeRequest(MultiplayerSelectionGUI.this,suppliedIP).start();
        				connectionActive = true;
        				
        			}
        			else 
        			{
        				JOptionPane.showMessageDialog(frmMultiplayerConnection, suppliedIP + " is an invalid IP Address. Please enter a valid IPv4 address.");
        			}
        		} catch (Exception e1) {
        			e1.printStackTrace();
        		}
        	}
        });
        btnSabateur.setBounds(20, 100, 117, 29);
        layeredPane.add(btnSabateur);
        
        btnCancel.setEnabled(false);
        
        
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
        		try {
        			btnOperator.setEnabled(true);
        			btnSabateur.setEnabled(true);
        			btnCancel.setEnabled(false);
        			connectionActive = false;
        			new Message().run("ANCHOVY KILL", Inet4Address.getLocalHost().getHostAddress(), SocketUtil.HANDSHAKE_PORT_NO);
        		} catch (Exception e1) {
        		}
        	}
        });
        btnCancel.setBounds(20, 140, 117, 29);
        layeredPane.add(btnCancel);
        
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(20, 180, 117, 28);
        layeredPane.add(btnBack);
        
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Sound.play(Sound.DEFAULT_MENU_BUTTON_CLICK);
        		frmMultiplayerConnection.dispose();
        		try {
        			if(connectionActive)
        			{
        				new Message().run("ANCHOVY KILL", Inet4Address.getLocalHost().getHostAddress(), SocketUtil.HANDSHAKE_PORT_NO);
        				connectionActive = false;
        			}
        		} catch (Exception e1) 
        		{
        		}
        		new GameTypeSelectionGUI();
        	}
        });
        
        //Text box for user to enter the ip sddress of the operator. 
        txtOperatorIP = new JTextField();
        txtOperatorIP.setHorizontalAlignment(SwingConstants.CENTER);
        txtOperatorIP.setBounds(147, 100, 177, 28);
        txtOperatorIP.setColumns(10);
        layeredPane.add(txtOperatorIP);
        
        txtOperatorIP.setText("Enter your foe's IP here.");
		
        //Find users UP address and set lebel text displaying it.
        JLabel lblNewLabel;
        String myIP = new String();
        try {
        myIP = "Your I.P. address is " + Inet4Address.getLocalHost().getHostAddress();		
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			myIP = "Could not find users IP";
		}
        
        lblNewLabel = new JLabel(myIP);
        lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblNewLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblNewLabel.setForeground(new Color(240, 255, 255));
        lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 13));
        lblNewLabel.setLabelFor(txtOperatorIP);
        lblNewLabel.setBounds(20, 0, 205, 55);
        layeredPane.add(lblNewLabel);
        JLabel backgroundImageLabel = new JLabel(backgroundImageIcon);
        backgroundImageLabel.setBackground(new Color(0, 153, 0));
        backgroundImageLabel.setBounds(0, 0, 450, 300);
        layeredPane.add(backgroundImageLabel);
	}
	
	/**
	 * Closes this window.
	 */
	public void close(){
		frmMultiplayerConnection.dispose();
	}
	/**
	 * 
	 * @return GUI frame.
	 */
	public JFrame getFrame(){
		return frmMultiplayerConnection;
	}
}
