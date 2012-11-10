/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package noc.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import noc.ui.utils.MapFactory;

public class PageConfiguration extends AbstractPage
{
	private static final long serialVersionUID = 1L;

	public PageConfiguration(JFrame frame)
	{
		super(frame, "Configuration");

	}

	@Override
	protected void initUI()
	{
		JPanel j = MapFactory.createNocMap(this, toolTip, 0, 100, 400, 400);
		// j.setSize(400, 400);
	}
}
