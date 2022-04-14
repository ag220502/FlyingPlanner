package F28DA_CW2;

import java.util.ArrayList;
import java.util.List;

public class Journey implements IJourneyPartB<Airport, Flight>, IJourneyPartC<Airport, Flight> 
{
	//Declaring variables to store the data
	private List<String> flights;
	private List<String> stops;
	private int totalHop;
	private int totalCost;
	private int airTime;
	private int connecTime;
	
	//Constructor of the class
	public Journey()
	{
		//Initializing all the values to 0
		this.totalHop = 0;
		this.airTime=0;
		this.totalCost=0;
		//Initializing the lists
		this.stops=new ArrayList<String>();
		this.flights = new ArrayList<String>();
		this.connecTime = 0;
	}
	
	//Function to return the list of all the stops 
	@Override
	public List<String> getStops() 
	{
		return this.stops;
	}
	
	//Functions to set all the stops
	public void setStops(List<String> stops)
	{
		this.stops = stops;
	}

	//Function to return the all the flights included in the journey
	@Override
	public List<String> getFlights() 
	{
		return this.flights;
	}

	//Functions to set all the flights included in the journey
	public void setFlights(List<String> flights)
	{
		this.flights = flights;
	}

	//Function to return totalHop
	@Override
	public int totalHop() 
	{
		return this.totalHop;
	}
	
	//Function to set totalHop
	public void setTotalHop(int d) 
	{
		this.totalHop =d;
	}

	//Function to return the totalCost
	@Override
	public int totalCost() 
	{
		return this.totalCost;
	}
	
	//Function to set totalCost 
	public void setTotalCost(int d) 
	{
		this.totalCost = d;
	}

	//Function to return the air time
	@Override
	public int airTime() 
	{
		return this.airTime;
	}
	
	//Function to set the air time
	public void setAirTime(int airTime) 
	{
		this.airTime = airTime;
	}

	//Function to return the connecting time
	@Override
	public int connectingTime() 
	{
		return this.connecTime;
	}

	//Function to set the connecting time
	public void setConnectingTime(int time) 
	{
		this.connecTime = time;
	}

	//Function to set the total time of the journey
	@Override
	public int totalTime() 
	{
		return airTime()+connectingTime();
	}

}
