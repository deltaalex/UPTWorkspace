package upt.social.model;

import java.util.Vector;

/**
 * Interface for all (complex) network nodes
 */
public interface INode
{

	/**
	 * Obtain the ID of a node
	 * 
	 * @return String representation of the id
	 */
	public String getId();

	/**
	 * Get node state
	 * 
	 * @return The state in one of its forms
	 */
	public STATE getState();

	/**
	 * Get the node state as a double value between [0, 1]
	 * 
	 * @return The state expressed in percentage
	 */
	public double getStateAsPercentage();

	/**
	 * The node changes its state by randomly asking one of its friends for
	 * opinion. <br>
	 * 
	 * @return true if it has changed it's state; false if it has kept it.
	 * */
	public boolean changeState();

	/**
	 * Get the node tolerance. Tolerance (theta) can be quantified by three
	 * double values.
	 * 
	 * @see Tolerance
	 * @return Tolerance of the node
	 */
	public Tolerance getTolerance();

	/**
	 * Get the node credibility. Credibility (c) can be quantified by the amoint
	 * of opinion changes and autonomy regarding stubborn agents.
	 * 
	 * @return
	 */
	public Credibility getCredibility();

	/**
	 * Adds one link towards the given node. The link is <b>unidirectional</b>.
	 * 
	 * @param node
	 *        Friendly node
	 */
	public void addFriendUni(INode node);

	/**
	 * Adds one link towards the given node. The link is <b>bidirectional</b>.
	 * 
	 * @param node
	 *        Friendly node
	 */
	public void addFriendBi(INode node);

	/**
	 * Removes the link (both unidirectional/single bidirectional) between the
	 * current node and the given node
	 * 
	 * @param node
	 *        Ex-friendly node
	 * @return <b>true</b> if the link was found and removed
	 */
	public boolean removeFriend(INode node);

	/**
	 * Removes the link (both unidirectional/single bidirectional) between the
	 * current node and the node found at the given index
	 * 
	 * @param index
	 *        Index in the node's friends list
	 * @return <b>true</b> if the link was found and removed
	 */
	public boolean removeFriend(int index);

	/**
	 * Checks whether the given node is a friend (i.e. a link exists to it)
	 * 
	 * @param node
	 *        Friendly node
	 * @return <b>true</b> if a link from <i>this->node</i>, and not vice-versa,
	 *         exists
	 */
	public boolean isFriend(INode node);

	/**
	 * Checks whether the agent is active or not
	 * 
	 * @return
	 */
	public boolean isActive();

	/**
	 * Enables implementing local initialization. <br>
	 * Called after the network has been created.
	 */
	public void initialize();

	/**
	 * Get all of the node's friends
	 * 
	 * @return List of connections
	 */
	public Vector<INode> getFriends();

	/**
	 * Keeps track if the node has changed it's status during the last tick.
	 */
	public boolean isModified();

	/**
	 * Gets the special status of a node.
	 */
	public NODETYPE getNodeType();
}