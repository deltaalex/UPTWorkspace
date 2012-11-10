/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 8, 2011 12:00:15 PM </copyright>
 */
package noc.ui.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import noc.internal.Simulator;

public class NocMapPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final int DragRatio = 5;

	private NodeListener[] nodeListeners;
	private LineListener[] connectionListeners;
	private Line2D[] connections;
	private Point location, dragPoint;

	private static final int NodeSize = 40;
	private static final int NodeSpacing = 40;
	private static final Stroke LineSize = new BasicStroke(2.0f);
	private static int width, height;

	public NocMapPanel(JLabel tipLabel, int x, int y, final int viewWidth, final int viewHeight)
	{
		super();

		int m = Simulator.NumRows;
		int n = Simulator.NumColumns;

		width = n * NodeSize + (n + 1) * NodeSpacing;
		height = m * NodeSize + (m + 1) * NodeSpacing;

		setLayout(null);
		setSize(width, height);
		getSize();
		setLocation(x, y);
		setBackground(ColorFactory.getItemBackcolor());
		setForeground(ColorFactory.getItemForecolor());
		setBorder(new LineBorder(Color.RED));
		location = new Point(-1, -1);
		dragPoint = new Point(-1, -1);

		// add nodes
		nodeListeners = new NodeListener[m * n];
		for (int i = 0; i < m; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				JLabel node = new JLabel("N " + (i * m + j + 1), SwingConstants.CENTER);
				node.setBackground(ColorFactory.getWindowBackcolor());
				node.setForeground(ColorFactory.getWindowForecolor());
				node.setBorder(LineBorder.createBlackLineBorder());
				node.setSize(NodeSize, NodeSize);
				node.setLocation(NodeSpacing * (j + 1) + NodeSize * j, NodeSpacing * (i + 1) + NodeSize * i);
				/*addMouseMotionListener(nodeListeners[i * m + j] = new NodeListener(this, node, "Node "
					+ (i * m + j + 1), tipLabel));*/
				add(node);
			}
		}

		// create connections
		connectionListeners = new LineListener[(m - 1) * n + m * (n - 1)];
		connections = new Line2D[(m - 1) * n + m * (n - 1)];
		int c = 0;

		for (int i = 0; i < m; ++i)
		{
			for (int j = 0; j < n - 1; ++j)
			{
				Point pStart = new Point(NodeSpacing * (j + 1) + NodeSize * (j + 1), NodeSpacing * (i + 1) + NodeSize
					* i + NodeSize / 2);
				Point pEnd = new Point(NodeSpacing * (j + 2) + NodeSize * (j + 1), NodeSpacing * (i + 1) + NodeSize * i
					+ NodeSize / 2);

				connections[c] = new Line2D.Double(pStart, pEnd);
				/*addMouseMotionListener(connectionListeners[c] = new LineListener(this, connections[c], "Link "
					+ (j + 1 + i * m) + " to " + (j + 2 + i * m), tipLabel));*/

				c++;
			}
		}

		for (int i = 0; i < m - 1; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				Point pStart = new Point(NodeSpacing * (j + 1) + NodeSize * (j) + NodeSize / 2, NodeSpacing * (i + 1)
					+ NodeSize * (i + 1));
				Point pEnd = new Point(NodeSpacing * (j + 1) + NodeSize * (j) + NodeSize / 2, NodeSpacing * (i + 1)
					+ NodeSize * (i + 2));

				connections[c] = new Line2D.Double(pStart, pEnd);
				/*addMouseMotionListener(connectionListeners[c] = new LineListener(this, connections[c], "Link "
					+ (j + 1 + i * m) + " to " + (j + 1 + (i + 1) * m), tipLabel));*/
				c++;
			}
		}

		// drag listener
		addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseMoved(MouseEvent e)
			{/**/
			}

			public void mouseDragged(MouseEvent e)
			{
				Point p = e.getPoint();
				int dx = 0, dy = 0;
				int x = dragPoint.x;
				int y = dragPoint.y;

				if (location.x <= 0 && (location.x + width) >= viewWidth)
				{
					if (p.x < x)
					{
						dx = -1;
					}
					else if (p.x > x)
					{
						dx = +1;
					}
				}
				if (location.y <= 0 && (location.y + height) >= viewHeight)
				{
					if (p.y < y)
					{
						dy = -1;
					}
					else if (p.y > y)
					{
						dy = +1;
					}
				}
				location = new Point(Math.max(Math.min(0, location.x + dx * DragRatio), viewWidth - width), Math.max(
					Math.min(0, location.y + dy * DragRatio), viewHeight - height));
				setLocation(location);
				dragPoint = p;
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(LineSize);
		g2.setColor(ColorFactory.getLineColor());

		for (int i = 0; i < connections.length; ++i)
		{
			g2.draw(connections[i]);
		}

		if (getLocation().x != location.x || getLocation().y != location.y)
		{
			// if (getLocation().x != 0 && getLocation().y != 0)
			{
				setLocation(location);
			}
		}
	}

	class LineListener extends MouseMotionAdapter
	{
		private Line2D line = null;
		private JComponent parent = null;
		private String tooltip;
		private JLabel tipLabel;

		LineListener(JComponent parent, Line2D line, String tooltip, JLabel tipLabel)
		{
			this.parent = parent;
			this.line = line;
			this.tooltip = tooltip;
			this.tipLabel = tipLabel;
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			Point p = e.getPoint();
			if (line.ptSegDist(p) <= 1.0)
			{
				tipLabel.setText(tooltip);
			}
			parent.repaint();
		}
	}

	class NodeListener extends MouseMotionAdapter
	{
		private JLabel node;
		private JComponent parent;
		private String tooltip;
		private JLabel tipLabel;

		NodeListener(JComponent parent, JLabel node, String tooltip, JLabel tipLabel)
		{
			this.parent = parent;
			this.node = node;
			this.tooltip = tooltip;
			this.tipLabel = tipLabel;
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			Point p = e.getPoint();
			Point l = node.getLocation();
			Dimension d = node.getSize();

			if (l.x <= p.x && l.y <= p.y && l.x + d.width >= p.x && l.y + d.height >= p.y)
			{
				tipLabel.setText(tooltip);
			}
			parent.repaint();
		}
	}

}
