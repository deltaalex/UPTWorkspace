package upt.social.model.complex;

import java.util.Random;
import java.util.Vector;

import upt.social.model.Credibility;
import upt.social.model.INode;
import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;
import upt.social.model.opinion.Age;
import upt.social.model.opinion.Opinion;

/**
 * Human-like behavior of agent: has an opinion, education, age and a confidence
 * factor in his friends.
 * 
 */
public class ComplexAgent implements IComplexNode
{
	/**
	 * Must guarantee that each id is unique.
	 */
	private String id;
	/**
	 * Stores the agent's opinion, which outputs a state at a given time.
	 */
	protected Opinion opinion;
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
	 * Age of the agent. Each simulation tick increases it's age by <1> day.
	 */
	private Age age;
	/**
	 * Local random generator
	 */
	protected Random rand;
	/**
	 * Active flag
	 */
	protected boolean isActive;

	public ComplexAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		this.id = id;
		this.minSleep = minSleep;
		this.maxSleep = maxSleep;
		rand = new Random();

		friends = new Vector<INode>();
		tolerance = new Tolerance(1);
		modified = false;
		sleep = getSleep();
		opinion = new Opinion(rand, opinionated);
		age = new Age(rand);
	}

	@Override
	public boolean changeState()
	{
		sleep--;
		age.increaseAge();

		// talk to a friend...
		if (sleep <= 0)
		{
			// save old sate
			STATE oldState = getState();
			// pick a random friend
			int index = rand.nextInt(friends.size());
			// stop if the friend is not active
			if (!friends.get(index).isActive())
			{
				return false;
			}

			// get friends opinion and update yours
			// opinion = friends opinion (if >limit) * his education * your
			// confidence * your education if you have same opinion
			opinion.changeOpinion(((IComplexNode) friends.get(index)).getOpinion(), tolerance.getTolerance(),
				age.getEducation());

			// refresh current tolerance
			tolerance.updateTolerance(oldState, getState(), friends.get(index).getState(),
				friends.get(index).getTolerance().getTolerance());
			// detect if opinion modified (voting?)
			modified = !getState().equals(oldState);

			// compute next sleep interval
			sleep = getSleep();
			return modified;
		}
		return false;
	}

	@Override
	public double getOpinion()
	{
		return opinion.getOpinion() * age.getEducation();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public STATE getState()
	{
		return opinion.getState();
	}

	@Override
	public double getStateAsPercentage()
	{
		return opinion.getOpinion();
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
	public double getAgeAsPercent()
	{
		return age.getAgeAsPercent();
	}

	@Override
	public double getEducationAsPercent()
	{
		return age.getEducationAsPercent();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ComplexAgent)
		{
			return ((ComplexAgent) obj).id.equals(id);
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
}
