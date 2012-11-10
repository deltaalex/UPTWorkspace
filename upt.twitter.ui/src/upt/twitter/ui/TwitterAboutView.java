/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 1:36:17 PM </copyright>
 */
package upt.twitter.ui;

import javax.swing.JFrame;

import upt.ui.AbstractAboutView;
import upt.ui.utils.SystemUIConfig;

public class TwitterAboutView extends AbstractAboutView
{
	private static final long serialVersionUID = 1L;

	public TwitterAboutView(JFrame parent)
	{

		super(parent, "Credits");
	}

	@Override
	protected String getContentPage()
	{
		return SystemUIConfig.CREDITS_FOLDER + "/about.html";
	}
}
