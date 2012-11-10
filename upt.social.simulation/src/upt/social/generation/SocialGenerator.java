package upt.social.generation;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import upt.social.model.INode;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.ISocialModelFactory;
import upt.social.state.SimConfig;

/**
 * Creates a mesh network of agents with vertical and horizontal links between
 * them. Can be parameterized.
 */
public class SocialGenerator
{
	protected long time;
	private int proAgents = 0;
	protected ISocialModelFactory factory;

	public SocialGenerator(ISocialModelFactory modelFactory)
	{
		this.factory = modelFactory;
		if (factory == null)
		{
			throw new IllegalArgumentException("Factory cannot be null!");
		}
	}

	/**
	 * Creates a parameterized mesh network
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

		// add links on OX and OY axes
		// 8 1 2
		// 7 x 3
		// 6 5 4
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				if (LINKS.isVertical(links))
				{
					// 1 = N
					try
					{
						nodes[i][j].addFriendBi(nodes[i][j - 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}
					// 5 = S
					try
					{
						nodes[i][j].addFriendBi(nodes[i][j + 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}
				}

				if (LINKS.isHorizontal(links))
				{
					// 3 = E
					try
					{
						nodes[i][j].addFriendBi(nodes[i + 1][j]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}

					// 7 = W
					try
					{
						nodes[i][j].addFriendBi(nodes[i - 1][j]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}
				}

				if (LINKS.isPrimaryDiagonal(links))
				{
					// 4 = SE
					try
					{
						nodes[i][j].addFriendBi(nodes[i + 1][j + 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}

					// 8 = NW
					try
					{
						nodes[i][j].addFriendBi(nodes[i - 1][j - 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}
				}

				if (LINKS.isSecondaryDiagonal(links))
				{
					// 2 = NE
					try
					{
						nodes[i][j].addFriendBi(nodes[i + 1][j - 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}

					// 6 = SW
					try
					{
						nodes[i][j].addFriendBi(nodes[i - 1][j + 1]);
					}
					catch (IndexOutOfBoundsException e)
					{/*ignore*/}
				}

				_nodes.add(nodes[i][j]);
			}
		}

		network.addNodes(_nodes);

		time = System.nanoTime() - time;

		return network;
	}

	/**
	 * Returns the time required to create the last network. Base time is
	 * calculated in nanoseconds. <br>
	 * 
	 * @param division
	 *        Set value to : <br>
	 *        <b>TimeUnit.NANOSECONDS</b> for nanoseconds <br>
	 *        <b>TimeUnit.MICROSECONDS</b> for microseconds <br>
	 *        <b>TimeUnit.MILLISECONDS</b> for milliseconds <br>
	 *        <b>TimeUnit.SECONDS</b> for seconds <br>
	 *        <b>TimeUnit.MINUTES</b> for minutes <br>
	 * @return Time amount
	 */
	public long getTime(TimeUnit division)
	{
		if (division.equals(TimeUnit.NANOSECONDS))
		{
			return time;
		}
		else if (division.equals(TimeUnit.MICROSECONDS))
		{
			return time / 1000;
		}
		else if (division.equals(TimeUnit.MILLISECONDS))
		{
			return time / 1000000;
		}
		else if (division.equals(TimeUnit.SECONDS))
		{
			return time / 1000000000;
		}
		else if (division.equals(TimeUnit.MINUTES))
		{
			return time / 1000000000 * 60;
		}
		else
		{
			return time;
		}
	}

	/**
	 * Returns the time required to create the last network. Base time is
	 * calculated in nanoseconds. <br>
	 * 
	 * @return Time amount
	 */
	public long getTime()
	{
		return time;
	}

	/**
	 * Returns the number of pro (positive) stubborn agents introduced into the
	 * network
	 * 
	 * @return Integer value
	 */
	public int getProAgents()
	{
		return proAgents;
	}
}
