package ReactorEE.pcomponents;

/**
 * Pump is a plant component that pumps some amount of water based on pump's "on"
 * state and its RPM. There is a chance that a pump fails randomly. 
 * 
 * @author Lamprey
 */
public class Pump extends PlantComponent {

	private static final long serialVersionUID = -446684199807618671L;
	
	//DEFAULT_RPM is used when a rpm argument is not passed to the constructor
	private final static int DEFAULT_RPM = 0;
	private final static boolean DEFAULT_ON_STATE = true;
	private final static int MAX_RPM = 1000; 
	public final static int DEFAULT_FAILURE_RATE = 10; //1%
	public final static int DEFAULT_REPAIR_TIME = 5;
	private final static int MAX_FAILURE_RATE = 50; //5%
	
	private int ID;
	private int rpm;
	private boolean on;
	
	/**
	 * Constructs a pump with the selected ID.
	 * 
	 * @param ID the selected ID for the pump.
	 */
	public Pump(int ID) {
		super(DEFAULT_FAILURE_RATE, DEFAULT_REPAIR_TIME, MAX_FAILURE_RATE);
		this.ID = ID;
		setRpm(DEFAULT_RPM);
		this.on = DEFAULT_ON_STATE;
	}
	
	/**
	 * 
	 * @return the ID of this pump.
	 */
	public int getID()
	{
		return ID;
	}

	/**
	 * Note: If the pump is off, the RPM returned will be zero.
	 * 		 If the pump is broken, the RPM returned will also be zero.
	 * 
	 * @return the current RPM value of the pump
	 */
	public int getRpm() {
		return (!on || !this.isOperational()) ? 0 : rpm;
	}
	
	/**
	 * Sets a new value for the RPM of the pump
	 * 
	 * @param rpm the new value
	 */
	public void setRpm(int rpm) throws IllegalArgumentException {
		if (rpm <= MAX_RPM && rpm >= 0) {
			this.rpm = rpm;
		} else {
			throw new IllegalArgumentException("Pump rpm must be in the range [0 - " + MAX_RPM + "]");
		}
		if (rpm != 0 && !on) on = true;
	}
	
	/**
	 * 
	 * @return true if the pump is on
	 */
	public boolean isOn() {
		return on;
	}
	
	/**
	 * Sets the pump (on/off).
	 * 
	 * @param on true if the pump needs to be on, false if it needs to be off
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
	
	/**
	 * 
	 * @return the max RPM of the pump.
	 */
	public int getMaxRpm()
	{
		return MAX_RPM;
	}
	
	/**
	 * Update the state of the pump.
	 * 
	 * Increases the failure rate of the pump if appropriate.
	 */
	public void updateState() {
		increaseFailureRate();
	}
	
	/**
	 * Checks if the pump fails randomly.
	 * 
	 * @return true if the pump has failed
	 */
	public boolean checkFailure() {
		return super.checkFailure();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ID;
		result = prime * result + (on ? 1231 : 1237);
		result = prime * result + rpm;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pump other = (Pump) obj;
		if (ID != other.ID)
			return false;
		if (on != other.on)
			return false;
		if (rpm != other.rpm)
			return false;
		return true;
	}
}
