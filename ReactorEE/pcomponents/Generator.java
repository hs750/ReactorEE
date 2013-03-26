package ReactorEE.pcomponents;

/**
 * Generator class is used to calculate the power output of the plant
 * based on the current RPM of the Turbine. The generator is perfect,
 * i.e. it never breaks.
 * 
 * @author Lamprey
 */
public class Generator extends PlantComponent {
	private static final long serialVersionUID = -7558087247939142245L;

	private static final int DIVISOR = 123; //Random number to make score look better (not a multiple of 10/100/1000)
	
	private int powerOutput;
	private Turbine turbine;
	
	/**
	 * Creates a new non-breakable generator.
	 * 
	 * @param turbine the turbine that the power output calculations are based on
	 */
	public Generator(Turbine turbine) {
		super(0, 0, 0); //Perfect - never fails
		this.turbine = turbine;
		this.powerOutput = 0;
	}
	
	/**
	 * Updates the value of the power output based on the RPM of the turbine.
	 */
	@Override
	public void updateState() {
		powerOutput = turbine.getRpm() / DIVISOR;
	}
	
	/**
	 * 
	 * @return the power output.
	 */
	public int getPowerOutput() {
		return powerOutput;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = super.hashCode();
//		result = prime * result + powerOutput;
//		result = prime * result + ((turbine == null) ? 0 : turbine.hashCode());
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
		Generator other = (Generator) obj;
		if (powerOutput != other.powerOutput)
			return false;
		if (turbine == null) {
			if (other.turbine != null)
				return false;
		} else if (!turbine.equals(other.turbine))
			return false;
		return true;
	}
}
