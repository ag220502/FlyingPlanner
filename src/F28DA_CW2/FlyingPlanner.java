package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class FlyingPlanner implements IFlyingPlannerPartB<Airport,Flight>, IFlyingPlannerPartC<Airport,Flight> 
{

	//Creating the object of the class of the graph
	Graph<Airport, Flight> graph;
	
	//Constructor of the class
	public FlyingPlanner()
	{
		//Initializing the graph object
		graph = new SimpleDirectedWeightedGraph<>(Flight.class);
	}
	
	//Method to populate the graph using the FlightsReader object  
	@Override
	public boolean populate(FlightsReader fr) 
	{
		//Calling the functions from FlightsReader class to get the list of the Airports and the Flights
		//And Calling the populate function with HashSets as the parameters to populate the graph
		//If the function returns true, returning true 
		if(populate(fr.getAirports(),fr.getFlights()))
		{
			return true;
		}
		//Else returning false
		return false;
	}

	//Declaring private function to populate the vertices of the graph by passing the HashSet containing the list of Airports      
	private boolean populateAirports(HashSet<String[]> airports)
	{
		//Running for each loop to access each position in the HashSet
		for(String[] airString : airports)
		{
			//Creating the object of the Airport class to store it in the graph
			//Passing the airport code and the name of airport as argument in the constructor
			Airport airport = new Airport(airString[0],airString[1]);
			//Calling the built in function to add vertex in the graph
			//Checking if the function returns true or false
			//if the function returns false then returning false from the function
			if(!graph.addVertex(airport))
			{
				return false;
			}
		}
		//Checking the number of the vertices in the graph and the size of HashSet in parameter 
		if(graph.vertexSet().size()==airports.size())
		{
			//if they are same then returning true
			return true;
		}
		//Else Returning false
		return false;
	}
	
	//Declaring private function to populate the edges of the graph by passing the HashSet containing the list of flights
	private boolean populateFlights(HashSet<String[]> flights)
	{
		//Running for each loop to access all the flights in the HashSet 
		for(String[] fly : flights)
		{
			//Declaring variable to store the cost of the flight
			int cost=0;
			//Converting the cost into integer from the string
			try
			{
				cost = Integer.parseInt(fly[5]);
				//Creating the object of Airport class to store the leaving airport of the flight
				//In this we are using the airport function to find the object using the airport code
				Airport leaveAirport = airport(fly[1]);
				//Here, also we are doing the same step but for the arrival airport
				Airport arriveAirport = airport(fly[3]);
				//Checking if the both airport are present or not
				if(leaveAirport==null || arriveAirport == null)
				{
					//Even if one airport is not present then returning false
					return false;
				}
				//Creating the object of flight class to store the details in the edge of the graph 
				Flight flight = new Flight(fly[0],leaveAirport,fly[2],arriveAirport,fly[4],cost);
				//Calling the built in function of the Graph class to store the details of the edge
				//if the function returns false then we are returning false 
				if(!graph.addEdge(leaveAirport, arriveAirport, flight))
				{
					return false;
				}
			}
			catch(Exception e)
			{
				//If the exception is thrown then returning false
				System.out.println("Cost is not in Numbers");
				return false;
			}
			
		}
		//Checking if the number of edges is equal to the size of HashSet of the flights  
		if(graph.edgeSet().size()==flights.size())
		{
			//If they are same then returning true
			return true;
		}
		//Else returning false
		return false;
	}
		
	//Method to populate the graph using the HashSets of the list of Airports and the Flights 
	@Override
	public boolean populate(HashSet<String[]> airports, HashSet<String[]> flights) 
	{
		//Calling the functions by passing HashSets as the argument
		//In first function call we are passing the HashSet which contains list of the airports in form of String array
		//In Second function call we are passing the HashSet which contains list of the flights in the String array format
		//If the both function call returns true then returning true from this function
		if(populateAirports(airports) && populateFlights(flights))
		{
			return true;
		}
		//Else Returning false
		return false;
	}

	//Returns the airport object if the code of the airport is same
	@Override
	public Airport airport(String code) 
	{
		//Storing all the vertices in the Set 
		Set<Airport> airportSet = graph.vertexSet();
		//Running for each loop to access each element
		for(Airport airport : airportSet)
		{
			//If the code of the airport in graph is equal to the code passed as argument then returning the object 
			if(airport.getCode().equalsIgnoreCase(code))
			{
				return airport;
			}
		}
		//Else returning null
		return null;
	}

	//Returns the flight object if the code of the flight is same
	@Override
	public Flight flight(String code) 
	{
		//Storing all the edges in the Set
		Set<Flight> flightSet = graph.edgeSet();
		//Running for each loop to access each element
		for(Flight flight : flightSet)
		{
			//If the code of the flight in graph is equal to the code passed as argument then returning the object
			if(flight.getFlightCode().equalsIgnoreCase(code))
			{
				return flight;
			}
		}
		//Else returning null
		return null;
	}

	
	//Function to get all the flights and storing it in journey object
	private void setJFlight(List<Flight> fly,Journey j)
	{
		//Taking the list to store it temporarily in String format
		List<String> flyString = new ArrayList<String>();
		//Running for loop for all the edges
		for(Flight flyHelp : fly)
		{
			//Adding the flight code in the List of strings
			flyString.add(flyHelp.getFlightCode());
		}
		//Setting the data in the journey class
		j.setFlights(flyString);
	}
	
	//Function to calculate the least cost and return the journey
	@Override
	public Journey leastCost(String from, String to) throws FlyingPlannerException
	{
		//Calling the common function to calculate the least cost and returning journey 
		return leastCostReturn(from,to,null);
	} 

	//Function to get all the stops from graph path and storing it in journey object
	private void setJStops(List<Airport> airp,Journey j)
	{
		//Taking the list to store it temporarily in String format
		List<String> airString = new ArrayList<String>();
		//Running for loop for all the vertices
		for(Airport help : airp)
		{
			//Adding the airport code in the List of strings
			airString.add(help.getCode());
		}
		//Setting the data in the journey class
		j.setStops(airString);
	}
	
	//Function to return the air time of the journey
	private int setJAirTime(List<Flight> flyp)
	{
		//Declaring variables to store the hours and minutes
		int mins=0;
		int totHr=0;
		//Running for loop till the list of the flight
		for(int i=0;i<flyp.size();i++)
		{
			//Converting the string into integer and converting it into hours
			int toHr = Integer.parseInt(flyp.get(i).getToGMTime())/100;
			int frHr = Integer.parseInt(flyp.get(i).getFromGMTime())/100;
			//Checking if the arriving hour is less than departure hour
			if(toHr<frHr)
			{
				//If it is less then subtracting it from 24 and adding the arrival hours
				totHr+= (24 - frHr) + toHr;
			}
			else
			{
				//Else just subtract arrival hour from departure hour 
				totHr+=  toHr - frHr;
			}
			//Calculating the minutes by subtracting arrival minutes from departure minutes  
			mins += Integer.parseInt(flyp.get(i).getToGMTime())%100 - Integer.parseInt(flyp.get(i).getFromGMTime())%100;
			//If the minutes are less then 0 then
			if(mins<0)
			{
				//We will decrement the hour by 1 
				totHr-=1;
				//And increase minutes by 60
				mins += 60;
			}
		}
		//Converting the result in minutes
		return (totHr*60) + mins;
	}

	//Function to return the cost of the journey  
	private int setJcost(List<Flight> flyp)
	{
		//Declaring the variable to store the total cost
		int cost =0;
		//Running for each loop for each flight 
		for(Flight flight : flyp)
		{
			//Adding the cost to the variable
			cost += flight.getCost();
		}
		//Returning the cost
		return cost;
	}

	//Function to calculate the connecting time
	private int connectTime(List<Flight> flyp)
	{
		//Declaring variables to store the hours and minutes
		int totMins=0;
		int totHr=0;
		//Running for loop for the flight
		for(int i=0;i<flyp.size()-1;i++)
		{
			//Converting the string into integer and converting it into hours
			int toHr = Integer.parseInt(flyp.get(i+1).getFromGMTime())/100;
			int frHr = Integer.parseInt(flyp.get(i).getToGMTime())/100;
			int toMin=Integer.parseInt(flyp.get(i+1).getFromGMTime())%100;
			int frMin = Integer.parseInt(flyp.get(i).getToGMTime())%100;
			//Checking if the arriving hour is less than departure hour
			if(toHr<frHr)
			{
				//If it is less then subtracting it from 24 and adding the arrival hours
				totHr+= (24 - frHr) + toHr;
			}
			//Checking if the hour is same and the leaving minute of the next flight is less than reaching minutes 
			else if(toHr ==frHr&& toMin<frMin)
			{
				totHr+=24;
			}
			else
			{
				//Else just subtract arrival hour from departure hour 
				totHr+=  toHr - frHr;
			}

			//Calculating the minutes by subtracting arrival minutes from departure minutes  
			totMins += toMin - frMin;
			//If the minutes are less then 0 then
			if(totMins<0)
			{
				//We will decrement the hour by 1 
				totHr-=1;
				//And increase minutes by 60
				totMins += 60;
			}
		}
		//Converting the result in minutes
		return (totHr*60) + totMins;
	}
	
	//Function to calculate the leastHop and return the journey object 
	@Override
	public Journey leastHop(String from, String to) throws FlyingPlannerException 
	{
		//Converting the airport code from string into Airport object 
		Airport start = airport(from);
		Airport end = airport(to);
		//If airport is not found
		if(start==null || end==null)
		{
			//If the function returns null then throwing the error
			throw new FlyingPlannerException("Airport not found!!! Please enter correct airport");
		}
		//Creating the object of journey class
		Journey j = new Journey();
		//Creating object of DijkstraShortestPath class and passing the graph in constructor
		DijkstraShortestPath<Airport, Flight> dj = new DijkstraShortestPath<Airport, Flight>(graph);
		//Getting the shortest path using Dijkstra Algorithm
		GraphPath<Airport,Flight> gPath = dj.getPath(start, end);
		//If the path is null then throwing an error
		if(gPath==null) 
		{
			throw new FlyingPlannerException("Sorry, Path Not Found!!!");
		}
		//Calling the function to set the vertices in the journey class
		setJStops(gPath.getVertexList(),j);
		//Calling the function to set the flights in the journey class
		setJFlight(gPath.getEdgeList(),j);
		//Setting the totalHop using the length of GraphPath
		j.setTotalHop(gPath.getLength());
		//Setting the cost of the journey
		j.setTotalCost(setJcost(gPath.getEdgeList()));
		//Setting the air time in the journey
		j.setAirTime(setJAirTime(gPath.getEdgeList()));
		//Setting the connecting time of the journey
		j.setConnectingTime(connectTime(gPath.getEdgeList()));
		//If the total hop is 0 then throwing the error as the path is not found
		if(j.totalHop()==0)
		{
			throw new FlyingPlannerException("Sorry, Path Not Found!!!");
		}
		//Returning the object of Journey Class
		return j;
	}

	//Function to return the object of Journey class with least cost
	//This function is common function for both the options whether user want to exclude airport or not
	public Journey leastCostReturn(String from, String to,List<String> excluding) throws FlyingPlannerException
	{
		//Converting the airport code from string into Airport object 
		Airport start = airport(from);
		Airport end = airport(to);
		//Creating the object of journey class to store data
		Journey j = null;
		
		//If airport is not found
		if(start==null || end==null)
		{
			//If the function returns null then throwing the error
			throw new FlyingPlannerException("Airport not found!!! Please enter correct airport");
		}
		//If the user doesn't want to exclude the airport 
		if(excluding==null)
		{
			//Then Populating the edge weight of all the edges
			try 
			{
				//Calling the function and passing the HashSet Of the flights and null as excluding airports
				if(!populateEdgeWeight((new FlightsReader()).getFlights(),null))
				{
					//If the function returns false then throwing the error
					throw new FlyingPlannerException("System Fault : Airport not found or cost is invalid!!");
				}
			} 
			catch (FileNotFoundException | FlyingPlannerException e) {
				e.printStackTrace();
			}
			//Calling the least hop function to calculate the stops
			j = leastHop(from,to);
		}
		//If the user selects the exclude airports feature
		else
		{
			//Then calling the edge weight function with excluding list
			try 
			{
				//Calling the function and passing the HashSet Of the flights and list of airports
				if(!populateEdgeWeight((new FlightsReader()).getFlights(),excluding))
				{
					//If the function returns false then throwing the error
					throw new FlyingPlannerException("System Fault : Airport not found or cost is invalid!!");
				}
			} 
			catch (FileNotFoundException | FlyingPlannerException e) {
				e.printStackTrace();
			}
			//Calling the least hop function to calculate the path 
			j = leastHop(from,to,excluding);
		}
		//Creating the function for finding the least cost
		DijkstraShortestPath<Airport, Flight> dj = new DijkstraShortestPath<Airport, Flight>(graph);
		//Declaring the variable to store the cost
		int cost = 0;
		//Storing the cost in variable 
		cost = (int) dj.getPathWeight(start, end);
		//Setting the total cost in the journey
		j.setTotalCost(cost);
		//if the cost is 0 then throwing the error
		if(cost==0)
		{
			//Throwing an error
			throw new FlyingPlannerException("Sorry, Path Not Found!!!");
		}
		//Returning the object of the journey
		return j;
	}

	//Function to convert the cost into integer and setting the edge weight 
	private boolean poplateEdge(Airport leaveAirport,Airport arriveAirport,String costS)
	{
		//Declaring the variable to store the cost
		int cost=0;
		//Converting the cost into integer from the string
		try
		{
			cost = Integer.parseInt(costS);
		}
		catch(Exception e)
		{
			//If the exception is thrown then returning false
			System.out.println("Cost is not in Numbers");
			return false;
		}
		//Calling the inbuilt function from library to set the weight
		graph.setEdgeWeight(leaveAirport, arriveAirport, cost);
		//Returning true if it adds the path
		return true;
	}
	
	//Function to set the edge weight according to the users choice
	private boolean populateEdgeWeight(HashSet<String[]> flightSet,List<String> excluding)
	{
		//Running for each loop for all the flights
		for(String[] fly : flightSet)
		{
			//In this we are using the airport function to find the object using the airport code
			Airport leaveAirport = airport(fly[1]);
			//Here, also we are doing the same step but for the arrival airport
			Airport arriveAirport = airport(fly[3]);
			//Checking if the both airport are present or not
			if(leaveAirport==null || arriveAirport == null)
			{
				//Even if one airport is not present then returning false
				return false;
			}
			//If the excluding list is not null
			if(excluding!=null)
			{
				//Running the for each loop for the excluding list
				for(String code:excluding)
				{
					//If the code in both list are same then not setting the edge weight 
					if(leaveAirport.getCode().equalsIgnoreCase(code) ||arriveAirport.getCode().equalsIgnoreCase(code) )
					{
						continue;
					}
					//Then populating the edge if the code is not same
					if(!poplateEdge(leaveAirport,arriveAirport,fly[5]))
					{
						//If the function return false then returning false
						return false;
					}
				}
			}
			//If the list is empty then setting the edge weight for all the edges
			else
			{
				//Calling the function to set the edge weight
				if(!poplateEdge(leaveAirport,arriveAirport,fly[5]))
				{
					//Returning false if the function returns false
					return false;
				}
			}
		}
		//If everything executes then returning true
		return true;
	}
	
	//Function to remove all the vertices and the edges from the graph   
	private void removeAll(List<String> excluding)
	{
		//List to store all the airports
		List<Airport> allAirports = new ArrayList<Airport>();
		//Running the loop for all the aiports in the excluded list
		for(String code : excluding)
		{
			//Converting the object into object of Airport class
			Airport air = airport(code);
			//Adding the airport object into the list
			allAirports.add(air);
			//Getting all the edges of the this vertex 
			Set<Flight> allEdges= graph.edgesOf(air);
			//Then removing all the edges of these vertices
			graph.removeAllEdges(allEdges);
		}
		//Removing all the vertices from the graph
		graph.removeAllVertices(allAirports);
	}

	//Function to return object of journey class with least cost and excluded airports  
	@Override
	public Journey leastCost(String from, String to, List<String> excluding) throws FlyingPlannerException 
	{
		return leastCostReturn(from,to,excluding);
	}

	//Function to return the journey object with least stops and excluding airport
	@Override
	public Journey leastHop(String from, String to, List<String> excluding) throws FlyingPlannerException 
	{
		//Removing the Airports from the graph
		removeAll(excluding);
		//Calling the function
		return leastHop(from,to);
	}


	@Override
	public Set<Airport> directlyConnected(Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setDirectlyConnected() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Airport> getBetterConnectedInOrder(Airport airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyingPlannerException {
		// TODO Auto-generated method stub
		return null;
	}


}
