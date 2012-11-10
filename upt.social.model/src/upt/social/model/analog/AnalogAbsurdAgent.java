package upt.social.model.analog;

import upt.social.model.NODETYPE;

public class AnalogAbsurdAgent extends AnalogAgent
{
	public AnalogAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		super(id, opinionated, minSleep, maxSleep);
	}

	@Override
	protected void setState(double friendsState, double tolerance, double confidence, double credibility)
	{
		friendsState = 1 - friendsState;
		super.setState(friendsState, tolerance, confidence, credibility);
	}

	@Override
	public NODETYPE getNodeType()
	{
		return NODETYPE.ABSURD;
	}
}
