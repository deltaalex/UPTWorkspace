package upt.social.model;

import java.util.Random;

public enum STATE
{
	NO, YES, NONE;

	private static Random rand = new Random();

	public String stateToString()
	{
		if (this.equals(NO))
		{
			return "x";
		}
		else if (this.equals(YES))
		{
			return "o";
		}
		else
		{
			return ".";
		}
	}

	/**
	 * Randomly returns one of the two states.
	 * 
	 * @return YES/NO
	 */
	public static STATE getRandomState()
	{
		int r = rand.nextInt(2);

		if (r == 0)
		{
			return NO;
		}
		else
		{
			return YES;
		}
	}
}
