package ReactorEE.simulator;

import ReactorEE.swing.MainGUI;
import ReactorEE.swing.MultiplayerMainGUI;

public class GUIRefresher extends StepLooper {

	public GUIRefresher(PlantController controller, MainGUI GUI) {
		super(controller, GUI);
		waitPeriod = 50;
	}
	
	private static int MAX_NO_OF_SABOS = 3;
	private static int STEPS_TO_GAIN_SABO = 5;
	private int currentAvailableSabos = 0;
	private int lastSaboGivenAt = 0 ;
	
	@Override
	public void run(){
		((MultiplayerMainGUI) GUI).setGUIRefresher(this);
		
		while(true){
			try {
				if(controller.getPlant().isGameOver()){
					GUI.endGameHandler();
					break;
				}
				
				GUI.updateGUI();
				Thread.sleep(waitPeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NullPointerException e1){
			}
		}
		
	}
	
	/**
	 * Uses a sabotage if there are any currently available.
	 * @return Whether the sabotage was able to be done (if there were any sabotages to be used)
	 */
	public synchronized boolean useSabo(){
		if(currentAvailableSabos > 0){
			currentAvailableSabos--;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Calculates the number of currently available sabotages based on max number of sabotages available and how often they are given to the player.
	 * @return The Number of sabotages that the player currently has available.
	 */
	public synchronized int getNumberOfAvailableSabotages(){
		if(controller.getPlant().getTimeStepsUsed() == lastSaboGivenAt + STEPS_TO_GAIN_SABO){
			if(currentAvailableSabos < MAX_NO_OF_SABOS){
				currentAvailableSabos++;
			}
			lastSaboGivenAt = controller.getPlant().getTimeStepsUsed();
		}
		return currentAvailableSabos;
	}
	

}
