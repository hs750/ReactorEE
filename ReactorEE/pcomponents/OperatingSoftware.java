package ReactorEE.pcomponents;

import java.util.Random;

/**
 * This class implements the software component functionality of the plant.
 * When a command is given to the plant for execution, the corresponding method is
 * invoked within this class; the method stores the required information if the
 * software is operational or stores either random command or no command if the 
 * software is not operational.
 * 
 * The PlantController class contains a reference to an object of this class and
 * when a command in the plant has to be executed, its type and data is taken from
 * this class.
 * 
 * This class acts as a command storage location and its failure is implemented by
 * storing a wrong command or none.
 * 
 * @author Velislav
 */
public class OperatingSoftware extends PlantComponent {
    
	private static final long serialVersionUID = 2499545059142628546L;
	public final static int DEFAULT_FAILURE_RATE = 10; //1%
    public final static int DEFAULT_REPAIR_TIME = 3;
    private final static int MAX_FAILURE_RATE = 50; //5%
    
    //controls the chance of doing nothing relative to storing different command
    //the number can be set between 0 and 100 - the higher its value - the higher
    //the possibility of storing no command. By default the chances are equal
    private final double DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT = 1;//out of 100
    
    //the available commands that can be stored by the operating software
    public enum OperationRequest 
    {DoNothing, SetControlRods, SetPumpRpm, SetValve, RepairTurbine, RepairPump, SetPumpOnOff, Quench}
    
    private OperationRequest requestedOperation = OperationRequest.DoNothing;
    
    private int valveID;
    private boolean open;
    private int pumpID;
    private boolean on;
    private int rpm;
    private int percentageLowered;
 
    private Random random = new Random();
    
    
    

    /**
     * Creates new OperatingSoftware object with default values for its
     * failure rate, repair time and max failure rate
     */
    public OperatingSoftware()
    {
        super(DEFAULT_FAILURE_RATE, DEFAULT_REPAIR_TIME, MAX_FAILURE_RATE);
    }
    
    
    /**
     *  If the operating software has failed, this method either stores no command or
     *  stores a random command and generates its data.
     */
    private void failedSoftwareResponse()
    {
        //if a random number is less than the DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT value
        if(random.nextInt(101)<DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT)
            //store no command
            requestedOperation = OperationRequest.DoNothing;
        else
        {//else pick a command to swap with from the available ones
            int swapWith = 1 + random.nextInt(OperationRequest.values().length - 2);
            
            switch(OperationRequest.values()[swapWith])
            {//set the value of the picked command and
             //depending on it generate its data randomly 
                
                case SetControlRods: 
                    requestedOperation = OperationRequest.SetControlRods;
                    percentageLowered = random.nextInt(101);//random rods level generation
                    break;
                    
                case SetPumpOnOff:
                    requestedOperation = OperationRequest.SetPumpOnOff;
                    pumpID = 1 + random.nextInt(3);//random pump id generation
                    on = random.nextBoolean();//random on state generation
                    break;
                    
                case SetPumpRpm:
                    requestedOperation = OperationRequest.SetPumpRpm;
                    rpm = random.nextInt(1001);//random rpm generation
                    break;
                    
                case SetValve: 
                    requestedOperation = OperationRequest.SetValve;
                    valveID = 1 + random.nextInt(2);//random valve id generation
                    open = random.nextBoolean();//random open state generation
                    break;
                    
                case RepairTurbine:
                    requestedOperation = OperationRequest.RepairTurbine;
                    break;
                    
                case RepairPump:
                    requestedOperation = OperationRequest.RepairPump;
                    pumpID = 1 + random.nextInt(2);//random pump id generation
                    break;
                    
                default://if for some reason something goes wrong store no command
                    requestedOperation = OperationRequest.DoNothing;
                    break; 
            }
        }
        
    }
    
    
    
    /**
     * Update the state of the operating software.
     * 
     * Increases the failure rate of the operating software if appropriate.
     */
    public void updateState() {
        super.increaseFailureRate();
    }
    
    public int getFailureRate(){
    	return super.getFailureRate();
    }
    /**
     * 
     * @return whether or not the operating software has failed
     */
    public boolean checkFailure() {
        return super.checkFailure();
    }
    
    
    
    /**
     * Stores a valve state if the software is working properly,
     * otherwise calls the appropriate method.
     * 
     * @param valveID 
     * @param open   state of the valve
     */
    public synchronized void setValve(int valveID, boolean open)
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.SetValve;
            this.valveID = valveID;
            this.open = open;
        }
        else
            {failedSoftwareResponse();}//!
        
    }
    
    /**
     * Stores a pump on/off state if the software is working properly,
     * otherwise calls the appropriate method.
     * 
     * @param pumpID
     * @param on     state of the pump
     */
    public synchronized void setPumpOnOff(int pumpID, boolean on)
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.SetPumpOnOff;
            this.pumpID = pumpID;
            this.on = on;
        }
        else
            failedSoftwareResponse();
    }
    
    /**
     * Stores a pump rmp if the software is working properly,
     * otherwise calls the appropriate method.
     * 
     * @param pumpID
     * @param rpm    speed at which the pump is running
     */
    public synchronized void setPumpRpm(int pumpID, int rpm)
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.SetPumpRpm;
            this.pumpID = pumpID;
            this.rpm = rpm;
        }
        else
            failedSoftwareResponse();
        
    }
    
    /**
     * Stores the control rods level if the software is working properly,
     * otherwise calls the appropriate method.
     * 
     * @param percentageLowered  the level to which the rods should be set
     */
    public synchronized void setControlRods(int percentageLowered)
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.SetControlRods;
            this.percentageLowered = percentageLowered;
        }
        else
            failedSoftwareResponse();
    }
    
    /**
     * Stores a reactor quench command if the software is working properly,
     * otherwise calls the appropriate method.
     *
     */
    public synchronized void quench(){
    	if(super.isOperational()){
    		requestedOperation = OperationRequest.Quench;
    	}
    	else 
    		failedSoftwareResponse();
    }
    
    /**
     * Stores a turbine repair command if the software is working properly,
     * otherwise calls the appropriate method.
     */
    public synchronized void repairTurbine()
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.RepairTurbine;
        }
        else
            failedSoftwareResponse(); 
    }
    
    /**
     * Stores a pump repair command and id if the software is working properly,
     * otherwise calls the appropriate method.
     */
    public synchronized void repairPump(int pumpID)
    {
        if(super.isOperational())
        {
            requestedOperation = OperationRequest.RepairPump;
            this.pumpID = pumpID;
        }
        else
            failedSoftwareResponse();
        
    }
    
    /* getters section */
    
    /**
     * @return the default failure rate
     */
    public synchronized int getDefaultFailureRate()
    {
        return DEFAULT_FAILURE_RATE;
    }

    /**
     * @return the default repair time
     */
    public static synchronized int getDefaultRepairTime()
    {
        return DEFAULT_REPAIR_TIME;
    }

    /**
     * @return the maximum failure rate
     */
    public static synchronized int getMaxFailureRate()
    {
        return MAX_FAILURE_RATE;
    }

    /**
     * @return the chance of doing either nothing, or executing a different command
     */
    public synchronized double getDO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT()
    {
        return DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT;
    }

    /**
     * @return the requested Operation
     */
    public synchronized OperationRequest getRequestedOperation()
    {
        return requestedOperation;
    }

    /**
     * @return the valveID
     */
    public synchronized int getValveID()
    {
        return valveID;
    }

    /**
     * @return whether or not a valve is open
     */
    public synchronized boolean isOpen()
    {
        return open;
    }

    /**
     * @return the pumpID
     */
    public synchronized int getPumpID()
    {
        return pumpID;
    }

    /**
     * @return whether or not a pump is on
     */
    public synchronized boolean isOn()
    {
        return on;
    }

    /**
     * @return the rpm of the pump
     */
    public synchronized int getRpm()
    {
        return rpm;
    }

    /**
     * @return the rod level
     */
    public synchronized int getPercentageLowered()
    {
        return percentageLowered;
    }


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = super.hashCode();
//		long temp;
//		temp = Double.doubleToLongBits(DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result + (on ? 1231 : 1237);
//		result = prime * result + (open ? 1231 : 1237);
//		result = prime * result + percentageLowered;
//		result = prime * result + pumpID;
//		result = prime
//				* result
//				+ ((requestedOperation == null) ? 0 : requestedOperation
//						.hashCode());
//		result = prime * result + rpm;
//		result = prime * result + valveID;
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
		OperatingSoftware other = (OperatingSoftware) obj;
		if (Double.doubleToLongBits(DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT) != Double
				.doubleToLongBits(other.DO_NOTHING_OR_DIFFERENT_COMMAND_LIMIT))
			return false;
		if (on != other.on)
			return false;
		if (open != other.open)
			return false;
		if (percentageLowered != other.percentageLowered)
			return false;
		if (pumpID != other.pumpID)
			return false;
		if (requestedOperation != other.requestedOperation)
			return false;
		if (rpm != other.rpm)
			return false;
		if (valveID != other.valveID)
			return false;
		return true;
	}
}
