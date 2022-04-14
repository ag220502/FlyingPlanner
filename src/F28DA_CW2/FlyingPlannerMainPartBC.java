package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlyingPlannerMainPartBC 
{
	
	//Object to take input from the user
	private static Scanner scan = new Scanner(System.in);
	
	//Function to print the flight details
	private static void printFlightDetails(Flight fly)
	{
		//Printing the flight details
		System.out.printf("%-18s","| "+fly.getFrom().getName()+" ("+fly.getFrom().getCode()+")");
		System.out.printf("%-8s","| "+fly.getFromGMTime().substring(0, 2)+":"+fly.getFromGMTime().substring(2, 4));
		System.out.printf("%-10s","| "+fly.getFlightCode());
		System.out.printf("%-18s","| "+fly.getTo().getName()+" ("+fly.getTo().getCode()+")");
		System.out.printf("%-8s","| "+fly.getToGMTime().substring(0, 2)+":"+fly.getToGMTime().substring(2, 4));
		System.out.print("|");
		System.out.println();
	}
	
	//Function to print the journey details which includes the flight details
	private static void printJourneyDetails(List<String> flights,FlyingPlanner fi)
	{
		System.out.println("----------------------------------------------------------------------");
		System.out.printf("%-7s","| Leg");
		System.out.printf("%-18s","| Leave");
		System.out.printf("%-8s","| At");
		System.out.printf("%-10s","| On");
		System.out.printf("%-18s","| Arrive");
		System.out.printf("%-8s","| At");
		System.out.print("|");
		System.out.println();
		System.out.println("----------------------------------------------------------------------");
		int i=1;
		//Running loop to call the function and print it 
		for(String code : flights)
		{
			System.out.printf("%-7s","| "+i);
			i+=1;
			printFlightDetails(fi.flight(code));
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println();
	}
	
	//Function to print the whole journey
	private static void printJourney(Journey j,FlyingPlanner fi)
	{
		printJourneyDetails(j.getFlights(),fi);
		System.out.println("Total Cost of the journey is     : £"+j.totalCost());
		System.out.println("Total Air Time of the journey is : "+j.airTime()+" minutes.");
		System.out.println("Total Connecting Time is         : "+j.connectingTime()+" minutes.");
		System.out.println("Total Time of the journey is     : "+j.totalTime()+" minutes.");
	}
	
	//Function to print the full journey according to the choices
	private static void fullJourney(FlyingPlanner fi,int choice,int exChoice,String startCode,String endCode,List<String> exclude)
	{
		//Printing the journey details 
		System.out.println("Journey from "+startCode+" and "+endCode+" is : ");
		try 
		{
			//If the exclude airports are not there
			if(exChoice==0)
			{
				//If least cost is selected
				if(choice==1)
				{
					//Calling the function to print the journey with least cost 
					printJourney(fi.leastCost(startCode, endCode),fi);
				}
				else
				{
					//Calling the function to print the journey with least stops
					printJourney(fi.leastHop(startCode, endCode),fi);
				}
			}
			//If exclude airports is not selected
			else
			{
				//if least cost is selected
				if(choice==1)
				{
					//Calling the function to print the journey with least cost
					printJourney(fi.leastCost(startCode, endCode,exclude),fi);
				}
				else
				{
					//Calling the function to print the journey with least stops
					printJourney(fi.leastHop(startCode, endCode,exclude),fi);
					
				}
			}
		} 
		catch (FlyingPlannerException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	//Function for the whole project
	private static void project(FlyingPlanner fi)
	{
		//Printing the welcome statement
		System.out.println("=========================================");
		System.out.println("==            Flying Planner           ==");
		System.out.println("=========================================");
		//Asking for the inputs of the airport codes
		System.out.print("Please enter the departure Airport code : ");
		String startCode = scan.nextLine();
		System.out.print("Please enter the arrival Airport code   : ");
		String endCode = scan.nextLine();
		//If the airport code is not valid then reporting and exiting the code
		if((fi.airport(startCode)==null) && (fi.airport(startCode)==null))
		{
			System.err.println("Invalid Input!!");
			System.exit(0);
		}
		//Asking whether the user wants least cost or least stops
		System.out.println("Please choose your preference : ");
		System.out.println("1. Least Cost");
		System.out.println("2. Least Stops");
		System.out.print("Enter Your Choice : ");
		int choice = scan.nextInt();
		//If the input is not valid then exiting the code
		if(choice!=0 && choice!=1)
		{
			System.err.println("Invalid Input");
			System.exit(0);
		}
		//Asking whether the user wants to exclude airports or not 
		System.out.println("Do you want to exclude any aiport(Y/N) : ");
		char exChoice = scan.next().charAt(0);

		//Checking If the user selects yes for excluding the airports 
		if(exChoice == 'Y' || exChoice == 'y')
		{
			//Asking the user to enter the airports
			System.out.println("Please enter the codes of the airports you want to exclude : ");
			System.out.println("Please enter * to stop entering the code");
			String code ;
			//List to store all the airports
			List<String> exAirports= new ArrayList<String>();
			//Running infinite loop for asking the stops
			for(;;)
			{
				System.out.print("> ");
				//Taking the input
				code = scan.next();
				//If the user wants to stop the code then breaking the loop
				if(code.equalsIgnoreCase("*"))
				{
					break;
				}
				//If the code entered by the user is not valid then not adding it in the list
				if(!(fi.airport(code)==null))
				{
					//If the code is equal to the start code it end code then not adding it in the list
					if(!(code.equalsIgnoreCase(startCode)) && !(code.equalsIgnoreCase(endCode)))
					{
						exAirports.add(code);	
					}
					else
					{
						//Printing the error
						System.err.println("Invalid Input!!");
					}
				}
				else
				{
					//Printing the error
					System.err.println("Invalid Input!!");
				}
			}
			System.out.println("Thanks for entering the codes.");
			//Calling the function to print the whole journey
			fullJourney(fi,choice,1,startCode,endCode,exAirports);
		}
		//if the user doesn't want to exclude airports 
		else if(exChoice == 'N' || exChoice == 'n')
		{
			//Calling the function with different mode
			fullJourney(fi,choice,0,startCode,endCode,null);
		}
		//If the input is not valid then exiting the code
		else
		{
			System.err.println("Invalid Input");
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args) 
	{
		//Creating the object of class
		FlyingPlanner fi;
		fi = new FlyingPlanner();
		try 
		{
			//Calling the populate function to populate the graph 
			fi.populate(new FlightsReader());
			//Calling the main function
			project(fi);
		} 
		catch (FileNotFoundException | FlyingPlannerException e) 
		{
			e.printStackTrace();
		}
	}

}
