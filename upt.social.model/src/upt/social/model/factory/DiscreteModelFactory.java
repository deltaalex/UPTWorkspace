/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 11, 2011 11:27:33 AM </copyright>
 */
package upt.social.model.factory;

import upt.social.model.INode;
import upt.social.model.STATE;
import upt.social.model.discrete.DiscreteAbsurdAgent;
import upt.social.model.discrete.DiscreteAgent;
import upt.social.model.discrete.DiscreteStubbornAgent;

/**
 * Creates simple agents and simple stubborn agents
 */
public class DiscreteModelFactory implements ISocialModelFactory
{

	@Override
	public INode createAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new DiscreteAgent(id, opinionated, minSleep, maxSleep);
	}

	@Override
	public INode createStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		return new DiscreteStubbornAgent(id, initialState, minSleep, maxSleep);
	}

	@Override
	public INode createAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new DiscreteAbsurdAgent(id, opinionated, minSleep, maxSleep);
	}

}
