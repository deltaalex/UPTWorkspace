/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 2, 2011 12:12:41 AM </copyright>
 */
package upt.ui.utils;

/**
 * @author Alexander Implementors of this interface must be java awt components
 *         that can be hidden and toggled by tabs.
 */
public interface ITabbable
{
	public String getTabTitle();

	public void setVisible(boolean visible);
}
