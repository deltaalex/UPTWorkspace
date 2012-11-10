package dijkstra.tests;

import junit.framework.TestCase;
import dijkstra.City;
import dijkstra.DenseBidirectionalRoutesMap;
import dijkstra.RoutesMap;
import dijkstra.nodes.DijkstraEngine;
import dijkstra.nodes.MatrixMap;
import dijkstra.nodes.NodesMap;

public class PerformanceTest extends TestCase
{
	private NodesMap map;
	private DijkstraEngine engine;
	
	public PerformanceTest(String name)
	{
		super(name);
	}

	protected void setUp()
	{			
		map = new MatrixMap(7);
		map.addDirectRoute(0, 1);		
		map.addDirectRoute(0, 2);		
		map.addDirectRoute(0, 3);		
		
		map.addDirectRoute(1, 2);
		map.addDirectRoute(2, 3);
		
		map.addDirectRoute(1, 4);
		map.addDirectRoute(1, 5);
		map.addDirectRoute(4, 5);
		
		map.addDirectRoute(3, 6);		
	}
	 
	/*public void testEngine()
    {
        engine = new DijkstraEngine(map);
        
        long t0 = System.nanoTime();
        for(int i=0; i<7; ++i)
        {
        	engine.execute(i, null);

        	for(int j=0; j<7; ++j)
        	{
        		if(i != j)
        			engine.getShortestDistance(j);
        	}        	        	
        }
        long t1 = System.nanoTime();                
        System.out.println("Time : " + (t1-t0)/1000 + " us");              
    }*/
	
	public void testEngineE()
    {
        engine = new DijkstraEngine(map);
        engine.execute(4, null);

        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(0));
        assertEquals("incorrect shortest distance", 2, engine.getShortestDistance(2));
        assertEquals("incorrect shortest distance", 4, engine.getShortestDistance(6));      
    }
}
