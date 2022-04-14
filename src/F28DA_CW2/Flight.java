package F28DA_CW2;

public class Flight implements IFlight 
{
	//Variables to store the details of the flights
	private String code;
	private Airport leave;
	private Airport arrive;
	private String leaveTime;
	private String arriveTime;
	private int cost;
	
	//Constructor of the class
	Flight(String code,Airport leave,String leaveTime, Airport arrive,String arriveTime,int cost)
	{
		//Assigning all the values passed in the parameter
		this.code= code;
		this.leave= leave;
		this.arrive= arrive;
		this.leaveTime= leaveTime;
		this.arriveTime= arriveTime;
		this.cost= cost;
	}
		
	//Function to return the code of the flight
	@Override
	public String getFlightCode() 
	{
		return code;
	}
	
	//Function to return the destination of the flight
	@Override
	public Airport getTo() 
	{
		return arrive;
	}
	
	//Function to return the departure place of the flight
	@Override
	public Airport getFrom() 
	{
		return leave;
	}
	
	//Function to return the departing time of the flight
	@Override
	public String getFromGMTime() 
	{
		return leaveTime;
	}

	//Function to return the arrival time of the flight
	@Override
	public String getToGMTime() 
	{
		return arriveTime;
	}

	//Function to return the cost of the flight
	@Override
	public int getCost() 
	{
		return cost;
	}

}
