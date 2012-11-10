/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 5:07:14 PM </copyright>
 */
package noc.ui.scroll;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import upt.listeners.ICallableMouseEvent;
import upt.listeners.mouse.MouseDragListener;
import upt.listeners.mouse.MousePressListener;
import upt.ui.factory.ButtonFactory;

public class MyScrollBarX extends MyScrollBar
{
	private static final long serialVersionUID = 1L;

	public MyScrollBarX(JPanel window, String iconPath, int x, int y, int width)
	{
		super(window, iconPath, x, y, width, ScollBarThickness);
	}

	@Override
	protected void createScrollButton()
	{
		scroll = ButtonFactory.createButton(this, "Scroll", iconPath, 0, 0, 100, ScollBarThickness);
		point0 = scroll.getLocation();
	}

	@Override
	protected void addListeners()
	{
		scroll.addMouseListener(new MousePressListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				if (point0 != null)
				{
					point0 = e.getPoint();
				}
				if (location == null)
				{
					location = scroll.getLocation();
				}
			}
		}));

		scroll.addMouseMotionListener(new MouseDragListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				point1 = e.getPoint();
				delta = point1.x - point0.x;

				// avoids min-max usage :: Math.min(width - 100, Math.max(0,
				// location.x + delta)
				int x = location.x + delta > 0 ? (location.x + delta < width - 100 ? location.x + delta : width - 100)
					: 0;

				location.setLocation(x, 0);
				scroll.setLocation(location.x, location.y);

				point0.setLocation(point1.x, 0);
				// compute the windows x coordinate proportionally to the scroll
				// button location
				windowX = (window.getSize().width - width) * location.x / (width - 100);
				window.setLocation(-windowX, window.getLocation().y);
				// window.repaint();
			}
		}));
	}
}
