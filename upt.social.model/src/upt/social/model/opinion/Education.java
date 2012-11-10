/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 10, 2011 5:52:47 PM </copyright>
 */
package upt.social.model.opinion;

import java.util.Random;

/**
 * Quantifies educational levels
 */
public class Education
{
	public static final double MAX_EDUCATION = 5.0;

	public static final double NONE = 0.25;
	public static final double BAC = 0.75;
	public static final double BACHELOR = 1.0;
	public static final double MASTER = 1.5;
	public static final double PHD = 2.5;
	public static final double ACAD = 5.0;

	public static final int BAC_AGE = 18;
	public static final int BACHELOR_AGE = 22;
	public static final int MASTER_AGE = 24;
	public static final int PHD_AGE = 27;
	public static final int ACAD_AGE = 50;

	public static final double BAC_PROB = 0.75;
	public static final double BACHELOR_PROB = 0.8;
	public static final double MASTER_PROB = 0.9;
	public static final double PHD_PROB = 0.5;
	public static final double ACAD_PROB = 0.1; // 2.7% ?

	public static boolean isBac(Random rand)
	{
		return rand.nextDouble() < BAC_PROB;
	}

	public static boolean isBachelor(Random rand)
	{
		return rand.nextDouble() < BACHELOR_PROB;
	}

	public static boolean isMaster(Random rand)
	{
		return rand.nextDouble() < MASTER_PROB;
	}

	public static boolean isPhD(Random rand)
	{
		return rand.nextDouble() < PHD_PROB;
	}

	public static boolean isAcad(Random rand)
	{
		return rand.nextDouble() < ACAD_PROB;
	}
}
