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
 * <b>age</b> of all individuals. <br>
 * The network is drawn in a mesh structure to evenly fit all nodes. <br>
 * The refresh rate is determined by calling the redraw method. <br>
 */
public class PopulationAgeDisplay extends PopulationDisplay
{
	private static final long serialVersionUID = 1L;

	private Color cYoung, cOld, cSpecial;

	public PopulationAgeDisplay(int N, double x, double y, double width, double height, Color colorYoung,
		Color colorOld, Color colorSpecial)
	{
		super(N, x, y, width, height, colorYoung, colorSpecial, colorOld, colorSpecial, colorYoung);

		cYoung = colorYoung;
		cOld = colorOld;
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
				double age = ((IComplexNode) node).getAgeAsPercent();
				int r = Math.min(cYoung.getRed(), cOld.getRed())
					+ (int) (Math.abs(cYoung.getRed() - cOld.getRed()) * age);
				int g = Math.min(cYoung.getGreen(), cOld.getGreen())
					+ (int) (Math.abs(cYoung.getGreen() - cOld.getGreen()) * age);
				int b = Math.min(cYoung.getBlue(), cOld.getBlue())
					+ (int) (Math.abs(cYoung.getBlue() - cOld.getBlue()) * age);

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
		return "Age";
	}

}