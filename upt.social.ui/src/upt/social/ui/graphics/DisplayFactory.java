/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Sep 26, 2011 8:06:49 PM </copyright>
 */
package upt.social.ui.graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.GradientPaint;

import upt.social.state.SimConfig;
import upt.social.state.TOPOLOGY;
import upt.social.ui.graphics.population.PopulationAgeDisplay;
import upt.social.ui.graphics.population.PopulationDisplay;
import upt.social.ui.graphics.population.PopulationEducationDisplay;
import upt.social.ui.graphics.population.PopulationToleranceDisplay;
import upt.social.ui.graphics.population.ring.PopulationRingDisplay;
import upt.ui.factory.ColorFactory;
import upt.ui.widgets.ScrolledView;

public class DisplayFactory
{
	public static PercenatageBar createPercentageBar(Container container, Color leftColor, Color rightColor, double x,
		double y, double w, double h, double initialPercentage)
	{
		PercenatageBar bar;

		bar = new PercenatageBar(leftColor, rightColor, x, y, w, h, initialPercentage);
		bar.setBackground(ColorFactory.getItemBackcolor());
		bar.setForeground(ColorFactory.getItemForecolor());

		bar.setSize((int) w, (int) h);
		bar.setLocation((int) x, (int) y);

		container.add(bar);
		bar.setVisible(true);
		return bar;
	}

	public static PercenatageChart createPercentageChart(Container container, double x, double y, double w, double h,
		Object... titleColorArray)
	{
		try
		{
			PercenatageChart chart;

			GradientPaint[] colors = new GradientPaint[titleColorArray.length / 2];
			String[] titles = new String[titleColorArray.length / 2];

			for (int i = 0; i < colors.length; ++i)
			{
				titles[i] = (String) titleColorArray[2 * i];
				colors[i] = (GradientPaint) titleColorArray[2 * i + 1];
			}

			chart = new PercenatageChart(x, y, w - 2, h - 2, titles, colors);
			chart.setBackground(ColorFactory.getItemBackcolor());
			chart.setForeground(ColorFactory.getItemForecolor());

			ScrolledView scroller;
			scroller = new ScrolledView(chart, x, y, w, h, ScrolledView.SCROLL.HORIZONTAL);
			container.add(scroller);
			chart.setVisible(false);
			return chart;

		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(
				"Parameter 'titleColorArray' if incorrect: needs text+gradientpaint pairs.");
		}
	}

	public static PopulationDisplay createPopulationDisplay(Container container, int N, double x, double y, double w,
		double h, Color colorYes, Color colorYesHighlight, Color colorNo, Color colorNoHighlight, Color cDefault)
	{
		PopulationDisplay plot;

		if (SimConfig.NetworkModel.equals(TOPOLOGY.RING))
		{
			plot = new PopulationRingDisplay(N, x, y, w, h, colorYes, colorYesHighlight, colorNo, colorNoHighlight,
				cDefault);
		}
		else
		{
			plot = new PopulationDisplay(N, x, y, w, h, colorYes, colorYesHighlight, colorNo, colorNoHighlight,
				cDefault);
		}
		plot.setBackground(ColorFactory.getItemBackcolor());
		plot.setForeground(ColorFactory.getItemForecolor());

		plot.setSize((int) w, (int) h);
		plot.setLocation((int) x, (int) y);

		container.add(plot);
		plot.setVisible(false);
		return plot;
	}

	public static PopulationToleranceDisplay createPopulationToleranceDisplay(Container container, int N, double x,
		double y, double w, double h, Color colorTolerant, Color colorIntolerant, Color colorSpecial)
	{
		PopulationToleranceDisplay plot;

		plot = new PopulationToleranceDisplay(N, x, y, w, h, colorTolerant, colorIntolerant, colorSpecial);
		plot.setBackground(ColorFactory.getItemBackcolor());
		plot.setForeground(ColorFactory.getItemForecolor());

		plot.setSize((int) w, (int) h);
		plot.setLocation((int) x, (int) y);

		container.add(plot);
		plot.setVisible(false);
		return plot;
	}

	public static PopulationAgeDisplay createPopulationAgeDisplay(Container container, int N, double x, double y,
		double w, double h, Color colorYoung, Color colorOld, Color colorSpecial)
	{
		PopulationAgeDisplay plot;

		plot = new PopulationAgeDisplay(N, x, y, w, h, colorYoung, colorOld, colorSpecial);
		plot.setBackground(ColorFactory.getItemBackcolor());
		plot.setForeground(ColorFactory.getItemForecolor());

		plot.setSize((int) w, (int) h);
		plot.setLocation((int) x, (int) y);

		container.add(plot);
		plot.setVisible(false);
		return plot;
	}

	public static PopulationEducationDisplay createPopulationEducationDisplay(Container container, int N, double x,
		double y, double w, double h, Color colorYoung, Color colorOld, Color colorSpecial)
	{
		PopulationEducationDisplay plot;

		plot = new PopulationEducationDisplay(N, x, y, w, h, colorYoung, colorOld, colorSpecial);
		plot.setBackground(ColorFactory.getItemBackcolor());
		plot.setForeground(ColorFactory.getItemForecolor());

		plot.setSize((int) w, (int) h);
		plot.setLocation((int) x, (int) y);

		container.add(plot);
		plot.setVisible(false);
		return plot;
	}
}
