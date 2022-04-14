package F28DA_CW2;

import java.util.Set;

public class Airport implements IAirportPartB, IAirportPartC 
{
	//Variable for storing the code of the airport 
	private String code;
	//Variable for storing the name of the airport
	private String name;

	//Constructor of the class
	Airport(String code,String name)
	{
		//Assigning the values passes in the parameter
		this.code = code;
		this.name = name;
	}
		
	//Function to return the code of the airport
	@Override
	public String getCode() 
	{
		return this.code;
	}

	//Function to return the name of the city of the airport
	@Override
	public String getName() 
	{
		return this.name;
	}

	
	//----------------------Unimplemented functions------------------------
	@Override
	public void setDicrectlyConnected(Set<Airport> dicrectlyConnected) {

	}

	@Override
	public Set<Airport> getDicrectlyConnected() {
		return null;
	}


	@Override
	public void setDicrectlyConnectedOrder(int order) {

	}

	@Override
	public int getDirectlyConnectedOrder() {
		return 0;
	}

}
