/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 3:02:05 PM </copyright>
 */
package upt.listeners;

import java.awt.event.MouseEvent;

/**
 * Used by UI components to delegate an action to a listener with an event
 */
public interface ICallableMouseEvent
{
	public void performUIAction(MouseEvent e);
}