package dijkstra.nodes;

import java.util.ArrayList;
import java.util.List;

public class MatrixMap implements NodesMap
{
	protected final int[][] distances;
	
	public MatrixMap(int size)
	{
		distances = new int[size][size];
	}
	
	/**
	 * Link two cities by a direct route with the given distance.
	 */
	public void addDirectRoute(int start, int end)
	{
		distances[start][end] = 1;
		distances[end][start] = 1;
	}
	
	/**
	 * @return the distance between the two cities, or 0 if no path exists.
	 */
	public int getDistance(int start, int end)
	{
		return distances[start][end];
	}
	
	/**
	 * @return the list of all valid destinations from the given city.
	 */
	public List<Integer> getDestinations(int start)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (int col = 0; col < distances[start].length; col++)
		{
			if (distances[start][col] > 0)
			{
				list.add( distances[start][col] );
			}
		}		
		return list;
	}

	/**
	 * @return the list of all cities leading to the given city.
	 */
	public List<Integer> getPredecessors(int end)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for (int row = 0; row < distances.length; row++)
		{
			if (distances[row][end] > 0)
			{
				list.add( distances[row][end] );
			}
		}
		return list;
	}

	public int getMapSize() {
		return distances.length; 
	}
}
