package upt.social.generation.scalefree;

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
 * Creates a scale free network of agents with links organized under a power law
 * distribution. <br>
 * Can be parameterized. Models the Ablert-Barabasi network.
 * 
 * @See <a
 *      href="http://en.wikipedia.org/wiki/Barab%C3%A1si%E2%80%93Albert_model"
 *      >Wikipedia</a>
 */
public class ScaleFreeSocialGenerator extends SocialGenerator
{
	public ScaleFreeSocialGenerator(ISocialModelFactory modelFactory)
	{
		super(modelFactory);
	}

	/**
	 * Creates a parameterized scale free network
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
		Vector<INode> sfNodes = new Vector<INode>();
		Vector<INode> nodes = new Vector<INode>();
		INode[][] meshNodes;

		if (stubbornAgents.size() >= WIDTH * HEIGHT)
		{
			throw new IllegalArgumentException("Too many stubborn agents in network !");
		}
		meshNodes = new INode[WIDTH][HEIGHT];

		time = System.nanoTime();

		// normal agents
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				meshNodes[i][j] = factory.createAgent("A" + ((i * WIDTH) + j + 1), SimConfig.isOpinionated,
					SimConfig.MinSleep, SimConfig.MaxSleep);
			}
		}

		// absurd agents
		for (NodeCoordinate p: absurdAgents)
		{
			meshNodes[p.x][p.y] = factory.createAbsurdAgent("AA" + (p.x * WIDTH + p.y + 1), SimConfig.isOpinionated,
				SimConfig.MinSleep, SimConfig.MaxSleep);
		}

		// add stubborn agents and compute ProFactor
		SimConfig.ProFactor = 0;
		for (NodeCoordinate p: stubbornAgents)
		{
			meshNodes[p.x][p.y] = factory.createStubbornAgent("SA" + (p.x * WIDTH + p.y + 1), p.getState(),
				SimConfig.MinSleep, SimConfig.MaxSleep);
			if (p.getState().equals(STATE.YES))
			{
				SimConfig.ProFactor++;
			}
		}
		SimConfig.ProFactor /= 1.0 * stubbornAgents.size();
		Random rand = new Random();

		/**
		 * Add scale free "links" (BA algorithm)
		 **/

		// mesh to list
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				nodes.add(meshNodes[i][j]);
			}
		}

		// create one initial link : [middle]<--->[middle+1]
		sfNodes.add(nodes.get(WIDTH * HEIGHT / 2));
		sfNodes.add(nodes.get(WIDTH * HEIGHT / 2 + 1));
		sfNodes.get(0).addFriendBi(sfNodes.get(1));

		nodes.remove(sfNodes.get(0));
		nodes.remove(sfNodes.get(1));

		for (int i = 0; i < nodes.size(); ++i)
		{
			boolean success = false;

			while (!success)
			{
				INode node = nodes.get(i);

				// compute sum of all degrees
				int sumpk = 0;
				for (INode sfNode: sfNodes)
				{
					sumpk += sfNode.getFriends().size();
				}

				// try to connect the new node to nodes in the network
				for (INode sfNode: sfNodes)
				{
					// degree of current sfNode
					int pi = sfNode.getFriends().size();

					// get random value
					double p = rand.nextDouble();

					// connect if p < probability
					if (p < 1.0 * pi / sumpk)
					{
						node.addFriendBi(sfNode);
					}
				}

				// if no connection was done repeat
				if (node.getFriends().size() == 0)
				{
					int index = rand.nextInt(sfNodes.size());
					node.addFriendBi(sfNodes.get(index));
					success = false;
				}
				else
				{
					// add new node to network
					if (i < WIDTH * HEIGHT / 2)
					{
						sfNodes.add(i, node);
					}
					else
					{
						sfNodes.add(node);
					}
					success = true;
				}
			}
		}

		network.addNodes(sfNodes);

		time = System.nanoTime() - time;

		return network;
	}
}
