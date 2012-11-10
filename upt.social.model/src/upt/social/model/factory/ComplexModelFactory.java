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
import upt.social.model.complex.ComplexAbsurdAgent;
import upt.social.model.complex.ComplexAgent;
import upt.social.model.complex.ComplexStubbornAgent;

/**
 * Creates agents and stubborn agents
 */
public class ComplexModelFactory implements ISocialModelFactory
{

	@Override
	public INode createAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new ComplexAgent(id, opinionated, minSleep, maxSleep);
	}

	@Override
	public INode createStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		return new ComplexStubbornAgent(id, initialState, minSleep, maxSleep);
	}

	@Override
	public INode createAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		return new ComplexAbsurdAgent(id, opinionated, minSleep, maxSleep);
	}

}
