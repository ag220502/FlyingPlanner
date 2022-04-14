package F28DA_CW2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class FlyingPlannerTest {

	FlyingPlanner fi;

	@Before
	public void initialize() {
		fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}
	}

	//----------------------- Part B Tests -----------------------------
	
	//Test to check the least cost from NCL to NTL 
	@Test
	public void leastCostTest() 
	{
		try 
		{
			Journey i = fi.leastCost("NCL", "NTL");
			assertEquals(4, i.totalHop());
			assertEquals(1035, i.totalCost());
		} 
		catch (FlyingPlannerException e) {
			fail();
		}
	}
	
	//Test to check the count of least hop from NCl to NTL
	@Test
	public void leastHopTest() {
		try {
			Journey i = fi.leastHop("NCL", "NTL");
			assertEquals(3, i.totalHop());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}
	
	//Test to check the cost of journey when user selects least hop
	@Test
	public void leastHopCostTest() {
		try {
			Journey i = fi.leastHop("NCL", "NTL");
			assertEquals(3, i.totalHop());
			assertEquals(1070, i.totalCost());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}

	//Test to check the air time of the journey when least cost is selected
	@Test
	public void leastCostAirTime()
	{
		try 
		{
			Journey i = fi.leastCost("NCL", "NTL");
			assertEquals(1061, i.airTime());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}
	
	//Test to check the air time of the journey when least hop is selected
	@Test
	public void leastHopAirTime()
	{
		try 
		{
			Journey i = fi.leastHop("NCL", "NTL");
			assertEquals(1236, i.airTime());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}
	
	
	//----------------------- Part C Tests -----------------------------
	//Test to check the total time of the journey when least cost is selected
	@Test
	public void leastCostTotalTime()
	{
		try 
		{
			Journey i = fi.leastCost("NCL", "NTL");
			assertEquals(1061, i.airTime());
			assertEquals(1710, i.connectingTime());
			assertEquals(2771, i.totalTime());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}
	
	//Test to check the total time of the journey when least hop is selected
	@Test
	public void leastHopTotalTime()
	{
		try 
		{
			Journey i = fi.leastHop("NCL", "NTL");
			assertEquals(1236, i.airTime());
			assertEquals(2259, i.connectingTime());
			assertEquals(3495, i.totalTime());
		} 
		catch (FlyingPlannerException e) 
		{
			fail();
		}
	}
	
	//Test to check the result with excluding airports and least cost
	@Test
	public void leastCostExclTest() {
		try {
			LinkedList<String> exclude = new LinkedList<String>();
			exclude.add("AMS");
			Journey i = fi.leastCost("NCL", "NTL", exclude);
			assertEquals(1042, i.totalCost());
			assertEquals(5, i.totalHop());
		} catch (FlyingPlannerException e) {
			assertTrue(true);
		}
	}
}
