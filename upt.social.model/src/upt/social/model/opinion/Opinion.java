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
 * Models human opinion. Based on external interactions, the opinion changes
 * between states.
 * 
 */
public class Opinion
{
	public static int VOTING_LIMIT = 5;
	public static int VOTING_LIMIT_DELTA = 0;

	public static int OPINION_LIMIT = 10;
	public static int OPINION_LIMIT_DELTA = 0;

	/**
	 * Quantifies the opinion. Negative values mean 'against', positive ones
	 * mean 'for'.
	 */
	protected int opinion;
	/**
	 * The state resulting when the opinion > threshold or < -threshold
	 */
	protected STATE state;
	/**
	 * Limit at which a state is formed. I.E The agent votes
	 */
	protected int votingLimit;
	/**
	 * Limit to which opinion can grow
	 */
	protected int maxOpinion;

	public Opinion(Random rand, boolean opinionated)
	{
		this(rand, STATE.NONE);
		opinion = initialState(rand, opinionated);
	}

	public Opinion(Random rand, STATE initialState)
	{
		votingLimit = VOTING_LIMIT - VOTING_LIMIT_DELTA + rand.nextInt(2 * VOTING_LIMIT_DELTA + 1);
		maxOpinion = OPINION_LIMIT - OPINION_LIMIT_DELTA + rand.nextInt(2 * OPINION_LIMIT_DELTA + 1);

		opinion = initialState.equals(STATE.YES) ? maxOpinion : -maxOpinion;
		this.state = initialState;
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
	public void changeOpinion(double friendOpinion, double trustFactor, double myEducation)
	{
		// we're on same side
		if (opinion * friendOpinion > 0)
		{
			opinion = opinion + (int) (friendOpinion * trustFactor * myEducation);
		}
		else
		{
			opinion = opinion + (int) (friendOpinion * trustFactor);
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

	/**
	 * Get the node's opinion.
	 * 
	 * @return Opinion, if it is greater that the voting limit <br>
	 *         0, if he's not voting
	 */
	public double getOpinion()
	{
		if (Math.abs(opinion) >= votingLimit)
		{
			return 1.0 * opinion / votingLimit;
		}
		else
		{
			return 0;
		}
	}

	public STATE getState()
	{
		return state;
	}

	private int initialState(Random rand, boolean hasOpinion)
	{
		if (hasOpinion)
		{
			return -maxOpinion + 1 + rand.nextInt(2 * maxOpinion - 1);
		}
		else
		{
			return -votingLimit + 1 + rand.nextInt(2 * votingLimit - 1);
		}
	}
}
