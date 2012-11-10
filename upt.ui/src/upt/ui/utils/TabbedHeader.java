/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 2, 2011 12:08:20 AM </copyright>
 */
package upt.ui.utils;

import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;

public class TabbedHeader extends JPanel
{
	private static final long serialVersionUID = 1L;

	private ITabbable[] items;
	private JLabel[] tabs;

	public static TabbedHeader createTabbedHeader(Container container, int x, int y, int width, int height,
		ITabbable... items)
	{
		TabbedHeader header = new TabbedHeader(x, y, width, height, items);
		container.add(header);
		return header;
	}

	private TabbedHeader(int x, int y, int width, int height, ITabbable... items)
	{
		setSize(width, height);
		setLocation(x, y);
		setLayout(null);
		setBorder(LineBorder.createGrayLineBorder());
		setBackground(ColorFactory.getWindowBackcolor());

		int N = items.length;
		this.items = items;
		tabs = new JLabel[N];

		int sx = width / N;
		int sy = height;
		for (int i = 0; i < tabs.length; ++i)
		{
			tabs[i] = ButtonFactory.createLabel(this, items[i].getTabTitle(), sx * i, 0, sx, sy);
			tabs[i].setHorizontalAlignment(SwingConstants.CENTER);
			tabs[i].setVerticalAlignment(SwingConstants.CENTER);
			tabs[i].setBorder(LineBorder.createGrayLineBorder());
		}

		addListeners();
	}

	private void addListeners()
	{
		for (int i = 0; i < tabs.length; ++i)
		{
			final int _i = i;
			tabs[i].addMouseListener(new MouseClickListener(new ICallable()
			{
				public void performUIAction()
				{
					for (int j = 0; j < tabs.length; ++j)
					{
						items[j].setVisible(false);
						tabs[j].setForeground(ColorFactory.getItemForecolor());
					}
					items[_i].setVisible(true);
					tabs[_i].setForeground(ColorFactory.getLongLinkColor());
				}
			}));
		}
	}
}
