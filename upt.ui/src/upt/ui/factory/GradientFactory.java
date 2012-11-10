/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Sep 27, 2011 4:34:13 PM </copyright>
 */
package upt.ui.factory;

import java.awt.Color;
import java.awt.GradientPaint;

/**
 * Creates gradient brushes usable for drawing chart lines etc.
 */
public class GradientFactory
{
	/**
	 * Creates a gradient color transforming color c1 into c2 from (x1, y1) to
	 * (x2,y2)
	 */
	public static GradientPaint create(double x1, double y1, double x2, double y2, Color c1, Color c2)
	{
		return new GradientPaint((float) x1, (float) y1, c1, (float) x2, (float) y2, c2);
	}

	/**
	 * Creates a gradient along one axis transforming color c1 into c2.
	 */
	public static GradientPaint createLinear(Color c1, Color c2, double dx, double dy)
	{
		return new GradientPaint(0, 0, c1, (float) dx, (float) dy, c2);
	}
}
