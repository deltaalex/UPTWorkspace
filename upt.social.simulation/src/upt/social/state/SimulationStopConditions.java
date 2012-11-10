/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Jan 31, 2012 5:14:27 PM </copyright>
 */
package upt.social.state;

/**
 * @author Alexander Defines conditions on which the simulation can stop
 */
public class SimulationStopConditions
{

	// simulation stop conditions enabling

	/**
	 * Determines whether the simulation will stop after K watchdog steps
	 */
	public static boolean SimStopOnLength = false;

	/**
	 * Determines whether the simulation will stop after the social network has
	 * reached a low point of tolerance
	 */
	public static boolean SimStopOnTolerance = false;
	/**
	 * Determines whether the simulation will stop after the positive side has
	 * more than a given percentage
	 */
	public static boolean SimStopOnPositiveWin = false;
	/**
	 * Determines whether the simulation will stop after the negative side has
	 * more than a given percentage
	 */
	public static boolean SimStopOnNegativeWin = false;
	/**
	 * Determines whether the simulation will stop after the network's opinion
	 * change percentage has dropped below a given margin
	 */
	public static boolean SimStopOnOpinionChange = false;

	// simulation boolean stop conditions

	// conditional thresholds

	/**
	 * Length of the simulation. Expressed in monitoring ticks.
	 */
	public static int SimulationLength = 1000; // # of watchdog ticks
	/**
	 * Threshold for tolerance level
	 */
	public static double SimulationToleranceThreshold = 0.1;
	/**
	 * Threshold for positive win
	 */
	public static double SimulationPositiveWinThreshold = 0.90;
	/**
	 * Threshold for negative win
	 */
	public static double SimulationNegativeWinThreshold = 0.10;
	/**
	 * Threshold for opinion change level
	 */
	public static double SimulationOpinionChangeThreshold = 0.02;

	// simulation repetition variables

	/**
	 * Determines whether the simulation is repeated for
	 * <b>SimulationIterations</b> times
	 */
	public static boolean SimulationRepeatEnabled = false;

	/**
	 * Number of times the simulation is repeated
	 */
	public static int SimulationIterations = 1;
}
