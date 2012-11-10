package upt.social.model.discrete;

import upt.social.model.NODETYPE;
import upt.social.model.STATE;

public class DiscreteAbsurdAgent extends DiscreteAgent
{
	public DiscreteAbsurdAgent(String id, boolean opinionated, int minSleep, int maxSleep)
	{
		super(id, opinionated, minSleep, maxSleep);
	}

	@Override
	protected void setState(STATE newState)
	{
		if (newState.equals(STATE.YES))
		{
			state = STATE.NO;
		}
		else if (newState.equals(STATE.NO))
		{
			state = STATE.YES;
		}
	}

	@Override
	public NODETYPE getNodeType()
	{
		return NODETYPE.ABSURD;
	}
}
