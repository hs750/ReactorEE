package ReactorEE.pcomponents;

import java.util.Random;
import java.io.Serializable;

import ReactorEE.model.Flow;





/**
 * PlantComponent is an abstract class that has fields and methods that apply
 * to most or all plant components. That allows for flexible changes to future
 * implementations (i.e. valves/generator that break randomly).
 * 
 * @author Lamprey
 */
public abstract class PlantComponent implements Serializable{

	private static final long serialVersionUID = -4184587415447732647L;
	
	//The values of the constants below are for illustration.
	public final static int DEFAULT_FAILURE_RATE = 10; //1%
	public final static int DEFAULT_REPAIR_TIME = 5; 
	public final static int MAX_FAILURE_RATE = 50; //5%
	public final static boolean DEFAULT_OPERATIONAL = true;
	public final static boolean DEFAULT_PRESSURISED = false;

	private int failureRate;
	private int maxFailureRate;
	private int repairTime;
	private boolean operational; //Possibly unnecessary
	private boolean pressurised;
	private PlantComponent input;
	private PlantComponent output;
	private Flow flowOut;
	private Random random;
	
	/**
	 * Creates a new operational PlantComponent object
	 * with the default chance to fail and turns to repair.
	 */
	protected PlantComponent() {
		this.failureRate    = DEFAULT_FAILURE_RATE;
		this.maxFailureRate = MAX_FAILURE_RATE;
		this.repairTime     = DEFAULT_REPAIR_TIME;
		this.operational    = DEFAULT_OPERATIONAL;
		this.pressurised    = DEFAULT_PRESSURISED;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new operational PlantComponent with
	 * the specified chance to fail and turns to repair.
	 * 
	 * @param failureRate  chance of a component failing randomly
	 * @param repairTime  number of steps needed to repair the component
	 * @param maxFailureRate 
	 */
	protected PlantComponent(int failureRate, int repairTime, int maxFailureRate) {
		this.failureRate    = failureRate;
		this.repairTime     = repairTime;
		this.maxFailureRate = maxFailureRate;
		this.operational = DEFAULT_OPERATIONAL;
		this.pressurised = DEFAULT_PRESSURISED;
		this.flowOut = new Flow();
		random = new Random();
	}
	

	
	/**
	 * Creates a new PlantComponent with
	 * the specified chance to fail, turns to repair and working condition.
	 * 
	 * @param failureRate the chance of the component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 * @param operational determines whether the component is initially
	 * 					  operational or not
	 * @param pressurised determines whether or not the component is pressurised.
	 */
	protected PlantComponent(int failureRate, int repairTime, boolean operational, boolean pressurised) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = operational;
		this.pressurised = pressurised;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new PlantComponent with
	 * the specified chance to fail, turns to repair and working condition.
	 * 
	 * @param failureRate the chance of the component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 * @param operational determines whether the component is initially
	 * 					  operational or not
	 * @param pressurised determines whether or not the component is pressurised.
	 * @param flow the flow out of the component
	 */
	protected PlantComponent(int failureRate, int repairTime, boolean operational, boolean pressurised, Flow flow) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = operational;
		this.pressurised = pressurised;
		this.flowOut = flow;
		random = new Random();
	}

	/**
	 * 
	 * @return the current chance of the component failing randomly
	 */
	public int getFailureRate() {
		return failureRate;
	}
	
	/**
	 * Sets a new value for failureRate.
	 * 
	 * @param failureRate the new value for failureRate
	 */
	public void setFailureRate(int failureRate) {
		this.failureRate = failureRate;
	}
	
	/**
	 * 
	 * @return the number of turns that it takes for a component to repair
	 */	
	public int getRepairTime() {
		return repairTime;
	}
	
	/**
	 * Changes the number of turns it take for a component to get repaired.
	 * 
	 * @param repairTime the number of turns required for this component 
	 * 					 to be repaired
	 */
	protected void setRepairTime(int repairTime) {
		this.repairTime = repairTime;
	}
	
	/**
	 * 
	 * @return the component connected to the input of this component.
	 */
	public PlantComponent getInput() {
		return this.input;
	}

	/**
	 * Changes the component connected to the input of this component.
	 * 
	 * @param input the component to connect to the input of this component
	 */
	public void setInput(PlantComponent input) {
		this.input = input;
	}

	/**
	 * 
	 * @return the component connected to the output of this component
	 */
	public PlantComponent getOutput() {
		return this.output;
	}

	/**
	 * Changes the component connected to the output of this component.
	 * 
	 * @param output the component to connect to the output of this component
	 */
	public void setOutput(PlantComponent output) {
		this.output = output;
	}

	/**
	 * 
	 * @return the flow object that stores the rate of flow out of this component
	 */
	public Flow getFlowOut() {
		return this.flowOut;
	}

	/**
	 * 
	 * @return true if the component is pressurised (i.e. Reactor or Condenser)
	 */
	public boolean isPressurised() {
		return this.pressurised;
	}

	/**
	 * 
	 * @return true if the component is operational (working correctly)
	 */
	public boolean isOperational() {
		return operational;
	}
	
	/**
	 * Sets the condition of the component - true if it is
	 * working correctly, false if it has failed.
	 * 
	 * @param operational the new state of the component
	 */
	public void setOperational(boolean operational) {
		this.operational = operational;
	}
	
	/**
	 * Updates the information about the component.
	 */
	abstract public void updateState();
	
	/**
	 * Runs all checks for the component and changes it's operational state if needed. 
	 */
	public boolean checkFailure() {
		int checkFailure = random.nextInt(1000);
		if(failureRate > checkFailure) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Increases the component's chance to fail by 0.1% per call.
	 * 
	 * Doesn't increase the chance to fail once MAX_FAILURE_RATE is reached.
	 */
	public void increaseFailureRate() {
		int currentFailureRate = this.getFailureRate();
		if (currentFailureRate < maxFailureRate) {
			this.setFailureRate(++currentFailureRate);
		}
	}

	public void setMaxFailureRate(int maxFailureRate) {
		this.maxFailureRate = maxFailureRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + failureRate;
		result = prime * result + ((flowOut == null) ? 0 : flowOut.hashCode());
		result = prime * result + maxFailureRate;
		result = prime * result + (operational ? 1231 : 1237);
		result = prime * result + (pressurised ? 1231 : 1237);
		result = prime * result + repairTime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PlantComponent))
			return false;
		PlantComponent other = (PlantComponent) obj;
		if (failureRate != other.failureRate)
			return false;
		if (flowOut == null) {
			if (other.flowOut != null)
				return false;
		} else if (!flowOut.equals(other.flowOut))
			return false;
		if (input == null) {
			if (other.input != null)
				return false;
		}
		if (maxFailureRate != other.maxFailureRate)
			return false;
		if (operational != other.operational)
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		}
		if (pressurised != other.pressurised)
			return false;
		if (repairTime != other.repairTime)
			return false;
		return true;
	}

}
