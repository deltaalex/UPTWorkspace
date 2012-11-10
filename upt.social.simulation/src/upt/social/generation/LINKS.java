/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 11, 2011 11:37:22 AM </copyright>
 */
package upt.social.generation;

/**
 * Specifies link types in a mesh network <br>
 * <b> Keep id-s unique !</b> <br>
 * H = horizontal only <br>
 * V = vertical only <br>
 * DP = diagonals along the primary direction only (NW-SE) <br>
 * DS = diagonals along the secondary direction only (NE-SW) <br>
 * any combination : e.g. HV, HVDPDS ...
 */
public class LINKS
{
	/**
	 * H = horizontal only
	 */
	public static final String H = "H";
	/**
	 * V = vertical only
	 */
	public static final String V = "V";
	/**
	 * DP = diagonals along the primary direction only (NW-SE)
	 */
	public static final String DP = "DP";
	/**
	 * DS = diagonals along the secondary direction only (NE-SW)
	 */
	public static final String DS = "DS";

	public static boolean isHorizontal(String links)
	{
		return links.contains(H);
	}

	public static boolean isVertical(String links)
	{
		return links.contains(V);
	}

	public static boolean isPrimaryDiagonal(String links)
	{
		return links.contains(DP);
	}

	public static boolean isSecondaryDiagonal(String links)
	{
		return links.contains(DS);
	}
}
