/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:48:36 PM </copyright>
 */
package upt.ui.factory;

import java.awt.Color;

public class ColorFactory
{
	public static final Color WHITE = Color.WHITE;
	public static final Color TRANSPARENT_WHITE = new Color(0, 0, 0, 0);
	public static final Color BLACK = Color.BLACK;
	public static final Color TRANSPARENT_BLACK = new Color(255, 255, 255, 0);
	public static final Color RED = Color.RED;
	public static final Color BLUE = Color.BLUE;
	public static final Color GREEN = Color.GREEN;
	public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
	public static final Color IVORY = new Color(255, 255, 236);
	public static final Color DARK_BROWN = new Color(64, 0, 0);
	public static final Color DARK_ORANGE = new Color(255, 128, 64);
	public static final Color DARK_RED = new Color(150, 0, 0);
	public static final Color DARK_GREEN = new Color(0, 150, 0);
	public static final Color LIGHT_GREEN = new Color(128, 255, 128);
	public static final Color LIGHT_RED = new Color(255, 128, 128);
	public static final Color LIGHT_BLUE = new Color(128, 255, 255);
	public static final Color SKY_BLUE = new Color(0, 128, 255);

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
		return WHITE;
	}

	public static Color getItemForecolor()
	{
		return DARK_BROWN;
	}

	public static Color getLinkColor()
	{
		return DARK_BROWN;
	}

	public static Color getLongLinkColor()
	{
		return DARK_ORANGE;
	}

	public static Color getAxisColor()
	{
		return LIGHT_GRAY;
	}
}
