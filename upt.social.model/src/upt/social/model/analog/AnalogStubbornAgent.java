package upt.social.model.analog;

import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;

public class AnalogStubbornAgent extends AnalogAgent
{
	// window time
	private int activeTime = 100;
	// (1/lambda) reactivation time
	private int resetTime = 100;

	private int counter;

	public AnalogStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		super(id, true, minSleep, maxSleep);
		state = initialState.equals(STATE.YES) ? 1.0 : 0;
		tolerance = new Tolerance(0);
		isActive = true;
		counter = 0;
	}

	@Override
	public boolean changeState()
	{
		if (counter >= resetTime)
		{
			counter = 0;
		}

		if (counter < activeTime)
		{
			isActive = true;
		}
		else
		{
			isActive = false;
		}

		counter++;

		// nothing - he's stubborn !
		return false;
	}

	@Override
	protected void setState(double friendsState, double tolerance, double confidence, double credibility)
	{
		/*nothing*/
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
