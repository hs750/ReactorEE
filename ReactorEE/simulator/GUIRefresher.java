package ReactorEE.simulator;

import ReactorEE.swing.MainGUI;

public class GUIRefresher extends StepLooper {

	public GUIRefresher(PlantController controller, MainGUI GUI) {
		super(controller, GUI);
	}
	
	@Override
	public void run(){
		while(true){
			GUI.updateGUI();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
