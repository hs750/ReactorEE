package ReactorEE.swing;

import ReactorEE.simulator.PlantController;

public class MPOperatorMainGUI extends MainGUI {

	public MPOperatorMainGUI(PlantController plantController) {
		super(plantController);
		
		//disable gui componnets that are not needed in multiplayer
		btnLoad.setEnabled(false);
		btnLoad.setVisible(false);
		btnSave.setEnabled(false);
		btnSave.setVisible(false);
		
		//Move remaining buttons
      	btnShowManual.setBounds(btnLoad.getX(), btnShowManual.getY(), btnShowManual.getWidth(), btnShowManual.getHeight());
      	btnShowScores.setBounds(btnSave.getX(), btnShowScores.getY(), btnShowScores.getWidth(), btnShowScores.getHeight());
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
