package upt.social.ui.graphics;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import upt.listeners.ICallableMouseEvent;
import upt.listeners.mouse.MousePressListener;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;

/**
 * Draws a custom sized chart area which is filled with the overall percentage
 * evolution in time. <br>
 * The draw rate is determined by calling the redraw method. <br>
 * When the chart area fills up to the right it resets.
 */
public class PercenatageChart extends JPanel implements IPercentageDisplay
{
	private static final long serialVersionUID = 1L;

	private int w, w0, h;
	private int nCharts;
	private JLabel[] currentPercents;
	private GradientPaint[] lineColors;
	private boolean showChart[];
	private Graphics2D g2;
	private Vector<Vector<Point>> points;
	private Vector<JLabel> messageLabels;
	private Line2D[] axis;

	private OptionMenu menu;

	private static final Stroke LineSize = new BasicStroke(2.0f);
	private static final Stroke ThinLineSize = new BasicStroke(0.5f);

	public PercenatageChart(double x, double y, double width, double height, String[] titles, GradientPaint[] lineColors)
	{
		w = (int) width;
		w0 = (int) width;
		h = (int) height;

		this.lineColors = lineColors;
		this.nCharts = lineColors.length;

		setLayout(null);
		setLocation((int) x, (int) y);
		setSize(w, h);
		// setBorder(LineBorder.createBlackLineBorder());

		points = new Vector<Vector<Point>>();
		for (int i = 0; i < nCharts; ++i)
		{
			points.add(new Vector<Point>());
		}
		messageLabels = new Vector<JLabel>();

		currentPercents = new JLabel[nCharts];
		for (int i = 0; i < nCharts; ++i)
		{
			currentPercents[i] = ButtonFactory.createLabel(this, "", ColorFactory.getItemBackcolor(),
				ColorFactory.getItemForecolor(), 10, i * 30 + 10, 50, 30);
		}

		showChart = new boolean[nCharts];
		for (int i = 0; i < nCharts; ++i)
		{
			showChart[i] = true;
		}
		toggleChart(4);
		menu = new OptionMenu(this, 0, 0, showChart, titles, lineColors);

		addListeners();
		createAxisSystem();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2 = (Graphics2D) g;

		// draw the axes
		g2.setColor(ColorFactory.getAxisColor());
		g2.setStroke(ThinLineSize);
		for (Line2D line: axis)
		{
			if (line != null)
			{
				g2.draw(line);
			}
		}

		// for each chart
		g2.setStroke(LineSize);
		for (int i = 0; i < nCharts; ++i)
		{
			if (showChart[i])
			{
				// set custom gradient color
				g2.setPaint(lineColors[i]);
				// draw whole graph
				for (int j = 0; j < points.get(i).size() - 1; ++j)
				{
					g2.drawLine(points.get(i).get(j).x, points.get(i).get(j).y, points.get(i).get(j + 1).x,
						points.get(i).get(j + 1).y);
				}
			}
		}

	}

	private void addListeners()
	{
		addMouseListener(new MousePressListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				menu.show(e.getPoint());
				System.out.println(" T = " + e.getX() * 100 / w0 + " intervals, P% =  " + (h - e.getY()) * 100 / h);
			}
		}));
	}

	private int k = 0;

	@Override
	public void redraw(double... percents)
	{
		if (g2 != null)
		{
			for (int i = 0; i < nCharts && i < percents.length; ++i)
			{
				currentPercents[i].setText(((int) (10000 * percents[i]) / 100.0) + "%");

				points.get(i).add(new Point(k * w0 / 100, (int) (h * (1 - percents[i]))));
				currentPercents[i].setLocation(Math.min(k * w0 / 100, w - 50),
					Math.max(Math.min((int) (h * (1 - percents[i])), h - 30), 0));
			}

			// extend graphics
			if (k > 0 && k % 100 == 0)
			{
				w += w0;
				setSize(w, h);
				validate();
			}

			createAxisSystem();
			repaint();
			k++;
		}
	}

	@Override
	public void reset()
	{
		k = 0;
		points.clear();
		for (int i = 0; i < nCharts; ++i)
		{
			points.add(new Vector<Point>());
		}
		for (JLabel label: currentPercents)
		{
			label.setText("");
		}
		for (JLabel label: messageLabels)
		{
			remove(label);
		}
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		this.w = width;
		this.h = height;
	}

	@Override
	public void setSize(Dimension d)
	{
		setSize(d.width, d.height);
	}

	/**
	 * Draws horizontal axes to make graphical reading easier.
	 */
	private synchronized void createAxisSystem()
	{
		axis = new Line2D[3];

		// top down creation
		for (int i = 0; i < axis.length; ++i)
		{
			axis[i] = new Line2D.Float(0, (i + 1) * h / (axis.length + 1), w * (1 + k / 100), (i + 1) * h
				/ (axis.length + 1));
		}
	}

	@Override
	public String getTabTitle()
	{
		return "Evolution";
	}

	@Override
	public void toggleChart(int index)
	{
		if (index >= 0 && index < nCharts)
		{
			showChart[index] = !showChart[index];
			currentPercents[index].setVisible(showChart[index]);
		}
	}

	@Override
	public void highlightMessage(String message, int chartIndex)
	{
		messageLabels.add(ButtonFactory.createLabel(this, message, ColorFactory.WHITE, ColorFactory.RED,
			currentPercents[chartIndex].getLocation().x, currentPercents[chartIndex].getLocation().y, 100, 25));
	}

	static class OptionMenu extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private static final int w = 250;
		private static final int h = 60;

		private JRadioButton[] radios;

		public OptionMenu(final IPercentageDisplay container, int x, int y, boolean[] showChart, String[] titles,
			GradientPaint[] lineColors)
		{
			setLayout(new GridLayout(3, 2, 2, 2));
			setLocation(x, y);
			setSize(w, h);

			setBackground(ColorFactory.IVORY);
			setBorder(LineBorder.createBlackLineBorder());
			((JPanel) container).add(this);
			setVisible(false);

			radios = new JRadioButton[lineColors.length];
			for (int i = 0; i < radios.length; ++i)
			{
				radios[i] = new JRadioButton(titles[i]);
				radios[i].setSelected(showChart[i]);
				radios[i].setBackground(lineColors[i].getColor1());
				add(radios[i]);

				// toggle the corresponding chart
				final int _i = i;
				radios[i].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						container.toggleChart(_i);
					}
				});
			}
		}

		public void show(Point p)
		{
			setLocation(p);
			setVisible(!isVisible());
		}
	}

}