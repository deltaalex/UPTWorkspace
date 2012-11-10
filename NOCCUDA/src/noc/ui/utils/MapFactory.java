/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 8, 2011 11:04:38 AM </copyright>
 */
package noc.ui.utils;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapFactory
{
	public static JPanel createNocMap(Container container, JLabel tipLabel, int x, int y, int w, int h)
	{
		final NocMapPanel map = new NocMapPanel(tipLabel, 0, 0, w, h);

		map.getSize();
		JPanel scrollPane = new JPanel();
		scrollPane.setLayout(new BorderLayout());
		scrollPane.add(map);
		scrollPane.setLocation(x, y);
		scrollPane.setSize(w, h);

		container.add(scrollPane);

		return scrollPane;

	}
}
