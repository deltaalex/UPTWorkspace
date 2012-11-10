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
import upt.social.model.analog.AnalogAbsurdAgent;
import upt.social.model.analog.AnalogAgent;
import upt.social.model.analog.AnalogStubbornAgent;

/**
 * Creates analog model agents
 */
public class AnalogModelFactory implements ISocialModelFactory
{

	@Override
	public INode createAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new AnalogAgent(id, opinionated, minSleep, maxSleep);
	}

	@Override
	public INode createStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		return new AnalogStubbornAgent(id, initialState, minSleep, maxSleep);
	}

	@Override
	public INode createAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new AnalogAbsurdAgent(id, opinionated, minSleep, maxSleep);
	}

}
