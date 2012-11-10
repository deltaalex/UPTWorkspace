/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 11, 2011 11:25:32 AM </copyright>
 */
package upt.social.model.factory;

import upt.social.model.INode;
import upt.social.model.STATE;

/**
 * An abstract factory interface that creates simple or complex (opinion) nodes
 * 
 */
public interface ISocialModelFactory
{
	public abstract INode createAgent(String id, boolean opinionated, int minSleep, int maxSleep);

	public abstract INode createStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep);

	public abstract INode createAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep);
}
