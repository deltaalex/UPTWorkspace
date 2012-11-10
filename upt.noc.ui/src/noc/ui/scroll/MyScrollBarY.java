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

public class MyScrollBarY extends MyScrollBar
{
	private static final long serialVersionUID = 1L;

	public MyScrollBarY(JPanel window, String iconPath, int x, int y, int height)
	{
		super(window, iconPath, x, y, ScollBarThickness, height);
	}

	@Override
	protected void createScrollButton()
	{
		scroll = ButtonFactory.createButton(this, "Scroll", iconPath, 0, 0, ScollBarThickness, 100);
		point0 = scroll.getLocation();
	}

	@Override
	protected void addListeners()
	{
		scroll.addMouseListener(new MousePressListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				point0 = e.getPoint();
				location = scroll.getLocation();
			}
		}));

		scroll.addMouseMotionListener(new MouseDragListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				point1 = e.getPoint();
				delta = point1.y - point0.y;

				// avoids min-max usage :: Math.min(height - 100, Math.max(0,
				// location.y + delta)
				int y = location.y + delta > 0 ? (location.y + delta < height - 100 ? location.y + delta : height - 100)
					: 0;

				location.setLocation(0, y);
				scroll.setLocation(location.x, location.y);

				point0.setLocation(0, point1.y);
				// compute the windows y coordinate proportionally to the scroll
				// button location
				windowY = (window.getSize().height - height) * location.y / (height - 100);
				window.setLocation(window.getLocation().x, -windowY);
				// window.repaint();
			}
		}));
	}
}
