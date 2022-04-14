package F28DA_CW2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class FlyingPlannerProvidedTest {

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

	// Example test cases for Part B

	@Test
	public void leastCostTest() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
			assertEquals(3, i.totalHop());
			assertEquals(374, i.totalCost());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastHopTest() {
		try {
			Journey i = fi.leastHop("EDI", "DXB");
			assertEquals(2, i.totalHop());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastCostExclTest() {
		try {
			LinkedList<String> exclude = new LinkedList<String>();
			exclude.add("LHR");
			exclude.add("PRG");
			exclude.add("LGW");
			exclude.add("LCY");
			exclude.add("DUS");
			exclude.add("FRA");
			exclude.add("WAW");
			exclude.add("AMS");
			exclude.add("CDG");
			exclude.add("IST");
			exclude.add("GLA");
			exclude.add("CWL");
			exclude.add("EWR");
			exclude.add("BOS");
			Journey i = fi.leastCost("DXB", "EDI", exclude);
			System.out.println(i.getStops());
			fail();
		} catch (FlyingPlannerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void leastCostTimeTest() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
			assertEquals(436, i.airTime());
			assertEquals(1495, i.connectingTime());
			assertEquals(1931, i.totalTime());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}
}
