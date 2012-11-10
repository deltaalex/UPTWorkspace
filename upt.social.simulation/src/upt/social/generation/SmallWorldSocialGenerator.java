package upt.social.generation;

import java.util.Random;
import java.util.Vector;

import upt.social.model.INode;
import upt.social.model.SocialNetwork;

/**
 * Creates a mesh network of agents with mostly vertical and horizontal links
 * between them. Some random links are removed and, instead, <b>long range links
 * between random distant nodes</b> are inserted. This simulates a real-world
 * social mesh better. <br>
 * Can be parameterized.
 */
public class SmallWorldSocialGenerator
{

	/**
	 * Creates a parameterized mesh network
	 * 
	 * @param network
	 *        Any regular social network.
	 * @param longRangeDensity
	 *        Double value between [0,1]. The closer to 1, the more long range
	 *        links are inserted. A factor of 0.5 will add SQRT(W*H) links to
	 *        the network, thus each node will have a long range links with the
	 *        probability of 1/SQRT(W*H).
	 * @return The new network with long range links inserted.
	 */
	public SocialNetwork createSmallWorldNetwork(SocialNetwork network, double density)
	{
		Random rand = new Random();
		Vector<INode> nodes = network.getNodes();
		double r;

		for (INode node1: nodes)
		{
			for (int i = 0; i < node1.getFriends().size(); ++i)
			{
				r = rand.nextDouble();

				// change to long range link
				if (r >= density && node1.getFriends().size() >= 2)
				{
					// remove friend
					node1.removeFriend(i);

					INode randomFriend = nodes.get(rand.nextInt(nodes.size()));

					while (randomFriend.equals(node1) || node1.getFriends().contains(randomFriend))
					{
						randomFriend = nodes.get(rand.nextInt(nodes.size()));
					}

					node1.addFriendBi(randomFriend);
				}
			}
		}

		// // add 'nLinks' links at random nodes
		// for (int i = 0; i < nLinks; ++i)
		// {
		// // select one random source node
		// int index1 = rand.nextInt(N);
		// INode node1 = nodes.get(index1);
		//
		// // get one random friend & remove him
		// int friendIndex = rand.nextInt(node1.getFriends().size());
		// node1.removeFriend(friendIndex);
		//
		// // then add a long link to a 2nd distant node
		// int index2 = index1;
		//
		// // make sure we don't have the same node
		// while (index2 == index1)
		// {
		// index2 = rand.nextInt(N);
		// }
		//
		// INode node2 = nodes.get(index2);
		// node1.addFriendBi(node2);
		// }

		return network;
	}
}
