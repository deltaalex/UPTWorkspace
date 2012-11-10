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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import upt.noc.simulation.Simulator;
import upt.ui.factory.ColorFactory;

public class NocMapPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JLabel nodes[][];
	private Line2D[] connections;
	private CurvedLine[] longConnections;
	private JLabel tipLabel;

	private static final int NodeSize = 40;
	private static final int NodeSpacing = 40;
	private static final Stroke LineSize = new BasicStroke(2.0f);
	private int sizeX, sizeY;
	int rows, cols;

	public NocMapPanel(final JLabel tipLabel, int x, int y)
	{
		super();
		this.tipLabel = tipLabel;

		rows = Simulator.NumRows;
		cols = Simulator.NumColumns;

		sizeX = cols * NodeSize + (cols + 1) * NodeSpacing;
		sizeY = rows * NodeSize + (rows + 1) * NodeSpacing;

		setLayout(null);
		setSize(sizeX, sizeY);
		setLocation(x, y);
		setBackground(ColorFactory.getItemBackcolor());
		setForeground(ColorFactory.getItemForecolor());
		// setBorder(new LineBorder(ColorFactory.DARK_ORANGE));

		// add nodes
		nodes = new JLabel[rows][cols];
		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < cols; ++j)
			{
				JLabel node = new JLabel("N " + (i * rows + j), SwingConstants.CENTER);
				node.setBackground(ColorFactory.getWindowBackcolor());
				node.setForeground(ColorFactory.getWindowForecolor());
				node.setBorder(LineBorder.createBlackLineBorder());
				node.setSize(NodeSize, NodeSize);
				node.setLocation(NodeSpacing * (j + 1) + NodeSize * j, NodeSpacing * (i + 1) + NodeSize * i);

				// node.addMouseListener(new NodeListener(node, null, "Node " +
				// (i *
				// rows + j), tipLabel));
				add(node);
				nodes[i][j] = node;
			}
		}

		// create connections
		connections = new Line2D[(rows - 1) * cols + rows * (cols - 1)];
		int c = 0;

		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < cols - 1; ++j)
			{
				Point pStart = new Point(NodeSpacing * (j + 1) + NodeSize * (j + 1), NodeSpacing * (i + 1) + NodeSize
					* i + NodeSize / 2);
				Point pEnd = new Point(NodeSpacing * (j + 2) + NodeSize * (j + 1), NodeSpacing * (i + 1) + NodeSize * i
					+ NodeSize / 2);

				connections[c] = new Line2D.Double(pStart, pEnd);
				/*addMouseMotionListener(connectionListeners[c] = new LineListener(this, connections[c], "Link "
					+ (j + i * rows) + " to " + (j + 1 + i * rows), tipLabel));*/

				c++;
			}
		}

		for (int i = 0; i < rows - 1; ++i)
		{
			for (int j = 0; j < cols; ++j)
			{
				Point pStart = new Point(NodeSpacing * (j + 1) + NodeSize * (j) + NodeSize / 2, NodeSpacing * (i + 1)
					+ NodeSize * (i + 1));
				Point pEnd = new Point(NodeSpacing * (j + 1) + NodeSize * (j) + NodeSize / 2, NodeSpacing * (i + 1)
					+ NodeSize * (i + 2));

				connections[c] = new Line2D.Double(pStart, pEnd);
				/*addMouseMotionListener(connectionListeners[c] = new LineListener(this, connections[c], "Link "
					+ (j + i * rows) + " to " + (j + (i + 1) * rows), tipLabel));*/
				c++;
			}
		}
	}

	public void setLongRangeLinks(Vector<Integer> links)
	{
		longConnections = new CurvedLine[links.size() / 2];
		for (int k = 0; k < links.size() - 1; k += 2)
		{
			int i1 = links.get(k) / cols;
			int j1 = links.get(k) % rows;

			int x1 = NodeSpacing * (j1 + 1) + NodeSize * j1 + NodeSize / 2;
			int y1 = NodeSpacing * (i1 + 1) + NodeSize * i1 + NodeSize / 2;

			int i2 = links.get(k + 1) / cols;
			int j2 = links.get(k + 1) % rows;

			int x2 = NodeSpacing * (j2 + 1) + NodeSize * j2 + NodeSize / 2;
			int y2 = NodeSpacing * (i2 + 1) + NodeSize * i2 + NodeSize / 2;

			longConnections[k / 2] = new CurvedLine(x1, y1, x2, y2, 1);

			nodes[i1][j1].addMouseListener(new NodeListener(nodes[i1][j1], nodes[i2][j2], "Node " + (i1 * rows + j1),
				tipLabel));
			nodes[i2][j2].addMouseListener(new NodeListener(nodes[i2][j2], nodes[i1][j1], "Node " + (i2 * rows + j2),
				tipLabel));
		}

		// add remaining listeners to nodes
		for (int i = 0; i < rows; ++i)
		{
			for (int j = 0; j < cols; ++j)
			{
				if (nodes[i][j].getListeners(NodeListener.class).length == 0)
				{
					nodes[i][j].addMouseListener(new NodeListener(nodes[i][j], null, "Node " + (i * rows + j), tipLabel));
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(LineSize);
		g2.setColor(ColorFactory.getLinkColor());

		for (int i = 0; i < connections.length; ++i)
		{
			g2.draw(connections[i]);
		}

		g2.setColor(ColorFactory.getLongLinkColor());
		for (int i = 0; i < longConnections.length; ++i)
		{
			longConnections[i].draw(g2);
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

	class NodeListener extends MouseAdapter
	{
		private JLabel node, node2;
		private String tooltip;
		private JLabel tipLabel;

		NodeListener(JLabel node, JLabel linkedNode, String tooltip, JLabel tipLabel)
		{
			this.node = node;
			this.node2 = linkedNode;
			this.tooltip = tooltip;
			this.tipLabel = tipLabel;
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			tipLabel.setText(tooltip);
			node.setForeground(ColorFactory.RED);
			if (node2 != null)
			{
				node2.setForeground(ColorFactory.RED);
			}
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			tipLabel.setText("");
			node.setForeground(ColorFactory.getWindowForecolor());
			if (node2 != null)
			{
				node2.setForeground(ColorFactory.getWindowForecolor());
			}
		}
	}

}
