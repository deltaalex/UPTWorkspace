/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 3:02:05 PM </copyright>
 */
package upt.listeners;

import java.awt.event.KeyEvent;

/**
 * Used by UI components to delegate an action to a listener with an event
 */
public interface ICallableKeyEvent
{
	public void performUIAction(KeyEvent e);
}