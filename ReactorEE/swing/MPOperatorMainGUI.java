package ReactorEE.swing;

import ReactorEE.simulator.PlantController;

public class MPOperatorMainGUI extends MainGUI {

	public MPOperatorMainGUI(PlantController plantController) {
		super(plantController);
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
