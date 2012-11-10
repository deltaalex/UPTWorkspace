/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Dec 6, 2011 12:25:22 PM </copyright>
 */
package upt.social.model;

/**
 * Models an agent's confidence in another friend. <br>
 * Confidence value is a double that increases whenever both communicating
 * agents have the same opinion, and falls whenever they have opposite opinions. <br>
 * The confidence may be forced to 0 (minimum) if a certain uncertainty pattern
 * is detected in a friend (e.g. "11110"). <br>
 * If the confidence is lost it will not rise again for a certain amount of
 * cycles. When the cycles pass, the confidence will rise to 1 (maximum). <br>
 * 
 */
public class Confidence
{
	/**
	 * Pattern used to discredit a friend.<br>
	 * i.e. If opinion changes like Y,Y,Y,N or N,N,N,Y.
	 */
	private static final int[] PATTERN = new int[] {1, 1, 1, 0};
	/**
	 * Maximum time until a friend is trusted again.
	 */
	private static final int TRUST_TIMEOUT = 150;
	/**
	 * Increment used to increase/decrease confidence in one another.
	 */
	private static final double CONFIDENCE_INCREMENT = 0.1;

	/**
	 * Confidence in another node
	 */
	private double confidence;
	/**
	 * Level of confidence. If it reaches 0 the friend becomes discredited.
	 */
	private int discreditationLevel;
	/**
	 * Previous state of the friend
	 */
	private STATE oldFriendState = STATE.NONE;
	/**
	 * Concrete time until a friend is trusted again. <br>
	 * Initial value is TRUST_TIMEOUT and decreases until it reaches 0.
	 */
	private int trustTimeout;

	public Confidence()
	{
		confidence = 1.0;
		discreditationLevel = 0;
		trustTimeout = 0;
	}

	/**
	 * Update the confidence in one friend.
	 * 
	 * @param myState
	 *        - Node's current opinion
	 * @param friendState
	 *        - Friend's current opinion
	 */
	public void updateConfidence(STATE myState, STATE friendState)
	{
		// update confidence and detect pattern
		if (trustTimeout == 0)
		{
			if (myState.equals(STATE.NONE) || friendState.equals(STATE.NONE))
			{
				return;
			}

			if (myState.equals(friendState))
			{
				confidence = Math.min(confidence + CONFIDENCE_INCREMENT, 1);
			}
			else
			{
				confidence = Math.max(confidence - CONFIDENCE_INCREMENT, 0);
			}

			if ((oldFriendState.equals(friendState) && PATTERN[discreditationLevel] == PATTERN[discreditationLevel + 1])
				|| (!oldFriendState.equals(friendState) && PATTERN[discreditationLevel] != PATTERN[discreditationLevel + 1]))
			{
				discreditationLevel++;
			}
			else
			{
				discreditationLevel = 0;
			}

			// pattern detected or confidence reached 0 => discredit
			if (discreditationLevel == PATTERN.length - 1 || confidence == 0)
			{
				discreditationLevel = 0;
				confidence = 0;
				trustTimeout = TRUST_TIMEOUT;
			}
		}

		oldFriendState = friendState;
	}

	/**
	 * Update discreditation timeout with the current sleep value
	 * 
	 * @param sleep
	 *        - Integer value
	 */
	public void updateTimeout(int sleep)
	{
		if (trustTimeout > 0)
		{
			trustTimeout = Math.max(trustTimeout - sleep, 0);
		}
	}

	public double getConfidence()
	{
		return confidence;
	}
}
