package dijkstra.tests;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import dijkstra.City;
import dijkstra.DenseBidirectionalRoutesMap;
import dijkstra.DijkstraEngine;
import dijkstra.Route;
import dijkstra.RoutesMap;

public class BidirectionalRoutesMapTest extends TestCase
{
	private RoutesMap map;
	
	public BidirectionalRoutesMapTest(String name)
	{
		super(name);
	}

	protected void setUp()
	{
		map = new DenseBidirectionalRoutesMap(7);
		map.addDirectRoute(City.A, City.B, 1);		
		map.addDirectRoute(City.A, City.C, 1);		
		map.addDirectRoute(City.A, City.D, 1);		
		
		map.addDirectRoute(City.B, City.C, 1);
		map.addDirectRoute(City.C, City.D, 1);
		
		map.addDirectRoute(City.B, City.E, 1);
		map.addDirectRoute(City.B, City.F, 1);
		map.addDirectRoute(City.E, City.F, 1);
		
		map.addDirectRoute(City.D, City.G, 1);		
	}
	
	public void testDistance()
	{
		assertDistanceEquals(City.A, City.B, 1);		
		assertDistanceEquals(City.B, City.A, 1);
		assertDistanceEquals(City.B, City.C, 1);									
	}
	
	public void testDefautDistance()
	{
		assertDistanceEquals(City.A, City.A, 0);		
	}
		
	private void assertDistanceEquals(City start, City stop, int expectedDistance)
	{
		assertEquals(
			"wrong distance", 
			expectedDistance,
			map.getDistance(start, stop)
    		);		
	}
	
	public void testDestinations()
	{
		List<City> l = map.getDestinations(City.A);
		
		assertEquals("incorrect number of destinations", 3, l.size());
		assertSame(City.B, l.get(0));
		assertSame(City.C, l.get(1));
		assertSame(City.D, l.get(2));
				
		l = map.getDestinations(City.E);
		
		assertEquals("incorrect number of destinations", 2, l.size());
		assertSame(City.B, l.get(0));
		assertSame(City.F, l.get(1));		
	}
	
	public void testPredecessors()
	{
		List<City> l = map.getPredecessors(City.A);		
		assertEquals("incorrect number of predecessors", 3, l.size());
		assertSame(City.B, l.get(0));
		assertSame(City.C, l.get(1));
		assertSame(City.D, l.get(2));
		
		l = map.getPredecessors(City.C);		
		assertEquals("incorrect number of predecessors", 3, l.size());
		assertSame(City.A, l.get(0));
		assertSame(City.B, l.get(1));
		assertSame(City.D, l.get(2));
	}
	
	private DijkstraEngine engine;
    
	public void testEngineA()
    {
        engine = new DijkstraEngine(map);
        engine.execute(City.A, null);

        assertEquals("incorrect shortest distance", 1, engine.getShortestDistance(City.C));
        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(City.F));
        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(City.G));
     
        engine.execute(City.E, City.A);
        
        City c = City.A;
        City dest = City.E;
        Route route = new Route();
        while(!c.equals(dest))
        {
        	route.addStop(c, 1);        	
        	System.out.print(c);
        	c = engine.getPredecessor(c);
        }
        System.out.println(dest);        
        //assertPredecessorAndShortestDistance(City.D, City.C, 5);
        //assertPredecessorAndShortestDistance(City.C, City.A, 4);
    }
	
	public void testEngineE()
    {
        engine = new DijkstraEngine(map);
        engine.execute(City.E, null);

        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(City.A));
        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(City.C));
        assertEquals("incorrect shortest distance", 4, engine.getShortestDistance(City.G));      
    }
    
    private void assertPredecessorAndShortestDistance(City c, City pred, int sd)
    {
        assertEquals("incorrect shortest path", pred, engine.getPredecessor(c));
        assertEquals("incorrect shortest distance", sd, engine.getShortestDistance(c));
    }
}
