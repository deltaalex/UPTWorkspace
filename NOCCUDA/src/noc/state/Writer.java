package noc.state;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import noc.structures.ArchitectureConfiguration;
import noc.structures.NocNode;

public class Writer
{
	/**
	 * Generates a new NOC architecture configuration. Represents one simulation
	 * step.
	 * 
	 * @param rows
	 *        - network row size
	 * @param cols
	 *        - network column size
	 * @param count
	 * @param longLinks
	 *        - long range links alreay present in the network
	 * @param console
	 *        - used for output in verbose mode
	 * @return a new architecture configuration
	 */
	public ArchitectureConfiguration generateArchConfiguration(int rows, int cols, int count,
		Vector<Integer> longLinks, StringBuilder console)
	{
		ArchitectureConfiguration archConfig = new ArchitectureConfiguration();

		int[] nodes = new int[rows * cols];
		for (int i = 0; i < rows * cols; i++)
		{
			// No extra link is assigned to the nodes yet
			nodes[i] = -1;
		}

		for (int i = 0; i < count; i += 2)
		{
			archConfig.addNode(new NocNode(longLinks.get(i), longLinks.get(i + 1)));
			nodes[longLinks.get(i)] = longLinks.get(i + 1);
			nodes[longLinks.get(i + 1)] = longLinks.get(i);
		}

		int dir = 0;
		int rlink = 0;
		int rlink_x = 0, rlink_y = 0, rdir = -1;
		boolean flag = true;
		NocNode node;

		// From each router
		for (int src_x = 0; src_x < cols; src_x++)
		{
			// (src_x,src_y): source row and source columns
			for (int src_y = 0; src_y < rows; src_y++)
			{
				node = new NocNode(src_y + src_x * rows);
				// To each router
				for (int dst_x = 0; dst_x < cols; dst_x++)
				{
					for (int dst_y = 0; dst_y < rows; dst_y++)
					{
						// if nodes are the same
						if ((src_x == dst_x) && (src_y == dst_y))
						{
							dir = 0;
						}
						else if (src_x < dst_x)
						{
							dir = 3; // right
						}
						else if (src_x > dst_x)
						{
							dir = 4; // left
						}
						else if (src_y < dst_y)
						{
							dir = 1; // down
						}
						else if (src_y > dst_y)
						{
							dir = 2; // until here find XY decision
						}

						if ((dir > 0) && (nodes[src_y + src_x * rows] >= 0))
						{
							// There is a random link assigned
							// The random link connects the source to rlink
							rlink = nodes[src_y + src_x * rows];
							rlink_y = rlink % rows;
							rlink_x = (rlink / rows);

							// Now find the direction. Random Link drives the
							// source
							if (rlink_x > src_x)
							{
								if (rlink_y > src_y)
								{
									rdir = 7; // NorthEast: includes X_to_Y turn
								}
								else if (rlink_y == src_y)
								{
									rdir = 6; // East: includes *_to_X turn
								}
								else
								{
									rdir = 5; // SouthEast
								}
							}
							else if (rlink_x < src_x)
							{
								if (rlink_y > src_y)
								{
									rdir = 1; // NorthWest
								}
								else if (rlink_y == src_y)
								{
									rdir = 2; // West
								}
								else
								{
									rdir = 3; // SouthWest
								}
							}
							else if (rlink_y > src_y)
							{
								rdir = 0; // North
							}
							else if (rlink_y < src_y)
							{
								rdir = 4; // South
							}
							else
							{
								System.out.println("Original direction is: " + dir);
								System.out.println(rlink_x + ":rlink_x, " + src_x + ":src_x," + rlink_y + ":rlink_y, "
									+ src_y + ":src_y");
								throw new IllegalArgumentException("Failed to find the requested output direction!");
							}

							// E, W or SE
							if ((rdir == 3) || (rdir == 4) || (rdir == 5))
							{
								// E, W, N
								if ((dir == 3) || (dir == 4) || ((dir == 2) && (rlink_x != dst_x)))
								{
									flag = false;
								}
							}

							// Random link is connected to the destination
							if ((rlink_x == dst_x) && (rlink_y == dst_y))
							{
								// Overwrite the previous value
								flag = true;
							}

							if (((Math.abs(rlink_y - dst_y) + Math.abs(rlink_x - dst_x)) < ((Math.abs(src_x - dst_x)
								+ Math.abs(src_y - dst_y) - 1)))
								&& flag)
							{
								// Random link provides a shorter path
								dir = 5;
							}

							flag = true;
						}
						node.addDirection(dir);
					} // for (int dst_y=0...
				}
				archConfig.addNode(node);
			}
		}
		return archConfig;
	}

	/**
	 * Loads a traffic configuration from the given file.
	 * 
	 * @param trafConfigPath
	 *        - source file
	 * @param console
	 *        - used for output in verbose mode
	 * @return a traffic configuration
	 * @throws FileNotFoundException
	 */
	public ArchitectureConfiguration initializeTraffic(String trafConfigPath, StringBuilder console)
		throws FileNotFoundException
	{
		// Initialization for the loop where the architecture file is parsed
		// Initialize current and destination IDs
		int id = -1;
		int dstId = -1;
		String inputLine; // Variable used to parse the input files

		// Open the traffic file
		if (!new File(trafConfigPath).exists())
		{
			throw new FileNotFoundException("Traffic file cannot be opened");
		}
		Scanner scanner = new Scanner(new File(trafConfigPath));
		NocNode node = null;
		ArchitectureConfiguration trafficConfig = new ArchitectureConfiguration();

		while (scanner.hasNextLine())
		{
			inputLine = scanner.nextLine().trim();

			// line starting with "#" are comments
			if (inputLine.startsWith("#"))
			{
				continue;
			}

			if (inputLine.startsWith("@NODE"))
			{
				id = Integer.parseInt(inputLine.split(" ")[1]);
				node = new NocNode(id);
				continue;
			}

			if (inputLine.startsWith("packet_to_destination_rate"))
			{
				dstId = Integer.parseInt(inputLine.split(" ")[1]);
				double rate = Double.parseDouble(inputLine.split(" ")[2]);
				if (node != null)
				{
					node.setRandomLink(dstId);
					node.setRate(rate);
				}

				if (rate > 1.0)
				{
					throw new IllegalArgumentException("Rate is larger than 1!");
				}

				trafficConfig.addNode(node);

				continue;
			}

			// unrecognized options
			if (inputLine.length() > 0)
			{
				System.out.println("Unrecognized option: " + inputLine);
				continue;
			}

		} // while

		scanner.close();
		return trafficConfig;
	}

}
