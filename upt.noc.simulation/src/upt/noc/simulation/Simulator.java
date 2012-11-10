package upt.noc.simulation;

import java.io.FileNotFoundException;
import java.util.Vector;

import upt.noc.simulation.threading.NocWorker;
import upt.noc.state.NocReader;
import upt.noc.state.NocWriter;
import upt.noc.structures.ArchitectureConfiguration;
import upt.noc.structures.NocResult;
import upt.threading.ThreadPool;

public class Simulator extends Thread
{
	// Network size
	public static int NumRows = 20, NumColumns = 20;
	public static int NumThreads = 8;
	// Constraint on the number of additional long-range links
	public static int Constraint = 10;

	private String arch_file = "architecture.config";
	private String traffic_file = "traffic/traffic6x6.config";
	private ArchitectureConfiguration trafConfig;
	private ArchitectureConfiguration archConfig;
	private Vector<Integer> longLinks;

	private StringBuilder consoleBuffer;
	private boolean VERBOSE = false;

	@Override
	public void run()
	{
		try
		{
			if (NumRows <= 10 && NumColumns <= 10)
			{
				simulate();
			}
			else
			{
				System.err.println("Network size is too big for simulation\n");
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		}
		catch (IllegalArgumentException ee)
		{
			System.err.println(ee.getMessage());
			System.exit(2);
		}
	}

	/**
	 * To be called after a simulation has finished to obtain a list of all long
	 * range links.
	 * 
	 * @return inserted long range links
	 */
	public Vector<Integer> getNocLinks()
	{
		return longLinks;
	}

	/**
	 * Runs a NOC simulation. Starts with a predefined network size and a
	 * traffic configuration. Creates an architectural configuration in which
	 * additional long range links are added to optimize efficiency based on the
	 * given traffic. The simulation is optimized using a <b>custom
	 * ThreadPool</b>
	 * 
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException
	 */
	private void simulate() throws FileNotFoundException, IllegalArgumentException
	{
		NocReader reader = new NocReader();
		NocWriter writer = new NocWriter();

		// Network size
		int N = NumRows * NumColumns;

		// load traffic
		trafConfig = writer.initializeTraffic(traffic_file, consoleBuffer);

		System.out.println("");
		if (VERBOSE)
		{
			System.out.println("Running the long-range link insertion tool with parameters:\n" + "number of rows:\t"
				+ NumRows + "\n" + "number of columns:\t" + NumColumns + "\n" + "constraint:\t" + Constraint + "\n"
				+ "traffic file: \t" + traffic_file + "\n" + "architecture file: " + arch_file + "\n\n");
		}

		// Main loop: Continue until the utilization is less than the
		// constraint.
		// Each time find the most beneficial link to be added

		/*** Initialization before the loop starts ***/
		longLinks = new Vector<Integer>();
		// Link_add[i]=1 => A long_range link is attached to node i
		boolean[] link_add = new boolean[N];
		// each node can have at most one long link
		for (int r = 0; r < N; r++)
		{
			// No link is added initially
			link_add[r] = false;
		}

		// To generate the initial topology and routing table (which is mesh and
		// XY routing), we do a trick: A link is added between nodes 0 and 1.
		// After generatinng the architecture files, we delete the link.
		longLinks.add(0);
		longLinks.add(1);
		// Number of long links already added
		int count = 2;

		consoleBuffer = new StringBuilder();
		archConfig = writer.generateArchConfiguration(NumRows, NumColumns, count, longLinks, consoleBuffer);
		double oriTao = reader.getFreePacketDelay(NumRows, NumColumns, trafConfig, archConfig, consoleBuffer);

		longLinks.remove(longLinks.size() - 1);
		longLinks.remove(longLinks.size() - 1);

		// Total length of long_range links inserted so far
		int utilization = 0;
		Double minRate;

		/*** End of the initialization before the main loop ***/
		// Starting the main loop
		int iteration = 0;
		double old_rate = 0;

		while (utilization < Constraint)
		{
			minRate = new Double(1000);
			ThreadPool threadPool = new ThreadPool(NumThreads);
			NocResult[] results = new NocResult[N * N];

			for (int m = 0; m < N; m++)
			{
				for (int n = 0; n < N; n++)
				{
					// if link already existss
					if (link_add[m] || link_add[n] || (m == n))
					{
						continue;
					}

					threadPool.runTask(new NocWorker(results, trafConfig, longLinks, reader, writer, (m * N + n),
						NumRows, NumColumns, m, n, count));
				}
			}
			threadPool.join();

			// Insert the best link found in the loop permanently
			NocResult result = getBestResult(results, minRate);
			if (result == null)
			{
				break;
			}
			longLinks.add(result.getBest0());
			longLinks.add(result.getBest1());
			link_add[result.getBest0()] = true;
			link_add[result.getBest1()] = true;
			count += 2;

			// Update the utilization
			int dy = Math.abs((result.getBest0() % NumColumns) - (result.getBest1() % NumColumns));
			int dx = Math.abs((result.getBest0() / NumRows) - (result.getBest1() / NumRows));
			utilization = utilization + dy + dx;

			if (VERBOSE)
			{
				System.out.println("Iteration " + iteration + ", Permanently inserted the long_range link "
					+ result.getBest0() + "," + result.getBest1() + "\n");
			}

			iteration++;

			if (Math.abs(old_rate - result.getCurrRate()) < 0.01)
			{
				System.out.println("Stopped at utilization = " + utilization);
				break;
			}
			old_rate = result.getCurrRate();
		} // End of main loop

		// Print out the long range links inserted to the original network
		System.out.println("*********************************************************************");
		System.out.print("The following long range links are inserted to the network:\n\t");

		for (int i = 0; i < longLinks.size() - 1; i += 2)
		{
			System.out.print(longLinks.get(i) + "->" + longLinks.get(i + 1) + "  ");
		}

		consoleBuffer = new StringBuilder();
		archConfig = writer.generateArchConfiguration(NumRows, NumColumns, count - 2, longLinks, consoleBuffer);
		double currRate = reader.getFreePacketDelay(NumRows, NumColumns, trafConfig, archConfig, consoleBuffer);
		System.out.println(consoleBuffer.toString());

		System.out.println("\nFinal free packet delay : " + currRate);
		System.out.println("Original free packet delay : " + oriTao);
		System.out.println("Improvement : " + (int) ((10000.0 * (oriTao - currRate) / oriTao)) / 100.0 + "%");

		System.out.println("\nThe architecture and routing table are written to " + arch_file);
		System.out.println("*********************************************************************\n");
	}

	private NocResult getBestResult(NocResult[] results, double minRate)
	{
		NocResult bestResult = null;
		minRate = Double.MAX_VALUE;
		for (NocResult result: results)
		{
			if (result != null && result.getCurrRate() < minRate)
			{
				minRate = result.getCurrRate();
				bestResult = result;
			}
		}
		return bestResult;
	}

}
