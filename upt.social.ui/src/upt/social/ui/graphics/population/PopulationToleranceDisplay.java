/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 20, 2011 6:49:46 PM </copyright>
 */
package upt.social.ui.graphics.population;

import java.awt.Color;
import java.util.Vector;

import upt.social.model.INode;
import upt.social.model.NODETYPE;
import upt.social.model.analog.IAnalogNode;
import upt.social.model.complex.IComplexNode;

/**
 * Draws a custom sized chart area which is filled dots representing the
 * <b>tolerance</b> level of all individuals. <br>
 * The network is drawn in a mesh structure to evenly fit all nodes. <br>
 * The refresh rate is determined by calling the redraw method. <br>
 */
public class PopulationToleranceDisplay extends PopulationDisplay
{
	private static final long serialVersionUID = 1L;

	private Color cTolerant, cIntolerant, cSpecial;

	public PopulationToleranceDisplay(int N, double x, double y, double width, double height, Color colorTolerant,
		Color colorIntolerant, Color colorSpecial)
	{
		super(N, x, y, width, height, colorTolerant, colorSpecial, colorIntolerant, colorSpecial, colorTolerant);

		cTolerant = colorTolerant;
		cIntolerant = colorIntolerant;
		cSpecial = colorSpecial;

	}

	@Override
	public void redraw(Vector<INode> nodes)
	{
		colors.clear();

		if (!(nodes.firstElement() instanceof IComplexNode) && !(nodes.firstElement() instanceof IAnalogNode))
		{
			lError.setVisible(true);
			repaint();
			return;
		}

		// set color for each node : by state and by type (stubborn?)
		for (INode node: nodes)
		{
			if (node.getNodeType().equals(NODETYPE.NORMAL))
			{
				double tolerance = node.getTolerance().getTolerance();

				int r = Math.min(cTolerant.getRed(), cIntolerant.getRed())
					+ (int) (Math.abs(cTolerant.getRed() - cIntolerant.getRed()) * tolerance);
				int g = Math.min(cTolerant.getGreen(), cIntolerant.getGreen())
					+ (int) (Math.abs(cTolerant.getGreen() - cIntolerant.getGreen()) * tolerance);
				int b = Math.min(cTolerant.getBlue(), cIntolerant.getBlue())
					+ (int) (Math.abs(cTolerant.getBlue() - cIntolerant.getBlue()) * tolerance);

				colors.add(new Color(r, g, b));
			}
			else if (node.getNodeType().equals(NODETYPE.STUBBORN))
			{
				colors.add(cSpecial);
			}
			// ...

		}

		repaint();
	}

	@Override
	public String getTabTitle()
	{
		return "Tolerance";
	}
}