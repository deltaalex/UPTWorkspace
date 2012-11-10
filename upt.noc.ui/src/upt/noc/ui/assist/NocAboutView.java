/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 1:36:17 PM </copyright>
 */
package upt.noc.ui.assist;

import javax.swing.JFrame;

public class NocAboutView extends AboutView
{
	private static final long serialVersionUID = 1L;

	public NocAboutView(JFrame parent)
	{
		super(parent, "Credits");
	}

	@Override
	protected String getCredits()
	{
		return "<HTML> " + "<h3> NOC Simulator - version 1.0</h3> 15 May 2011 <br><br>"
			+ "<h2>Lead Programmer</h2> Alexandru Topîrceanu <br><br>"
			+ "<h2>Support</h2>\"Politehnica\" University Timisoara, Romania<br><br>" + "</HTML>";
	}
}
