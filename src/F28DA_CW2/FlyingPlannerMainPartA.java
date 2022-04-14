package F28DA_CW2;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class FlyingPlannerMainPartA 
{
	//Function to print all the vertex to show the user all th available airports
	private static void printVertex(Graph<String, DefaultEdge> g)
	{
		//Storing the List of vertex into the Set of String so that only unique values are there
		Set<String> vertices = g.vertexSet();
		//Declaring the temporary variable
		int i=1;
		//Running for loop for each vertex
		for(String vertex : vertices)
		{
			//Printing the value of the vertex
			System.out.println(i+". "+vertex);
			//Incrementing i to print the value
			i++;
		}
	}

	//Function to print the shortest path returned from the Dijsktra Class
	private static void printPath(GraphPath<String, DefaultEdge> he)
	{
		//Taking the vertex list and storing the vertex in the List 
		List<String> check = he.getVertexList();
		//Running for loop to print the list of vertices
        for(int i=0;i<check.size()-1;i++)
        {
        	//Printing the current value and the next value
        	System.out.println((i+1)+". "+check.get(i)+" -> "+check.get(i+1));
        }
	}
	
	
	
	public static void main(String[] args) 
	{
		
		//Creating object to take input from user
		Scanner scan = new Scanner(System.in);
		
		//Creating the object of the graph to store the data
        Graph<String, DefaultEdge> g = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
        
        //Declaring variable to store the city airports
        String v1 = "Edinburgh";
        String v2 = "Heathrow";
        String v3 = "Dubai";
        String v4 = "Sydney";
        String v5 = "Kuala Lumpur";
        
        //Adding all the cities to the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        
        //Adding the edges(flights) to connect vertices 
        g.addEdge(v1, v2);
        g.addEdge(v2, v1);
        g.addEdge(v2, v3);
        g.addEdge(v3, v2);
        g.addEdge(v2, v4);
        g.addEdge(v4, v2);
        g.addEdge(v3, v5);
        g.addEdge(v5, v3);
        g.addEdge(v3, v1);
        g.addEdge(v1, v3);
        g.addEdge(v5, v4);
        g.addEdge(v4, v5);
        
        //Setting edge weights for each edge
        g.setEdgeWeight(v1, v2, 80);
        g.setEdgeWeight(v2, v1, 80);
        g.setEdgeWeight(v2, v3, 130);
        g.setEdgeWeight(v3, v2, 130);
        g.setEdgeWeight(v2, v4, 570);
        g.setEdgeWeight(v4, v2, 570);
        g.setEdgeWeight(v3, v5, 170);
        g.setEdgeWeight(v5, v3, 170);
        g.setEdgeWeight(v3, v1, 190);
        g.setEdgeWeight(v1, v3, 190);
        g.setEdgeWeight(v5, v4, 150);
        g.setEdgeWeight(v4, v5, 150);
        
        //Creating object to find the least path weight and the shortest path
        DijkstraShortestPath<String, DefaultEdge> dj = new DijkstraShortestPath<String, DefaultEdge>(g);
        
        //Starting the interface for the user
        System.out.println("===================================");
        System.out.println("==   Welcome to Flying Planner   ==");
        System.out.println("===================================");
        System.out.println("The airports available are : ");
        //Calling function to print all the vertices 
        printVertex(g);
        //Taking input from the user for the name of the airports
        System.out.print("Please enter the start airport :");
        String start = scan.nextLine();
        System.out.print("Please enter the dest. airport :");
        String dest = scan.nextLine();
        //Calculating the shortest path and printing the path
        System.out.println("Shortest Path is : ");
        //Calling function to print the path
        printPath(dj.getPath(start, dest));
        //Printing the cost of the least paths
        System.out.println("Cost of shortest path is : "+dj.getPathWeight(start, dest));
        
	}

}
