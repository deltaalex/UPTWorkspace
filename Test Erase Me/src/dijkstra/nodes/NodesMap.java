package dijkstra.nodes;

import java.util.List;

public interface NodesMap
{
	/**
	 * Enter a new segment in the graph.
	 */
	public void addDirectRoute(int start, int end);
	
	/**
	 * Get the value of a segment.
	 */
	public int getDistance(int start, int end);
	
	/**
	 * Get the list of cities that can be reached from the given city.
	 */
	public List<Integer> getDestinations(int city); 
	
	/**
	 * Get the list of cities that lead to the given city.
	 */
	public List<Integer> getPredecessors(int city);
	
	/**
	 * Get the map size 
	 */
	public int getMapSize();
}