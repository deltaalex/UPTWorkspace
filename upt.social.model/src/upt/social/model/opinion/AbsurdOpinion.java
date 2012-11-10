/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 10, 2011 5:34:18 PM </copyright>
 */
package upt.social.model.opinion;

import java.util.Random;

import upt.social.model.STATE;

/**
 * Models human absurd opinion. Based on external interactions, the opinion
 * changes between states in a inverse manner that in normal agents.
 * 
 */
public class AbsurdOpinion extends Opinion
{

	public AbsurdOpinion(Random rand, boolean opinionated)
	{
		super(rand, opinionated);
	}

	public AbsurdOpinion(Random rand, STATE initialState)
	{
		super(rand, initialState);
	}

	/**
	 * Updates a node's status depending on multiple factors
	 * 
	 * @param friendOpinion
	 *        - Friend's opinion = his opinion * his education
	 * @param trustFactor
	 *        - My trust factor in this friend
	 * @param myEducation
	 *        - My education. Influences opinion only if in same direction.
	 */
	@Override
	public void changeOpinion(double friendOpinion, double trustFactor, double myEducation)
	{
		// we're on same side
		if (opinion * friendOpinion > 0)
		{
			opinion = opinion - (int) (friendOpinion * trustFactor * myEducation);
		}
		else
		{
			opinion = opinion - (int) (friendOpinion * trustFactor);
		}

		// update voting status
		if (opinion >= votingLimit)
		{
			state = STATE.YES;
		}
		else if (opinion <= -votingLimit)
		{
			state = STATE.NO;
		}

		// limit opinion
		if (opinion > maxOpinion)
		{
			opinion = maxOpinion;
		}

		if (opinion < -maxOpinion)
		{
			opinion = -maxOpinion;
		}
	}
}
