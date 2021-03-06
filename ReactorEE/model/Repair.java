package ReactorEE.model;

import java.io.Serializable;

import ReactorEE.pcomponents.*;


/**
 * Repair class keeps track of the components that are being repaired.
 * When a component's timeStepsRemaining reach 0, the component needs to be
 * removed from the beingRepaired list inside Plant.
 * 
 * @author Lamprey
 * @author Vel
 */
public class Repair implements Serializable {
	private static final long serialVersionUID = 1819944421888642516L;
	
	private PlantComponent plantComponent;
	private int timeStepsRemaining;
	
	/**
	 * 
	 * @param componentToRepair component that needs repairing 
	 */
	public Repair (PlantComponent componentToRepair) {
		this.plantComponent     = componentToRepair;
		this.timeStepsRemaining = componentToRepair.getRepairTime();
	}
	
	/**
	 * Decrements the time remaining until the component is repaired 
	 */
	public void decTimeStepsRemaining() {
		if(timeStepsRemaining>0)
		    timeStepsRemaining--;
		else
		    timeStepsRemaining = 0;
	}
	
	/**
	 * 
	 * @return number of time steps remaining
	 */
	public int getTimeStepsRemaining() {
		return timeStepsRemaining;
	}
	
	/**
	 * 
	 * @return plant component 
	 */
	public PlantComponent getPlantComponent() {
		return plantComponent;
	}
	
}