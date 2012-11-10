/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 4:52:36 PM </copyright>
 */
package noc.ui.scroll;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;

import upt.ui.factory.ColorFactory;

/**
 * Creates a panel with one button that synchronizes it's drag movement on the
 * X/Y axis with a specified window panel.
 */
public abstract class MyScrollBar extends JPanel
{
	protected static final long serialVersionUID = 1L;
	public static final int DragRatio = 5;
	public static final int ScollBarThickness = 20;

	protected JPanel window;
	protected JButton scroll;
	protected String iconPath;
	protected int width, height;

	public MyScrollBar(JPanel window, String iconPath, int x, int y, int w, int h)
	{
		this.window = window;
		this.iconPath = iconPath;

		width = w;
		height = h;
		setLayout(null);
		setLocation(x, y);
		setSize(w, h);
		setBackground(ColorFactory.IVORY);
		setForeground(ColorFactory.getItemForecolor());
	}

	public void initialize()
	{
		createScrollButton();

		addListeners();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (location != null)
		{
			scroll.setLocation(location);
		}
	}

	protected Point location;
	protected Point point0, point1;
	protected int delta, windowX, windowY;

	protected abstract void addListeners();

	protected abstract void createScrollButton();
}
