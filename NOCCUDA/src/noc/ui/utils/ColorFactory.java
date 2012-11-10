/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:48:36 PM </copyright>
 */
package noc.ui.utils;

import java.awt.Color;

public class ColorFactory
{
	public static final Color WHITE = Color.WHITE;
	public static final Color BLACK = Color.BLACK;
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
	public static final Color IVORY = new Color(255, 255, 206);
	public static final Color DARK_BROWN = new Color(64, 0, 0);
	public static final Color DARK_ORANGE = new Color(255, 137, 29);

	public static Color getColorRGB(int r, int g, int b)
	{
		return new Color(r, g, b);
	}

	public static Color getWindowBackcolor()
	{
		return WHITE;
	}

	public static Color getWindowForecolor()
	{
		return BLACK;
	}

	public static Color getItemBackcolor()
	{
		return IVORY;
	}

	public static Color getItemForecolor()
	{
		return DARK_BROWN;
	}

	public static Color getLineColor()
	{
		return DARK_BROWN;
	}
}
