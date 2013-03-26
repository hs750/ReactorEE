package ReactorEE.pcomponents;

/**
 * The turbine is a class that makes the generator create power output.
 * The RPM is created based on the steam flow in. The turbine has a chance
 * to fail randomly.
 * 
 * @author Lamprey
 */
public class Turbine extends PlantComponent {
	private static final long serialVersionUID = 1106025692966179166L;

	public final static int DEFAULT_FAILURE_RATE = 10; //1%
	public final static int DEFAULT_REPAIR_TIME = 5;
	private final static int MAX_FAILURE_RATE = 25;
	private static final int MAX_TURBINE_RPM = 3500;
	
	private int rpm;
	private int maxSteamThroughput;
	
	/**
	 * 
	 * @param maxSteamThroughput  the maximum amount of steam that may enter the turbine
	 */
	public Turbine(int maxSteamThroughput) {
		super(DEFAULT_FAILURE_RATE, DEFAULT_REPAIR_TIME, MAX_FAILURE_RATE);
		this.maxSteamThroughput = maxSteamThroughput;
		this.rpm = 0;
	}
	
	/**
	 * Update the state of the Turbine.
	 * 
	 * Calculates the new value of RPM based on the steam flow in. It also 
	 * increases the failure rate if appropriate.
	 */
	@Override
	public void updateState() {
		int steamFlowIn = this.getInput().getFlowOut().getRate();
		// Need to create a couple of new doubles mid-calc here to make sure we get a decimal
		double linearMultiplier = 1 - (new Double((this.maxSteamThroughput - steamFlowIn))/new Double(this.maxSteamThroughput)); 
		int newRpm = (int) Math.round(new Double(MAX_TURBINE_RPM) * linearMultiplier);
		this.rpm = (this.isOperational()) ? newRpm : 0;
		
		increaseFailureRate();
	}
	
	/**
	 * Used only in Generator.
	 * 
	 * @return the value of rpm
	 */
	public int getRpm() {
		return (this.isOperational()) ? rpm : 0;
	}
	
	//for testing
	public void setRpm(int rpm){
		this.rpm = rpm;
	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = super.hashCode();
//		result = prime * result + maxSteamThroughput;
//		result = prime * result + rpm;
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turbine other = (Turbine) obj;
		if (maxSteamThroughput != other.maxSteamThroughput)
			return false;
		if (rpm != other.rpm)
			return false;
		return true;
	}
}