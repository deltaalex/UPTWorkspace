package upt.social.model.complex;

import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.social.model.Tolerance;
import upt.social.model.opinion.Opinion;

public class ComplexStubbornAgent extends ComplexAgent
{
	public ComplexStubbornAgent(String id, STATE initialState, int minSleep, int maxSleep)
	{
		super(id, true, minSleep, maxSleep);
		opinion = new Opinion(rand, initialState);
		tolerance = new Tolerance(1);
	}

	@Override
	public boolean changeState()
	{
		// nothing - he's stubborn !
		return false;
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
