/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 30, 2011 1:41:40 PM </copyright>
 */
package upt.social.model.helpers;

import java.util.Vector;

/**
 * Provides various mathematical functions
 * 
 */
public class MathHelper
{
	/**
	 * Compute the kurtosis of a data set. Vector needs at least 4 values. <br>
	 * <a href="http://en.wikipedia.org/wiki/Kurtosis">Wikipedia (Theory)</a> <br>
	 * <a href="http://www.ats.ucla.edu/stat/mult_pkg/faq/general/kurtosis.htm"
	 * >How to compute</a> - uses 3rd formula <br>
	 */
	public static double kurtosis(Vector<Double> values)
	{
		if (values.size() <= 3)
		{
			return 0;
		}

		double kurtosis = 0.0;

		double v = 0.0;
		int N = values.size();

		for (double d: values)
		{
			v += d;
		}

		double averageX = 1.0 * v / N;
		double s2 = 0.0, s4 = 0.0;

		for (double d: values)
		{
			s2 += (d - averageX) * (d - averageX);
			s4 += (d - averageX) * (d - averageX) * (d - averageX) * (d - averageX);
		}

		// double m2 = 1.0 * s2 / N;
		// double m4 = 1.0 * s4 / N;
		double Vx = 1.0 * s2 / (N - 1);

		kurtosis = 1.0 * N * (N + 1) * s4 / ((N - 1) * (N - 2) * (N - 3) * (Vx * Vx)) - 3.0 * (N - 1) * (N - 1)
			/ ((N - 2) * (N - 3));

		return kurtosis;
	}
}
