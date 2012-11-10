package upt.social.model.analog;

import java.util.Random;
import java.util.Vector;

import upt.social.model.Confidence;
import upt.social.model.Credibility;
import upt.social.model.INode;
import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;

/**
 * Variant of the discrete agent: has a double-value opinion between 0[NO] and
 * 1[YES], as well as a confidence factor in his friends.
 * 
 */
public class AnalogAgent implements IAnalogNode
{
	/**
	 * Used interaction model
	 */
	protected final static CommModel USED_COMM_MODEL = CommModel.CREDIBILITY_TOLERANCE;

	/**
	 * Determines if all agents are initially fully tolerant (1) or if their
	 * tolerance is a random value [0,1)
	 */
	private final static boolean isFullyTolerant = true;

	/**
	 * Must guarantee that each id is unique.
	 */
	private String id;
	/**
	 * Stores the agent's state as a value.
	 */
	protected double state;
	/**
	 * Keeps track if the node has changed its status during the last tick.
	 */
	protected boolean modified;
	/**
	 * List of connections
	 */
	protected Vector<INode> friends;
	/**
	 * List of friend confidences
	 */
	protected Vector<Confidence> confidence;
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

	public AnalogAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		this.id = id;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		rand = new Random();

		friends = new Vector<INode>();
		confidence = new Vector<Confidence>();
		tolerance = new Tolerance(isFullyTolerant ? 1.0 : rand.nextDouble());
		modified = false;
		sleep = getSleep();
		state = initialState(opinionated);
		isActive = true;
	}

	@Override
	public boolean changeState()
	{
		sleep--;

		// talk to a friend...
		if (sleep <= 0)
		{
			// save old state
			STATE oldState = getState();

			// pick a random friend
			int index = rand.nextInt(friends.size());
			// stop if the friend is not active
			if (!friends.get(index).isActive())
			{
				return false;
			}

			// get friend's opinion
			double friendsState = friends.get(index).getStateAsPercentage();
			// update my confidence
			confidence.get(index).updateConfidence(oldState, friends.get(index).getState());

			// set my new state
			setState(friendsState, tolerance.getTolerance(), confidence.get(index).getConfidence(),
				friends.get(index).getCredibility().getCredibility());

			// refresh current tolerance using old tolerance, old state, new
			// state friend's state
			tolerance.updateTolerance(oldState, getState(), friends.get(index).getState(),
				friends.get(index).getTolerance().getTolerance());

			// detect if opinion modified
			modified = !getState().equals(oldState);

			// update credibility based on opinion change
			credibility.updateAlpha(modified);

			// compute next sleep interval
			sleep = getSleep();

			// update confidence timeouts
			for (Confidence c: confidence)
			{
				c.updateTimeout(sleep);
			}

			return modified;
		}
		return false;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public double getStateAsPercentage()
	{
		return state;
	}

	@Override
	public STATE getState()
	{
		if (state < 0.5)
		{
			return STATE.NO;
		}
		else if (state > 0.5)
		{
			return STATE.YES;
		}
		else
		{
			return STATE.NONE;
		}
	}

	protected void setState(double friendsState, double tolerance, double confidence, double credibility)
	{
		// TODO debug using tolerance(I), confidence(II), both(III)

		if (USED_COMM_MODEL.equals(CommModel.TOLERANCE_ONLY))
		{
			state = tolerance * friendsState + (1 - tolerance) * state;
		}

		else if (USED_COMM_MODEL.equals(CommModel.CONFIDENCE_ONLY))
		{
			state = confidence * friendsState + (1 - confidence) * state;
		}

		else if (USED_COMM_MODEL.equals(CommModel.CREDIBILITY_ONLY))
		{
			state = credibility * friendsState + (1 - credibility) * state;
		}

		else if (USED_COMM_MODEL.equals(CommModel.CONFIDENCE_TOLERANCE))
		{
			// use confidence
			if (tolerance < 0.5)
			{
				state = confidence * friendsState + (1 - confidence) * state;
			}
			// use tolerance
			else
			{
				state = tolerance * friendsState + (1 - tolerance) * state;
			}
		}

		else if (USED_COMM_MODEL.equals(CommModel.CREDIBILITY_TOLERANCE))
		{
			// state = tolerance * credibility * friendsState + (1 - tolerance *
			// credibility) * state;

			// use credibility
			if (tolerance < 0.5)
			{
				state = credibility * friendsState + (1 - credibility) * state;
			}
			// use tolerance
			else
			{
				state = tolerance * friendsState + (1 - tolerance) * state;
			}
		}

		else
		{
			/* nothing */
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
			if (node instanceof IAnalogNode)
			{
				friends.add(node);
				confidence.add(new Confidence());
			}
			else
			{
				throw new IllegalArgumentException(
					"Analog nodes can only connect to other analog nodes ! Cannot mix models.");
			}
		}
	}

	@Override
	public void addFriendBi(INode node)
	{
		if (!friends.contains(node) && !node.equals(this))
		{
			if (node instanceof IAnalogNode)
			{
				friends.add(node);
				confidence.add(new Confidence());
				node.addFriendUni(this);
			}
			else
			{
				throw new IllegalArgumentException(
					"Analog nodes can only connect to other analog nodes ! Cannot mix models.");
			}
		}
	}

	@Override
	public boolean removeFriend(INode node)
	{
		if (friends.contains(node))
		{
			// remove both confidence and friend from lists
			confidence.remove(friends.indexOf(node));
			friends.remove(node);

			// then remove in opposite direction
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
			confidence.remove(index);
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
		if (obj instanceof AnalogAgent)
		{
			return ((AnalogAgent) obj).id.equals(id);
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

	/**
	 * The confidence in one friend is based on the number of common friends.
	 * 
	 * @return Value between 0.5 and 1.5
	 */
	// private double getConfidence(INode friend)
	// {
	// double confidence = 0.0;
	// double sum = 0.0;
	// double factor = 1.0 / (friends.size() - 1);
	//
	// // for all his friends
	// for (INode friend2: friend.getFriends())
	// {
	// // if his friend is also my friend
	// if (friends.contains(friend2))
	// {
	// sum += factor;
	// }
	// }
	//
	// // 1 or less common friends => long range => random factor
	// if (sum <= factor)
	// {
	// confidence += 1 - Math.abs(rand.nextGaussian());
	// }
	// else
	// {
	// // mesh : 4/7 inner => 1.288 confidence
	// confidence += Math.log10(sum * 9.0 + 1);
	// }
	//
	// return confidence;
	// }

	private double initialState(boolean hasOpinion)
	{
		if (hasOpinion)
		{
			return rand.nextDouble();
		}
		else
		{
			return 0.5;
		}
	}
}
