package upt.noc.state;

import java.util.Vector;

import upt.noc.structures.ArchitectureConfiguration;
import upt.noc.structures.NocNode;

public class NocReader
{
	public double getFreePacketDelay(int row, int col, ArchitectureConfiguration trafConfig,
		ArchitectureConfiguration archConfig, StringBuilder console)
	{
		// The switch that controls amount of displayed information
		boolean VERBOSE = false;

		// Initialize the arrival rate matrices
		double[][][][] arr_rate = new double[row][col][6][6];
		double[][][] arr_rate_agg = new double[row][col][6];
		double[][] arr_rate_agg1 = new double[row][col];

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				arr_rate_agg1[i][j] = 0;
				for (int k = 0; k < 6; k++)
				{
					arr_rate_agg[i][j][k] = 0;
					for (int l = 0; l < 6; l++)
					{
						arr_rate[i][j][k][l] = 0;
					}
				}
			}
		}

		// Data structures for routing table
		Vector<Vector<Integer>> routingTable = new Vector<Vector<Integer>>();

		// Initialize the long_range links
		int[] rLink = new int[row * col];
		for (int i = 0; i < row * col; i++)
		{
			rLink[i] = -1; // Initially there are no long_range links
		}

		// Initialization for the loop where the architecture file is parsed
		// Initialize current and destination IDs
		int id = -1;
		int dstId = -1;

		// Parse the architecture file and record the long_range links and the
		// routing table
		for (NocNode node: archConfig.getNodes())
		{
			id = node.getId();

			if (node.getDestId() != -1)
			{
				dstId = node.getDestId();

				rLink[id] = dstId;
				rLink[dstId] = id;
			}

			if (node.getDirections() != null)
			{
				routingTable.add(node.getDirections());
			}
		}

		// Initialize the free packet delay
		double freeDelay = 0;
		for (NocNode node: trafConfig.getNodes())
		{
			id = node.getId();

			if (node.getRate() != -1)
			{
				dstId = node.getDestId();
				double rate = node.getRate();

				if (rate > 1.0)
				{
					throw new IllegalArgumentException("Rate is larger than 1!");
				}

				int src_x, src_y, dst_x, dst_y;
				int curr_x, curr_y, curr_dir;
				int nid = -1;
				src_y = id % row;
				src_x = (id / row);
				dst_y = dstId % row;
				dst_x = (dstId / row);

				int nxt_dir = routingTable.get(src_y + src_x * row).get(dstId);
				arr_rate[src_x][src_y][0][nxt_dir] += rate;

				curr_x = src_x;
				curr_y = src_y;
				int hops = 0;
				while ((curr_x != dst_x) || (curr_y != dst_y))
				{
					// Continue until the curr and dst positions are the same
					curr_dir = routingTable.get(curr_y + curr_x * row).get(dstId);
					switch (curr_dir)
					{
						case 1:
							curr_y += 1;
							break;
						case 2:
							curr_y -= 1;
							break;
						case 3:
							curr_x += 1;
							break;
						case 4:
							curr_x -= 1;
							break;
						case 5:
							nid = rLink[curr_y + curr_x * row];
							curr_y = nid % row;
							curr_x = (nid / row);
							break;
					} // Switch

					int rev_dir = reverseDirection(curr_dir);
					nxt_dir = routingTable.get(curr_y + curr_x * row).get(dstId);
					arr_rate[curr_y][curr_x][rev_dir][nxt_dir] += rate;
					hops++;

				} // while

				freeDelay += rate * hops;
				continue;
			}

		} // while

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				for (int k = 0; k < 6; k++)
				{
					for (int l = 0; l < 6; l++)
					{
						arr_rate_agg[i][j][l] += arr_rate[i][j][k][l];
					}
				}
			}
		}

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				for (int k = 0; k < 6; k++)
				{
					if (VERBOSE)
					{
						System.out.println(arr_rate_agg[i][j][k] + "\t");
					}
					arr_rate_agg1[i][j] += arr_rate_agg[i][j][k];
					// Check here the conditions for criticality
					if (arr_rate_agg1[i][j] >= 0.5)
					{
						System.out.println("\nWARNING: Criticality reached at node (" + i + "," + j + ")");
						System.out.println("\t Try scaling down the packet generation rates");
					}
				}
				if (VERBOSE)
				{
					System.out.println();
				}
			} // for
		}

		if (VERBOSE)
		{
			System.out.println("The free packet delay :" + freeDelay);
		}

		return freeDelay;
	}

	private int reverseDirection(int current)
	{
		int reversed = 0;
		switch (current)
		{
			case 0: // Arrived
				reversed = 0;
				break;
			case 1: // South
				reversed = 2;
				break;
			case 2: // North
				reversed = 1;
				break;
			case 3: // East
				reversed = 4;
				break;
			case 4: // West
				reversed = 3;
				break;
			case 5: // long link
				reversed = 5;
				break;
		}
		return reversed;
	}
}
