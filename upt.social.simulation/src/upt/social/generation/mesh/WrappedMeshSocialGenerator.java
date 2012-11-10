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
public class WrappedMeshSocialGenerator extends SocialGenerator
{
	public WrappedMeshSocialGenerator(ISocialModelFactory modelFactory)
	{
		super(modelFactory);
	}

	/**
	 * Creates a parameterized wrap-around mesh network.
	 * 
	 * @param WIDTH
	 *        Width of the mesh
	 * @param HEIGHT
	 *        Height of the mesh
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

		SocialNetwork network = super.createNewNetwork(WIDTH, HEIGHT, stubbornAgents, absurdAgents, links);
		Vector<INode> nodes = network.getNodes();

		// add end-to-end links
		// N-S
		for (int i = 0; i < WIDTH; ++i)
		{
			try
			{
				nodes.get(i).addFriendBi(nodes.get((HEIGHT - 1) * WIDTH + i));
			}
			catch (IndexOutOfBoundsException e)
			{/*ignore*/}

		}

		// W-E
		for (int i = 0; i < HEIGHT; ++i)
		{
			try
			{
				nodes.get(i * WIDTH).addFriendBi(nodes.get((i + 1) * WIDTH - 1));
			}
			catch (IndexOutOfBoundsException e)
			{/*ignore*/}

		}

		time = System.nanoTime() - time;

		return network;
	}
}
