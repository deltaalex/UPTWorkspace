/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Feb 17, 2012 11:51:37 AM </copyright>
 */
package upt.social.model;

import java.util.Vector;

/**
 * Models an agent's credibility factor. <br>
 * Stores the number of opinion modifications during lifetime and the autonomy
 * factor.
 */
public class Credibility
{
	/**
	 * Keeps track of how many times this agent has changed its opinion
	 */
	private int nModifications;
	/**
	 * Keeps track of how many times this agent has interacted with others
	 */
	private int nInteractions;
	/**
	 * Stores a distance ratio to the nearest stubborn agents. The greater the
	 * ratio the smaller its autonomy, i.e. : <br>
	 * 1 : the node has no SA friends <br>
	 * 5 : the node has a SA friend <br>
	 * 10 : the node is a SA <br>
	 */
	private int beta;

	/**
	 * Stores a marker for stubborn agents.
	 */
	private boolean isStubborn;

	public Credibility(INode node, Vector<INode> friends)
	{
		nModifications = 1;
		nInteractions = 1;
		isStubborn = false;

		if (node.getNodeType().equals(NODETYPE.STUBBORN))
		{
			beta = 10;
			isStubborn = true;
		}
		else
		{
			int countSAFriends = 0;
			for (INode friend: friends)
			{
				if (friend.getNodeType().equals(NODETYPE.STUBBORN))
				{
					countSAFriends++;
				}
			}

			if (countSAFriends > 0)
			{
				beta = Math.min(4 + countSAFriends - 1, 10);
			}
			else
			{
				beta = 1;
			}
		}
	}

	public void updateAlpha(boolean modified)
	{
		nModifications += modified == true ? 1 : 0;
		nInteractions++;
	}

	public double getCredibility()
	{
		double alpha = Math.min(1.0 * nModifications / nInteractions + 0.1, 1);

		return isStubborn ? 0.5 : Math.pow(Math.E, -alpha * beta);
	}
}
