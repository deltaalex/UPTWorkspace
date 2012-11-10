/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Apr 25, 2012 2:00:50 PM </copyright>
 */
package upt.ui.help;

import upt.ui.AbstractPage;
import upt.ui.MainFrame;

/**
 * @author Alexander This page
 */
public abstract class AbstractHelpPage extends AbstractPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param frame
	 * @param title
	 */
	public AbstractHelpPage(MainFrame frame, String title)
	{
		super(frame, title);
	}

}
