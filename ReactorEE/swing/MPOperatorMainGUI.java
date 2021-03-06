package ReactorEE.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import ReactorEE.Networking.Message;
import ReactorEE.Networking.SocketUtil;
import ReactorEE.simulator.PlantController;

/**
 * The GUI used by the Operator of the plant in a multiplayer game.
 */
public class MPOperatorMainGUI extends MainGUI {

	
	public MPOperatorMainGUI(PlantController plantController) {
		super(plantController);
		
		//disable gui componnets that are not needed in multiplayer
		btnLoad.setEnabled(false);
		btnLoad.setVisible(false);
		btnSave.setEnabled(false);
		btnSave.setVisible(false);
		
		//Move remaining buttons to fill the gap.
      	btnShowManual.setBounds(btnLoad.getX(), btnShowManual.getY(), btnShowManual.getWidth(), btnShowManual.getHeight());
      	btnShowScores.setBounds(btnSave.getX(), btnShowScores.getY(), btnShowScores.getWidth(), btnShowScores.getHeight());
      	
      	/**
      	 * Add an aditional action listener to newGame button.
      	 * Kills the networking thread. 
      	 */
      	btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Message().run("ANCHOVY KILL", Inet4Address.getLocalHost().getHostAddress(), SocketUtil.SABOTAGE_LISTENER_PORT_NO);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				MPOperatorMainGUI.this.plantController.getStepLooper().stopLoop();
				
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endGameHandler(){
		@SuppressWarnings("unused")
		EndGameGUI endGameGui = new EndGameGUI(this, plantController.getUIData().getScore());
		plantController.newMultiplayerGame(initialNameValue);
		updateGUI();
	}

}
