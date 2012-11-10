package upt.social.model.discrete;

import java.util.Random;
import java.util.Vector;

import upt.social.model.Credibility;
import upt.social.model.INode;
import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;

/**
 * Simplest type of agent: can have one of the 3 states : YES, NO, NONE
 * 
 */
public class DiscreteAgent implements INode
{
	/**
	 * Must guarantee that each id is unique.
	 */
	private String id;
	/**
	 * Stores the agent's state.
	 */
	protected STATE state;
	/**
	 * Keeps track if the node has changed it's status during the last tick.
	 */
	protected boolean modified;
	/**
	 * List of connections
	 */
	protected Vector<INode> friends;
	/**
	 * Stores the agent's tolerance
	 */
	protected Tolerance tolerance;
	/**
	 * Stores agent's credibility
	 */
	private Credibility credibility;
	/**
	 * Time until the node is active again. Is decremented at each clock.
	 */
	private int sleep;
	/**
	 * Interval for sleeping
	 */
	private int minSleep, maxSleep;
	/**
	 * Local random generator
	 */
	protected Random rand;
	/**
	 * Active flag
	 */
	protected boolean isActive;

	public DiscreteAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		this.id = id;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		rand = new Random();

		friends = new Vector<INode>();
		tolerance = new Tolerance(1);
		modified = false;
		sleep = getSleep();
		state = initialState(opinionated);
	}

	@Override
	public boolean changeState()
	{
		sleep--;

		// talk to a friend
		if (sleep <= 0)
		{
			// pick a random friend
			int index = rand.nextInt(friends.size());
			// stop if the friend is not active
			if (!friends.get(index).isActive())
			{
				return false;
			}

			// get friend's opinion and update yours
			STATE newState = friends.get(index).getState();
			// detect if opinion modified, then set it
			modified = !state.equals(newState);
			setState(newState);

			// compute next sleep interval
			sleep = getSleep();

			return modified;
		}
		return false;
	}

	@Override
	public double getStateAsPercentage()
	{
		if (state.equals(STATE.YES))
		{
			return 0;
		}
		else if (state.equals(STATE.YES))
		{
			return 1;
		}
		else
		{
			return 0.5;
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public STATE getState()
	{
		return state;
	}

	protected void setState(STATE newState)
	{
		if (!newState.equals(STATE.NONE))
		{
			state = newState;
		}
	}

	@Override
	public Tolerance getTolerance()
	{
		tolerance.update(friends);
		return tolerance;
	}

	@Override
	public Credibility getCredibility()
	{
		return credibility;
	}

	@Override
	public void addFriendUni(INode node)
	{
		if (!friends.contains(node) && !node.equals(this))
		{
			friends.add(node);
		}
	}

	@Override
	public void addFriendBi(INode node)
	{
		if (!friends.contains(node) && !node.equals(this))
		{
			friends.add(node);
			node.addFriendUni(this);
		}
	}

	@Override
	public boolean removeFriend(INode node)
	{
		if (friends.contains(node))
		{
			friends.remove(node);
			node.removeFriend(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFriend(int index)
	{
		INode friend = friends.remove(index);
		if (friend != null)
		{
			friend.removeFriend(this);
		}
		return friend != null;
	}

	@Override
	public boolean isFriend(INode node)
	{
		return friends.contains(node);
	}

	@Override
	public boolean isActive()
	{
		return isActive;
	}

	@Override
	public void initialize()
	{
		credibility = new Credibility(this, friends);
	}

	@Override
	public Vector<INode> getFriends()
	{
		return friends;
	}

	@Override
	public boolean isModified()
	{
		return modified;
	}

	@Override
	public NODETYPE getNodeType()
	{
		return NODETYPE.NORMAL;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DiscreteAgent)
		{
			return ((DiscreteAgent) obj).id.equals(id);
		}
		return false;
	}

	@Override
	public String toString()
	{
		String ss = "Node " + id + " has " + friends.size() + " friends : ";

		for (INode node: friends)
		{
			ss += node.getId() + " ";
		}

		return ss;
	}

	private int getSleep()
	{
		return rand.nextInt(maxSleep - minSleep + 1) + minSleep;
	}

	private STATE initialState(boolean hasOpinion)
	{
		if (hasOpinion)
		{
			return STATE.getRandomState();
		}
		else
		{
			return STATE.NONE;
		}
	}
}
