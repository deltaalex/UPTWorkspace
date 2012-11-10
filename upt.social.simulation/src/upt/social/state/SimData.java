package upt.social.state;

import java.io.FileNotFoundException;
import java.util.Vector;

import upt.io.log.Log;
import upt.io.log.TextLog;
import upt.social.generation.SmallWorldSocialGenerator;
import upt.social.generation.SocialGenerator;
import upt.social.generation.advanced.WSDDSocialGenerator;
import upt.social.generation.mesh.HyperCube2DSocialGenerator;
import upt.social.generation.mesh.WrappedMeshSocialGenerator;
import upt.social.generation.random.RandomSocialGenerator;
import upt.social.generation.ring.RingSocialGenerator;
import upt.social.generation.scalefree.ScaleFreeSocialGenerator;
import upt.social.model.INode;
import upt.social.model.NodeCoordinate;
import upt.social.model.PollResults;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.AnalogModelFactory;
import upt.social.model.factory.ComplexModelFactory;
import upt.social.model.factory.DiscreteModelFactory;
import upt.social.model.factory.ISocialModelFactory;
import upt.social.simulation.threading.TimerLayer;

public class SimData
{
	// the network itself
	private static SocialNetwork network;

	// manages and creates 'nPoolThreads' threads
	private static TimerLayer timerManager;

	// the last sets of agents used for building the network
	private static Vector<NodeCoordinate> stubbornAgents, absurdAgents;

	/**
	 * Reinitializes the current mesh topology MESH network generator, the
	 * network and the threaded timer. Requires a list of initial stubborn
	 * agents (state and coordinates)
	 */
	public static void initializeSocialNetwork(Vector<NodeCoordinate> stubbornAgents,
		Vector<NodeCoordinate> absurdAgents)
	{
		ISocialModelFactory modelFactory = initializeSocialModel(stubbornAgents, absurdAgents);
		network = initializeNetworkTopology(modelFactory);
		initializeTimerLayer();
	}

	public static void reinitializeSocialNetwork()
	{
		if (stubbornAgents != null && absurdAgents != null)
		{
			initializeSocialNetwork(stubbornAgents, absurdAgents);
		}
		else
		{
			throw new IllegalArgumentException("No agents stored");
		}
	}

	/**
	 * Stores the simulation stubborn agents and creates the generator factory
	 * instance
	 * 
	 * @param stubbornAgents
	 * @return Created factory
	 */
	private static ISocialModelFactory initializeSocialModel(Vector<NodeCoordinate> stubbornAgents,
		Vector<NodeCoordinate> absurdAgents)
	{
		SimData.stubbornAgents = stubbornAgents;
		SimData.absurdAgents = absurdAgents;
		ISocialModelFactory factory;
		if (SimConfig.SocialModel == SIMMODEL.DISCETE)
		{
			factory = new DiscreteModelFactory();
		}
		else if (SimConfig.SocialModel == SIMMODEL.ANALOG)
		{
			factory = new AnalogModelFactory();
		}
		else if (SimConfig.SocialModel == SIMMODEL.COMPLEX)
		{
			factory = new ComplexModelFactory();
		}
		else
		{
			return null;
		}

		return factory;
	}

	/**
	 * Stores the simulation stubborn agents and creates the network topology. <br>
	 * Also adds long range links if so defined.
	 * 
	 * @param stubbornAgents
	 * @return New social network
	 */
	private static SocialNetwork initializeNetworkTopology(ISocialModelFactory modelFactory)
	{

		SimConfig.NA = SimConfig.MESH_WIDTH * SimConfig.MESH_HEIGHT - SimConfig.SA - SimConfig.AA;

		if (SimConfig.NetworkModel == TOPOLOGY.MESH)
		{
			network = new SocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH, SimConfig.MESH_HEIGHT,
				stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.MESHWRAP)
		{
			network = new WrappedMeshSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.MESH_HEIGHT, stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.HYPERCUBE2D)
		{
			network = new HyperCube2DSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.MESH_HEIGHT, stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.RANDOM)
		{
			network = new RandomSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.MESH_HEIGHT, stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.SCALEFREE)
		{
			network = new ScaleFreeSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.MESH_HEIGHT, stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.WSDD)
		{
			network = new WSDDSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.MESH_HEIGHT, stubbornAgents, absurdAgents, SimConfig.LINKS);
		}
		else if (SimConfig.NetworkModel == TOPOLOGY.RING)
		{
			network = new RingSocialGenerator(modelFactory).createNewNetwork(SimConfig.MESH_WIDTH,
				SimConfig.RingNeighbours, stubbornAgents, absurdAgents, SimConfig.LINKS);
			// just the width is used
			SimConfig.NA = SimConfig.MESH_WIDTH - SimConfig.SA - SimConfig.AA;
		}
		/**/
		else
		{
			return null;
		}

		// initialize long range links
		if (SimConfig.isSmallWorld)
		{
			network = new SmallWorldSocialGenerator().createSmallWorldNetwork(network, SimConfig.LongRangeDensity);
		}

		return network;
	}

	/**
	 * Initializes the timer manager with the given network.
	 */
	private static void initializeTimerLayer()
	{
		// 'nTimers x nPoolThreads' threads up and waiting
		timerManager = new TimerLayer(network, SimConfig.PoolThreads);
	}

	/**
	 * Determines if the current network status is sufficient for the simulator
	 * to stop
	 * 
	 * @return True if the simulation can stop
	 * 
	 */
	public static boolean isSimulationGoalReached(PollResults polls, int watchdogStep)
	{
		boolean finished = false;

		if (SimulationStopConditions.SimStopOnLength)
		{
			finished = watchdogStep >= SimulationStopConditions.SimulationLength;
		}
		else if (SimulationStopConditions.SimStopOnPositiveWin)
		{
			finished = polls.getPositivePercentage() >= SimulationStopConditions.SimulationPositiveWinThreshold;
		}
		else if (SimulationStopConditions.SimStopOnNegativeWin)
		{
			finished = polls.getNegativePercentage() <= SimulationStopConditions.SimulationNegativeWinThreshold;
		}
		else if (SimulationStopConditions.SimStopOnTolerance)
		{
			finished = polls.getTolerance() <= SimulationStopConditions.SimulationToleranceThreshold;
		}
		else if (SimulationStopConditions.SimStopOnOpinionChange)
		{
			finished = polls.getOpinionChangePercentage() <= SimulationStopConditions.SimulationOpinionChangeThreshold;
		}
		else
		{
			finished = false;
		}

		// log results
		if (finished && SimConfig.EnableLogResults)
		{
			Log log = new TextLog(SimConfig.LogRootPath, SimConfig.LogFileName, false);
			log.setResults(polls.toString());
			log.saveLog();
		}

		if (finished)
		{
			System.out.println(polls.getSimulationDays() + " days in " + polls.getRealTime() + "ms");
		}

		return finished;
	}

	/**
	 * Not implemented ! Very low performance.
	 * 
	 * @param path
	 * @throws FileNotFoundException
	 */
	@Deprecated
	public static void initializeFromFile(String path) throws FileNotFoundException
	{
		/*generator = new SocialGenerator();
		network = new SocialNetwork();
		Vector<INode> nodes = new Vector<INode>();
		INode node, friend;

		File file = new File(path);
		Scanner scan = new Scanner(file);

		String[] words;
		while (scan.hasNextLine())
		{
			words = scan.nextLine().trim().split(" ");

			if (words[0].toUpperCase().startsWith("S"))
			{
				node = new StubbornAgent(words[0], words[1].toUpperCase().equals("YES") ? STATE.YES : STATE.NO);
			}
			else
			{
				node = new Agent(words[0]);
			}

			nodes.add(node);
		}
		scan.close();

		scan = new Scanner(file);
		int l = 0;
		int start;
		while (scan.hasNextLine())
		{
			words = scan.nextLine().trim().split(" ");

			start = 1;
			if (words[0].toUpperCase().startsWith("S"))
			{
				start = 2;
			}
			for (int i = start; i < words.length; ++i)
			{
				if ((friend = getNodeById(nodes, words[i])) != null)
				{
					nodes.get(l).addFriendUni(friend);
				}
				else
				{
					System.err.println("weird");
				}
			}
			l++;
		}

		network.addNodes(nodes);

		scan.close();*/
	}

	/*public static SocialGenerator getGenerator()
	{
		return generator;
	}*/

	public static SocialNetwork getNetwork()
	{
		return network;
	}

	public static TimerLayer getTimerManager()
	{
		return timerManager;
	}

	public static Vector<NodeCoordinate> getStubbornAgents()
	{
		return stubbornAgents;
	}

	public static Vector<NodeCoordinate> getAbsurdAgents()
	{
		return absurdAgents;
	}

	private static INode getNodeById(Vector<INode> nodes, String id)
	{
		for (INode node: nodes)
		{
			if (node.getId().toLowerCase().equals(id.toLowerCase()))
			{
				return node;
			}
		}
		return null;
	}

}
