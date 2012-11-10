package upt.social.model.discrete;

import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;

public class DiscreteStubbornAgent extends DiscreteAgent
{
	public DiscreteStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		super(id, true, minSleep, maxSleep);
		state = initialState;
		tolerance = new Tolerance(0);
	}

	@Override
	public boolean changeState()
	{
		// nothing - he's stubborn !
		return false;
	}

	@Override
	protected void setState(STATE newState)
	{
		/**/
	}

	@Override
	public Tolerance getTolerance()
	{
		return tolerance.getStubbornTolerance();
	}

	@Override
	public NODETYPE getNodeType()
	{
		return NODETYPE.STUBBORN;
	}
}
