package upt.social.ui.graphics.population.ring;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import upt.social.ui.graphics.population.PopulationDisplay;

/**
 * Draws a custom sized chart area which is filled dots representing all
 * individuals. <br>
 * The network is drawn in a mesh structure to evenly fit all nodes. <br>
 * The refresh rate is determined by calling the redraw method. <br>
 */
public class PopulationRingDisplay extends PopulationDisplay
{
	private static final long serialVersionUID = 1L;

	public PopulationRingDisplay(int N, double x, double y, double width, double height, Color colorYes,
		Color colorYesHighlight, Color colorNo, Color colorNoHighlight, Color colorDefault)
	{
		super(N, x, y, width, height, colorYes, colorYesHighlight, colorNo, colorNoHighlight, colorDefault);
	}

	/**
	 * Initializes the N points on the circular plot area - lot's of maths ...
	 */
	@Override
	protected void plotPoints(int N)
	{
		points = new Vector<Point>();
		int dx = 20, dy = 20;
		int plotW = w - 2 * dx;
		int plotH = h - 2 * dy;
		double PI = Math.PI;
		int sx = Math.max(1, plotW / (N / 2));

		for (int i = 0; i < N; ++i)
		{
			int px = (int) ((plotW / 2) * Math.cos(2 * PI * i / N));
			int py = (int) ((plotH / 2) * Math.sin(2 * PI * i / N));
			points.add(new Point(dx + plotW / 2 + px, dy + plotH / 2 + py));
		}

		stroke = new BasicStroke(Math.max(1f, sx / 1.5f));
		specialStroke = new BasicStroke(Math.max(1.5f, 3.0f * sx / 4));
	}
}