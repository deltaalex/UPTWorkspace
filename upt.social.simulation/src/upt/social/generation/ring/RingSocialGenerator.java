package upt.social.generation.ring;

import java.util.Vector;

import upt.social.generation.SocialGenerator;
import upt.social.model.INode;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.ISocialModelFactory;
import upt.social.state.SimConfig;

/**
 * Creates a ring network of agents with links to the closest 'K' adjacent
 * nodes. Can be parameterized.
 */
public class RingSocialGenerator extends SocialGenerator
{
	public RingSocialGenerator(ISocialModelFactory modelFactory)
	{
		super(modelFactory);
	}

	/**
	 * Creates a parameterized ring network
	 * 
	 * @param SIZE
	 *        Number of nodes
	 * @param K
	 *        Number of left and right neighbors to which each node will connect
	 * @param stubbornAgents
	 *        List of coordinates at which stubborn agents will be inserted. The
	 *        data structure also contains their STATE.
	 * @return The new network
	 */
	@Override
	public SocialNetwork createNewNetwork(int SIZE, int K, Vector<NodeCoordinate> stubbornAgents,
		Vector<NodeCoordinate> absurdAgents, String links)
	{
		SocialNetwork network = new SocialNetwork();
		Vector<INode> nodes = new Vector<INode>();

		if (stubbornAgents.size() >= SIZE)
		{
			throw new IllegalArgumentException("Too many stubborn agents in network !");
		}

		time = System.nanoTime();

		// normal agents
		for (int i = 0; i < SIZE; ++i)
		{
			nodes.add(factory.createAgent("A" + (i + 1), SimConfig.isOpinionated, SimConfig.MinSleep, SimConfig.MaxSleep));
		}

		// add stubborn agents and compute ProFactor
		SimConfig.ProFactor = 0;
		for (NodeCoordinate p: stubbornAgents)
		{
			INode sNode = factory.createStubbornAgent("SA" + (p.x + 1), p.getState(), SimConfig.MinSleep,
				SimConfig.MaxSleep);
			nodes.remove(p.x);
			nodes.add(p.x, sNode);
			if (p.getState().equals(STATE.YES))
			{
				SimConfig.ProFactor++;
			}
		}
		SimConfig.ProFactor /= 1.0 * stubbornAgents.size();

		// add links to the left and right
		for (int i = 0; i < SIZE; ++i)
		{
			INode node = nodes.get(i);

			for (int j = i - K; j <= i + K; ++j)
			{
				if (j == i)
				{
					continue;
				}

				if (j < 0)
				{
					node.addFriendBi(nodes.get(SIZE + j));
				}
				else if (j >= SIZE)
				{
					node.addFriendBi(nodes.get(j % SIZE));
				}
				else
				{
					node.addFriendBi(nodes.get(j));
				}

			}
		}

		network.addNodes(nodes);

		time = System.nanoTime() - time;

		return network;
	}
}
