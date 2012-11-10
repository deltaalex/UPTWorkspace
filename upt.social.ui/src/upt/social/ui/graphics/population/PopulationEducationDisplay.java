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
import upt.social.model.complex.IComplexNode;

/**
 * Draws a custom sized chart area which is filled dots representing the
 * <b>education</b> level of all individuals. <br>
 * The network is drawn in a mesh structure to evenly fit all nodes. <br>
 * The refresh rate is determined by calling the redraw method. <br>
 */
public class PopulationEducationDisplay extends PopulationDisplay
{
	private static final long serialVersionUID = 1L;

	private Color cStupid, cSmart, cSpecial;

	public PopulationEducationDisplay(int N, double x, double y, double width, double height, Color colorStupid,
		Color colorSmart, Color colorSpecial)
	{
		super(N, x, y, width, height, colorStupid, colorSpecial, colorSmart, colorSpecial, colorStupid);

		cStupid = colorStupid;
		cSmart = colorSmart;
		cSpecial = colorSpecial;

	}

	@Override
	public void redraw(Vector<INode> nodes)
	{
		colors.clear();

		if (!(nodes.firstElement() instanceof IComplexNode))
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
				double education = ((IComplexNode) node).getEducationAsPercent();
				int r = Math.min(cStupid.getRed(), cSmart.getRed())
					+ (int) (Math.abs(cStupid.getRed() - cSmart.getRed()) * education);
				int g = Math.min(cStupid.getGreen(), cSmart.getGreen())
					+ (int) (Math.abs(cStupid.getGreen() - cSmart.getGreen()) * education);
				int b = Math.min(cStupid.getBlue(), cSmart.getBlue())
					+ (int) (Math.abs(cStupid.getBlue() - cSmart.getBlue()) * education);

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
		return "Education";
	}
}