/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Nov 7, 2011 10:58:13 AM </copyright>
 */
package upt.social.model;

import java.util.Vector;

/**
 * Encapsulates the tolerance types of an agent at any time. <br>
 * Holds : <br>
 * - dynamic tolerance (measured at each poll; depends on the state of all
 * friends) <br>
 * - static tolerance (measured at start / link change; depends on the node
 * types) <br>
 * - specific tolerance (measured at start / link change; depends on the node
 * types and states) <br>
 */
public class Tolerance
{
	private double tolerance;
	private double staticTolerance;
	private double specificTolerance;
	private double dynamicTolerance;

	private int nFriends;

	/**
	 * Tolerance modifications ratio after each interaction
	 */
	private double epsilon0 = 0.001, epsilon1 = 0.01;
	/**
	 * Tolerance modification scaling used for intolerance (0)
	 */
	private int scaling0 = 1;
	/**
	 * Tolerance modification scaling used for tolerance (1)
	 */
	private int scaling1 = 1;

	public Tolerance(double tolerance)
	{
		this.tolerance = tolerance;
		staticTolerance = -1;
		specificTolerance = -1;
		dynamicTolerance = -1;
		nFriends = 1;
	}

	/**
	 * Modifies the current node's tolerance according to the friend' tolerance
	 * value
	 * 
	 * @param friendsTolerance
	 *        - tolerance of the friend from which an opinion is taken
	 */
	public void updateTolerance(STATE myOldState, STATE myNewState, STATE friendState, double friendsTolerance)
	{
		if (myOldState.equals(STATE.NONE) || friendState.equals(STATE.NONE))
		{
			return;
		}

		// if (myOldState.equals(friendState))
		// {
		// tolerance = (tolerance + friendsTolerance) / 2;
		// }
		// else
		// {
		// tolerance = Math.max(tolerance, friendsTolerance);
		// }

		// tolerance drops
		if (myOldState.equals(friendState))
		{
			tolerance = Math.max(tolerance - epsilon0 * scaling0, 0);
		}
		// tolerance increases
		else
		{
			tolerance = Math.min(tolerance + epsilon1 * scaling1, 1);
		}

		if (myOldState.equals(myNewState))
		{
			scaling0++;
			scaling1 = 1;
		}
		else
		{
			scaling0 = 1;
			scaling1++;
		}

		// // 0+0->0 : become more intolerant
		// if (myOldState.equals(myNewState) && myOldState.equals(friendState))
		// {
		// tolerance = (tolerance + friendsTolerance) / 2;
		// }
		// // 0+1->0 : become less intolerant - tolerance raises or falls
		// // depending
		// // on the friend
		// else if (myOldState.equals(myNewState) &&
		// !myOldState.equals(friendState))
		// {
		// tolerance = (tolerance + friendsTolerance) / 2;
		// }
		// // 0+1->1 : become more tolerant
		// else if (!myOldState.equals(myNewState) &&
		// !myOldState.equals(friendState))
		// {
		// tolerance = Math.max(tolerance, friendsTolerance);
		// }
		// // 0+0->1 : impossible
		// else
		// {
		// // throw new IllegalStateException("Tolerance state exception");
		// }
	}

	public void update(Vector<INode> friends)
	{
		if (friends.size() == 0)
		{
			staticTolerance = -1;
			dynamicTolerance = -1;
			specificTolerance = -1;
			return;
		}

		// static : compute only once or on topology change
		// T1 = 1 - Si/Ti
		if (staticTolerance == -1 || nFriends != friends.size())
		{
			staticTolerance = 0.0;

			// iterate all friends
			for (INode friend: friends)
			{
				if (friend.getNodeType().equals(NODETYPE.STUBBORN))
				{
					staticTolerance += 0.0;
				}
				else
				{
					staticTolerance++;
				}
			}
			staticTolerance /= friends.size();
		}

		// specific : compute only once or on topology change
		// T2 = min(Tred, Tgreen)
		if (specificTolerance == -1 || nFriends != friends.size())
		{
			specificTolerance = 0.0;
			int tYes = 0, tNo = 0;

			// iterate all friends
			for (INode friend: friends)
			{
				if (friend.getNodeType().equals(NODETYPE.STUBBORN))
				{
					if (friend.getState().equals(STATE.YES))
					{
						tYes++;
					}
					else if (friend.getState().equals(STATE.NO))
					{
						tNo++;
					}
				}
				else
				{
					tYes++;
					tNo++;
				}
			}
			specificTolerance = 1.0 * Math.min(tYes, tNo) / friends.size();
		}

		// dynamic : compute on each update
		// T4 = min (Prob(YES), Prob(NO))
		dynamicTolerance = 0.0;

		int nYes = 0, nNo = 0;

		// iterate all friends
		for (INode friend: friends)
		{
			if (friend.getState().equals(STATE.YES))
			{
				nYes++;
			}
			else if (friend.getState().equals(STATE.NO))
			{
				nNo++;
			}
		}
		dynamicTolerance = 1.0 * Math.min(nYes, nNo) / friends.size();

		nFriends = friends.size();
	}

	/**
	 * @return the tolerance
	 */
	public double getTolerance()
	{
		return tolerance;
	}

	/**
	 * @return the staticTolerance
	 */
	public double getStaticTolerance()
	{
		return staticTolerance;
	}

	/**
	 * @return the specificTolerance
	 */
	public double getSpecificTolerance()
	{
		return specificTolerance;
	}

	/**
	 * @return the dynamicTolerance
	 */
	public double getDynamicTolerance()
	{
		return dynamicTolerance;
	}

	/**
	 * Sets all tolerances to 0 then returns itself
	 * 
	 * @return [0, 0, 0] for all three tolerances
	 */
	public Tolerance getStubbornTolerance()
	{
		staticTolerance = 0;
		specificTolerance = 0;
		dynamicTolerance = 0;

		return this;
	}

	@Override
	public String toString()
	{
		return tolerance + "(" + scaling0 + "," + scaling1 + ")";
	}
}
