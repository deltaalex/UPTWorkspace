/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 8, 2011 11:04:38 AM </copyright>
 */
package noc.ui.utils;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import noc.ui.scroll.MyScrollBar;
import upt.listeners.ICallable;
import upt.listeners.mouse.MouseDoubleClickListener;
import upt.ui.factory.ColorFactory;

public class MapFactory
{
	private NocMapPanel map;
	private Scrollbar scrollX, scrollY;

	private JPanel scrollPanel, mapPanel;

	public NocMapPanel createScrolledNocMap(Container container, JLabel tipLabel, double x, double y, double w, double h)
	{
		// the mapPanel is 20 pixel small than the whole size of the map panel

		mapPanel = new JPanel();
		mapPanel.setLayout(null);
		mapPanel.setSize((int) (w - MyScrollBar.ScollBarThickness - 2), (int) (h - MyScrollBar.ScollBarThickness - 2));
		mapPanel.setLocation(2, 2);
		mapPanel.setBackground(ColorFactory.getItemBackcolor());
		mapPanel.setForeground(ColorFactory.getItemForecolor());
		// mapPanel.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 2,
		// false));

		scrollPanel = new JPanel();
		scrollPanel.setLayout(null);
		scrollPanel.setSize((int) w + 2, (int) h + 2);
		scrollPanel.setLocation((int) x, (int) y);
		scrollPanel.setBackground(ColorFactory.getItemBackcolor());
		scrollPanel.setForeground(ColorFactory.getItemForecolor());
		scrollPanel.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 2, false));

		map = new NocMapPanel(tipLabel, 2, 2);

		scrollX = new Scrollbar(Scrollbar.HORIZONTAL, 0, 20, 0, 100);
		scrollX.setLocation(2, (int) (h - MyScrollBar.ScollBarThickness));
		scrollX.setSize((int) (w - MyScrollBar.ScollBarThickness), 20);
		scrollX.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				Scrollbar sb = (Scrollbar) e.getSource();
				double dx = 1.0 * sb.getValue() / sb.getMaximum();
				int windowX = (int) (1.0 * map.getSize().width * dx);
				map.setLocation(-windowX, map.getLocation().y);
			}
		});
		map.add(scrollX);

		scrollY = new Scrollbar(Scrollbar.VERTICAL, 0, 20, 0, 100);
		scrollY.setLocation((int) (w - MyScrollBar.ScollBarThickness), 2);
		scrollY.setSize(20, (int) (h - MyScrollBar.ScollBarThickness));
		scrollY.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				Scrollbar sb = (Scrollbar) e.getSource();
				double dy = 1.0 * sb.getValue() / sb.getMaximum();
				int windowY = (int) (1.0 * map.getSize().height * dy);
				map.setLocation(map.getLocation().x, -windowY);
			}
		});
		map.add(scrollY);
		// scrollX = new MyScrollBarX(map, UIConfig.ICON_SCROLL_X, 2, (int) (h -
		// MyScrollBar.ScollBarThickness),
		// (int) (w - MyScrollBar.ScollBarThickness));
		//
		// scrollY = new MyScrollBarY(map, UIConfig.ICON_SCROLL_Y, (int) (w -
		// MyScrollBar.ScollBarThickness), 2,
		// (int) (h - MyScrollBar.ScollBarThickness));

		mapPanel.add(map);
		scrollPanel.add(mapPanel);
		scrollPanel.add(scrollX);
		scrollPanel.add(scrollY);

		container.add(scrollPanel);

		return map;
	}

	public void initializeMap()
	{
		/*if (scrollX != null)
		{
			scrollX.initialize();
		}

		if (scrollY != null)
		{
			scrollY.initialize();
		}*/

		if (scrollPanel != null)
		{
			scrollPanel.addMouseListener(new MouseDoubleClickListener(new ICallable()
			{
				public void performUIAction()
				{
					maximizeNocMap(scrollPanel, mapPanel, scrollX, scrollY);
				}
			}));
		}
	}

	private void maximizeNocMap(JPanel master, JPanel mapPanel, Scrollbar scrollX, Scrollbar scrollY)
	{
		new MaximizedView(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds(), master, mapPanel,
			scrollX, scrollY);

	}
}
