/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 7:02:39 PM </copyright>
 */
package noc.ui.utils;

import java.awt.Button;
import java.awt.Container;

import javax.swing.JLabel;

public class ButtonFactory
{
	public static final int B_WIDTH = 100;
	public static final int B_HEIGHT = 25;

	public static Button createButtonLeft(String msg, int x, int y, int w, int h, Align align)
	{
		return null;
	}

	public static Button createAndAddButton(Container container, String msg, int x, int y)
	{
		return createButton(container, msg, x, y, B_WIDTH, B_HEIGHT);
	}

	public static Button createButton(Container container, String msg, int x, int y, int w, int h)
	{
		Button button = new Button(msg);
		button.setSize(w, h);
		button.setLocation(x, y);

		button.setBackground(ColorFactory.getItemBackcolor());
		button.setForeground(ColorFactory.getItemForecolor());

		container.add(button);
		return button;
	}

	public static JLabel createLabel(Container container, String msg, int x, int y, int w, int h)
	{
		JLabel label = new JLabel(msg);
		label.setSize(w, h);
		label.setLocation(x, y);

		label.setBackground(ColorFactory.getItemBackcolor());
		label.setForeground(ColorFactory.getItemForecolor());

		container.add(label);
		return label;
	}
}
