/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 20, 2011 3:26:34 PM </copyright>
 */
package upt.noc.structures;

public class NocLink
{
	private NocNode fromNode, toNode;
	private boolean isLongRange;

	public NocLink(NocNode fromNode, NocNode toNode)
	{
		this.fromNode = fromNode;
		this.toNode = toNode;
		isLongRange = true;
	}

	/**
	 * @return the fromNode
	 */
	public NocNode getFromNode()
	{
		return fromNode;
	}

	/**
	 * @return the toNode
	 */
	public NocNode getToNode()
	{
		return toNode;
	}

	/**
	 * @return the isLongRange
	 */
	public boolean isLongRange()
	{
		return isLongRange;
	}

}
