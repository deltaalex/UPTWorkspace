package upt.social.generation.mesh;

import java.util.Vector;

import upt.social.generation.SocialGenerator;
import upt.social.model.INode;
import upt.social.model.NodeCoordinate;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.ISocialModelFactory;

/**
 * Creates a mesh network of agents with vertical and horizontal links between
 * them. Additionally, links are added between the first and last nodes on each
 * row and column.<br>
 * Can be parameterized.
 */
public class HyperCube2DSocialGenerator extends SocialGenerator
{
	public HyperCube2DSocialGenerator(ISocialModelFactory modelFactory)
	{
		super(modelFactory);
	}

	/**
	 * Creates a parameterized 2D hyper cube network.
	 * 
	 * @param WIDTH
	 *        1/2 of the width of the mesh
	 * @param HEIGHT
	 *        1/2 of the height of the mesh
	 * @param stubbornAgents
	 *        List of coordinates at which stubborn agents will be inserted. The
	 *        data structure also contains their STATE.
	 * @return The new network
	 */
	@Override
	public SocialNetwork createNewNetwork(int WIDTH, int HEIGHT, Vector<NodeCoordinate> stubbornAgents,
		Vector<NodeCoordinate> absurdAgents, String links)
	{
		time = System.nanoTime();

		if (WIDTH % 2 != 0 || HEIGHT % 2 != 0)
		{
			throw new IllegalArgumentException("Both the width and height must be even numbers!");
		}

		int W = WIDTH / 2;
		int H = HEIGHT / 2;

		SocialNetwork network = super.createNewNetwork(WIDTH, HEIGHT, stubbornAgents, absurdAgents, links);
		Vector<INode> nodes = network.getNodes();

		// cut out a cross in the middle
		for (int i = 0; i < WIDTH; ++i)
		{
			for (int j = 0; j < HEIGHT; ++j)
			{
				if (i == HEIGHT / 2 - 1)
				{
					nodes.get(i * HEIGHT + j).removeFriend(nodes.get((i + 1) * HEIGHT + j));
				}
				if (j == WIDTH / 2 - 1)
				{
					nodes.get(i * HEIGHT + j).removeFriend(nodes.get(i * HEIGHT + j + 1));
				}

			}
		}

		// add long range links
		for (int i = 0; i < W; ++i)
		{
			for (int j = 0; j < H; ++j)
			{
				// horizontally long range
				nodes.get(i * HEIGHT + j).addFriendBi(nodes.get(i * HEIGHT + j + W));

				// vertically long range
				nodes.get(i * HEIGHT + j).addFriendBi(nodes.get((i + H) * HEIGHT + j));
			}
		}

		time = System.nanoTime() - time;

		return network;
	}
}
