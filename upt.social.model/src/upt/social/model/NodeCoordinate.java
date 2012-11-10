/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 5, 2011 1:00:16 PM </copyright>
 */
package upt.social.model;

/**
 * Stores a node's state and (x,y) coordinates in the mesh
 * 
 */
public class NodeCoordinate
{
	public int x, y;
	private STATE state;

	public NodeCoordinate(int x, int y, STATE state)
	{
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public STATE getState()
	{
		return state;
	}
}
