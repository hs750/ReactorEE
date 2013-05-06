package ReactorEE.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ReactorEE.Networking.Message;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.simulator.GUIRefresher;
import ReactorEE.simulator.PlantController;
import ReactorEE.sound.Sound;
import javax.swing.SwingConstants;

/**
 * The GUI used by the saboteur during a multiplayer game.
 *
 */
public class MultiplayerMainGUI extends MainGUI{

	private String operatorIP;
	JLabel lblAvailableSabos = new JLabel("0");
	GUIRefresher guir = null;
	
	/**
	 * Initialise the GUI
	 * @param plantController plant controller to use in the game
	 * @param IP The IP address of the operator. 
	 */
	public MultiplayerMainGUI(final PlantController plantController, String IP) {
		super(plantController);
		this.operatorIP = IP;
		
		//Change background of GUI to alternate image.
		java.net.URL imageURL = this.getClass().getClassLoader().getResource("ReactorEE/graphics/plantBackgroundSabo.png");
        ImageIcon backgroundImageIcon = new ImageIcon(imageURL);
        JLabel MPBackgroundImageLabel = new JLabel(backgroundImageIcon);
        MPBackgroundImageLabel.setBackground(new Color(0, 153, 0));
        MPBackgroundImageLabel.setBounds(0, 0, 1040, 709);
        layeredPane.remove(backgroundImageLabel);
        layeredPane.add(MPBackgroundImageLabel);
        layeredPane.setLayer(MPBackgroundImageLabel, 0);
        
        ImageIcon breakPumpIcon = new ImageIcon(MainGUI.class.getResource("/ReactorEE/graphics/breakButton.png"));
        Image breakPump1Image = breakPumpIcon.getImage().getScaledInstance(59, 57,  java.awt.Image.SCALE_SMOOTH);
        breakPumpIcon = new ImageIcon(breakPump1Image);
        btnRepairPump1.setIcon(breakPumpIcon);
        btnRepairPump2.setIcon(breakPumpIcon);
        btnRepairPump3.setIcon(breakPumpIcon);
        btnRepairTurbine.setIcon(breakPumpIcon);
        btnRepairOperatingSoftware.setIcon(breakPumpIcon);
       
        //Change actionlisteners of repair buttons to send a break comand over the network rather than break the component localy. 
		btnRepairPump1.removeActionListener(btnRepairPump1.getActionListeners()[0]);
		btnRepairPump1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Sound.play(Sound.DEFAULT_BUTTON_CLICK);
                try {
                	if(guir.useSabo())
                		new Message().run("pump1", operatorIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();
					JOptionPane.showMessageDialog(getFrame(), "Unable to connect to Operator", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		btnRepairPump2.removeActionListener(btnRepairPump2.getActionListeners()[0]);
		btnRepairPump2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Sound.play(Sound.DEFAULT_BUTTON_CLICK);
            	try {
                	if(guir.useSabo())
                		new Message().run("pump2", operatorIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();
					JOptionPane.showMessageDialog(getFrame(), "Unable to connect to Operator", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		btnRepairPump3.removeActionListener(btnRepairPump3.getActionListeners()[0]);
		btnRepairPump3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Sound.play(Sound.DEFAULT_BUTTON_CLICK);
            	try {
                	if(guir.useSabo())
                		new Message().run("pump3", operatorIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();
					JOptionPane.showMessageDialog(getFrame(), "Unable to connect to Operator", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		btnRepairTurbine.removeActionListener(btnRepairTurbine.getActionListeners()[0]);
		btnRepairTurbine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Sound.play(Sound.DEFAULT_BUTTON_CLICK);
            	try {
                	if(guir.useSabo())
                		new Message().run("turbine", operatorIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();
					JOptionPane.showMessageDialog(getFrame(), "Unable to connect to Operator", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		btnRepairOperatingSoftware.removeActionListener(btnRepairOperatingSoftware.getActionListeners()[0]);
		btnRepairOperatingSoftware.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Sound.play(Sound.DEFAULT_BUTTON_CLICK);
            	try {
                	if(guir.useSabo())
                		new Message().run("operator software", operatorIP, SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (Exception e1) {e1.printStackTrace();
					JOptionPane.showMessageDialog(getFrame(), "Unable to connect to Operator", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		//disable step button as sabateur cannot controll playing of the game. keeping it allows saboteur to see whether the game is playing or not.
		btnStep.removeActionListener(btnStep.getActionListeners()[0]);
        lblAvailableSabos.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//initialise label displaying the number of sabtages that that sabateur has.
        lblAvailableSabos.setFont(new Font("Tahoma", Font.PLAIN, 30));
        layeredPane.setLayer(lblAvailableSabos, 1);
        lblAvailableSabos.setBounds(90, 493, 315, 59);
        lblAvailableSabos.setFont(new Font("OCR A Std", Font.PLAIN, 20));
        layeredPane.setLayer(lblAvailableSabos, 2);
        lblAvailableSabos.setBounds(75, 497, 315, 59);

        layeredPane.add(lblAvailableSabos);
        
        //disable gui componnets that are not needed in multiplayer
      	btnLoad.setEnabled(false);
      	btnLoad.setVisible(false);
      	btnSave.setEnabled(false);
      	btnSave.setVisible(false);
      	//Move remaining buttons
      	btnShowManual.setBounds(btnLoad.getX(), btnShowManual.getY(), btnShowManual.getWidth(), btnShowManual.getHeight());
      	btnShowScores.setBounds(btnSave.getX(), btnShowScores.getY(), btnShowScores.getWidth(), btnShowScores.getHeight());
      	
      	//Add action listener to new game button to kill network communication thread. 
      	btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Message().run("ANCHOVY KILL", Inet4Address.getLocalHost().getHostAddress(), SocketUtil.GAMESTATE_LISTENER_PORT_NO);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Reset game values to default - makes it obvious that the multiplayer game is no longer active.
				plantController.newMultiplayerGame(initialNameValue);
			}
		});
	}
	
	@Override
	/**
	 * Only display the endgameGUI, dont do anything else.
	 */
	public void endGameHandler(){
		@SuppressWarnings("unused")
		EndGameGUI endGameGui = new EndGameGUI(this, plantController.getUIData().getScore());
	}
	
	@Override
	/**
	 * {@inheritJavaDoc}
	 */
	public void updateGUI(){
		super.updateGUI();
		if(lblAvailableSabos != null && guir!= null){
			lblAvailableSabos.setText(""+guir.getNumberOfAvailableSabotages());
		}
		
	}
	
	/**
	 * 
	 * @param guir thread which will continuously refresh game GUI. 
	 */
	public void setGUIRefresher(GUIRefresher guir){
		this.guir = guir;
	}

	
}

