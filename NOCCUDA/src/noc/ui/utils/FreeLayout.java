/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 13, 2011 6:47:29 PM </copyright>
 */
package noc.ui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * @author Alexander
 * 
 */
public class FreeLayout implements LayoutManager
{
	private int width, height;

	public FreeLayout(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	public void layoutContainer(Container c)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	public Dimension minimumLayoutSize(Container c)
	{
		return new Dimension(width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	public Dimension preferredLayoutSize(Container c)
	{
		return new Dimension(width, height);
	}

	@Deprecated
	public void removeLayoutComponent(Component arg0)
	{ /*nothing*/
	}

	@Deprecated
	public void addLayoutComponent(String arg0, Component arg1)
	{ /*nothing*/
	}

}
