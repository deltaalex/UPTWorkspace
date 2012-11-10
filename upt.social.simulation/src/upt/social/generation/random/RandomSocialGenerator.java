package upt.social.generation.random;

import java.util.Random;
import java.util.Vector;

import upt.social.generation.SocialGenerator;
import upt.social.model.INode;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.ISocialModelFactory;
import upt.social.state.SimConfig;

/**
 * Creates a random graph network of agents with links to random nodes in the
 * network. Can be parameterized.
 */
public class RandomSocialGenerator extends SocialGenerator
{
	public RandomSocialGenerator(ISocialModelFactory modelFactory)
	{
		super(modelFactory);
	}

	/**
	 * Creates a parameterized random network
	 * 
	 * @param WIDTH
	 *        Width of the mesh
	 * @param HEIGHT
	 *        Height of the mesh
	 * @param stubbornAgents
	 *        List of coordinates at which stubborn agents will be inserted. The
	 *        data structure also contains their STATE.
	 * @param absurdAgents
	 *        List of coordinates at which absurd agents will be inserted.
	 * @return The new network
	 */
	@Override
	public SocialNetwork createNewNetwork(int WIDTH, int HEIGHT, Vector<NodeCoordinate> stubbornAgents,
		Vector<NodeCoordinate> absurdAgents, String links)
	{
		SocialNetwork network = new SocialNetwork();
		Vector<INode> _nodes = new Vector<INode>();
		INode[][] nodes;

		if (stubbornAgents.size() >= WIDTH * HEIGHT)
		{
			throw new IllegalArgumentException("Too many stubborn agents in network !");
		}
		nodes = new INode[WIDTH][HEIGHT];

		time = System.nanoTime();

		// normal agents
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				nodes[i][j] = factory.createAgent("A" + ((i * WIDTH) + j + 1), SimConfig.isOpinionated,
					SimConfig.MinSleep, SimConfig.MaxSleep);
			}
		}

		// absurd agents
		for (NodeCoordinate p: absurdAgents)
		{
			nodes[p.x][p.y] = factory.createAbsurdAgent("AA" + (p.x * WIDTH + p.y + 1), SimConfig.isOpinionated,
				SimConfig.MinSleep, SimConfig.MaxSleep);
		}

		// add stubborn agents and compute ProFactor
		SimConfig.ProFactor = 0;
		for (NodeCoordinate p: stubbornAgents)
		{
			nodes[p.x][p.y] = factory.createStubbornAgent("SA" + (p.x * WIDTH + p.y + 1), p.getState(),
				SimConfig.MinSleep, SimConfig.MaxSleep);
			if (p.getState().equals(STATE.YES))
			{
				SimConfig.ProFactor++;
			}
		}
		SimConfig.ProFactor /= 1.0 * stubbornAgents.size();
		Random rand = new Random();

		// add random links
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				int current = i * WIDTH + j;

				// each node will have 8 random links
				while (nodes[i][j].getFriends().size() < 8)
				{
					int friend = current;

					// choose a friend != current
					while (friend == current)
					{
						friend = rand.nextInt(WIDTH * HEIGHT);
					}

					nodes[i][j].addFriendBi(nodes[friend / WIDTH][friend % WIDTH]);
				}

				_nodes.add(nodes[i][j]);
			}
		}

		network.addNodes(_nodes);

		time = System.nanoTime() - time;

		return network;
	}
}
