package upt.social.model.complex;

import upt.social.model.NODETYPE;
import upt.social.model.opinion.AbsurdOpinion;

public class ComplexAbsurdAgent extends ComplexAgent
{
	public ComplexAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		super(id, opinionated, minSleep, maxSleep);
		opinion = new AbsurdOpinion(rand, opinionated);
	}

	@Override
	public NODETYPE getNodeType()
	{
		return NODETYPE.ABSURD;
	}
}
