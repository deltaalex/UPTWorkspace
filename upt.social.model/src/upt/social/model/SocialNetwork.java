package upt.social.model;

import java.util.Vector;

/**
 * Models a complex network with nodes (agents) with links between them.
 */
public class SocialNetwork
{
	// graph of nodes
	/**
	 * Each node has its own list of 'friends'
	 */
	private Vector<INode> nodes;
	/**
	 * Current day in the social network. The network starts from day one.
	 */
	private int currentDay;

	public SocialNetwork()
	{
		nodes = new Vector<INode>();
		currentDay = 1;
	}

	public void addNode(INode node)
	{
		if (!nodes.contains(node))
		{
			nodes.add(node);
		}
		node.initialize();
	}

	public void addNodes(Vector<INode> nodes)
	{
		this.nodes.addAll(nodes);

		for (INode node: nodes)
		{
			node.initialize();
		}
	}

	public Vector<INode> getNodes()
	{
		return nodes;
	}

	/**
	 * Search if a node with a given unique id is present in the network.
	 * 
	 * @param id
	 *        String id
	 * @return The node if it was found; null otherwise.
	 */
	public INode getNodeById(String id)
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

	/**
	 * Get poll results
	 * 
	 * @return Poll instance
	 * @see #PollResults
	 */
	public PollResults getPolls(long realTimeMs)
	{
		return new PollResults(nodes, realTimeMs, currentDay);
	}

	/**
	 * Add a new day to the network
	 */
	public void addDay()
	{
		currentDay++;
	}

	/**
	 * Prints various debug info. <br>
	 * 1) Prints # of links for each node
	 */
	public void debug()
	{
		for (INode node: nodes)
		{
			System.out.println(node);
		}
	}

}
