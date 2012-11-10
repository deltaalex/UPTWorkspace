package upt.social.ui.graphics.population;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import upt.social.model.INode;
import upt.social.model.NODETYPE;
import upt.social.model.STATE;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;

/**
 * Draws a custom sized chart area which is filled dots representing all
 * individuals. <br>
 * The network is drawn in a mesh structure to evenly fit all nodes. <br>
 * The refresh rate is determined by calling the redraw method. <br>
 */
public class PopulationDisplay extends JPanel implements IPopulationDisplay
{
	private static final long serialVersionUID = 1L;

	protected int w, h;
	protected Graphics2D g2;
	protected Vector<Point> points;
	protected Vector<Color> colors;
	protected JLabel lError;

	private Color cYes, cNo, cYesS, cNoS, cDefault;
	protected Stroke stroke, specialStroke;

	public PopulationDisplay(int N, double x, double y, double width, double height, Color colorYes,
		Color colorYesHighlight, Color colorNo, Color colorNoHighlight, Color colorDefault)
	{
		this.w = (int) width;
		this.h = (int) height;
		cYes = colorYes;
		cYesS = colorYesHighlight;
		cNo = colorNo;
		cNoS = colorNoHighlight;
		cDefault = colorDefault;

		setLayout(null);
		setLocation((int) x, (int) y);
		setSize(w, h);
		setBorder(LineBorder.createBlackLineBorder());

		lError = ButtonFactory.createLabel(this, "Not available for this model", this.w / 2 - 100, this.h / 2, 200, 30);
		lError.setForeground(ColorFactory.RED);
		lError.setVisible(false);

		colors = new Vector<Color>();

		plotPoints(N);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		Stroke aux = g2.getStroke();
		g2.setStroke(stroke);

		for (int i = 0; i < points.size() && i < colors.size(); ++i)
		{
			if (colors.get(i).equals(cYesS) || colors.get(i).equals(cNoS))
			{
				g2.setStroke(specialStroke);
			}
			else
			{
				g2.setStroke(stroke);
			}

			g2.setColor(colors.get(i));
			g2.drawLine(points.get(i).x, points.get(i).y, points.get(i).x, points.get(i).y);
		}
		g2.setStroke(aux);
	}

	@Override
	public void redraw(Vector<INode> nodes)
	{
		colors.clear();

		// set color for each node : by state and by type (stubborn?)
		for (INode node: nodes)
		{
			if (node.getState().equals(STATE.YES))
			{
				// paint with default color (green)
				if (node.getNodeType().equals(NODETYPE.NORMAL))
				{
					colors.add(cYes);
				}
				// highlight color (light green ?)
				else
				{
					colors.add(cYesS);
				}
			}
			else if (node.getState().equals(STATE.NO))
			{
				// paint with default color (red)
				if (node.getNodeType().equals(NODETYPE.NORMAL))
				{
					colors.add(cNo);
				}
				// highlight color (light red ?)
				else
				{
					colors.add(cNoS);
				}
			}
			else
			{
				colors.add(cDefault);
			}
		}

		repaint();
	}

	/**
	 * Initializes the N points on plot area - lot's of maths
	 */
	protected void plotPoints(int N)
	{
		points = new Vector<Point>();
		int sqrt = (int) Math.sqrt(N);
		int rest = N - sqrt * sqrt;
		int dx = 20, dy = 20;
		int plotW = w - 2 * dx;
		int plotH = h - 2 * dy;
		int sx = Math.max(1, plotW / (sqrt - 1));
		int sy = Math.max(1, plotH / (sqrt + (rest == 0 ? 0 : 1) - 1));
		for (int i = 0; i < N; ++i)
		{
			points.add(new Point(dx + (sx * (i % sqrt)), dy + (sy * (i / sqrt))));
		}

		stroke = new BasicStroke(Math.max(1, sx / 2));
		specialStroke = new BasicStroke(Math.max(1.5f, 3.0f * sx / 4));
	}

	@Override
	public String getTabTitle()
	{
		return "Network";
	}

	@Override
	public void reset()
	{
		lError.setVisible(false);
		colors.clear();
	}
}