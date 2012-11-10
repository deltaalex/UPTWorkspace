/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 1:36:17 PM </copyright>
 */
package upt.social.ui;

import javax.swing.JFrame;

import upt.ui.AbstractAboutView;
import upt.ui.utils.SystemUIConfig;

public class SocialAboutView extends AbstractAboutView
{
	private static final long serialVersionUID = 1L;

	public SocialAboutView(JFrame parent)
	{

		super(parent, "Credits");
	}

	// @Override
	// protected String getCredits()
	// {
	// return "<HTML> " +
	// "<h2> Social Simulator - version 1.0</h2> 29 September 2011 <br>"
	// + "<h2>Concept & Design</h2> Alexandru Topîrceanu<br>"
	// +
	// "<h2>Programming & Testing</h2> Alexandru Topîrceanu, 2nd Year CS Master <br>"
	// +
	// "<h2>Acknowledgements</h2> ACSA Research Group <br> \"Politehnica\" University Timisoara, Romania <br>"
	// + "<h2>Contact</h2> <a href>alex.topirceanu@yahoo.com</a> <br>" +
	// "</HTML>";
	// }

	@Override
	protected String getContentPage()
	{
		return SystemUIConfig.CREDITS_FOLDER + "/about.html";
	}
}
