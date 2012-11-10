/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 7:02:39 PM </copyright>
 */
package upt.ui.factory;

import java.awt.Color;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import upt.ui.widgets.ButtonFrame;
import upt.ui.widgets.InputFrame;
import upt.ui.widgets.ToggleFrame;

public class ButtonFactory
{
	public static final int B_WIDTH = 100;
	public static final int B_HEIGHT = 25;

	public static JButton createButton(Container container, String msg, int x, int y, int w, int h)
	{
		return createButton(container, msg, null, x, y, w, h);
	}

	public static JButton createAndAddButton(Container container, String msg, int x, int y)
	{
		return createButton(container, msg, x, y, B_WIDTH, B_HEIGHT);
	}

	public static JButton createButton(Container container, String msg, String iconPath, int x, int y, int w, int h)
	{
		JButton button;
		if (iconPath != null)
		{
			button = new JButton(new ImageIcon(iconPath));
			button.setBorderPainted(false);
		}
		else
		{
			button = new JButton(msg);

			button.setBackground(ColorFactory.getItemBackcolor());
			button.setForeground(ColorFactory.getItemForecolor());
		}

		button.setSize(w, h);
		button.setLocation(x, y);

		if (container != null)
		{
			container.add(button);
		}
		return button;
	}

	public static JLabel createLabel(Container container, String msg, int x, int y, int w, int h)
	{
		JLabel label;

		label = new JLabel(msg);
		label.setBackground(ColorFactory.getItemBackcolor());
		label.setForeground(ColorFactory.getItemForecolor());

		label.setSize(w, h);
		label.setLocation(x, y);

		if (container != null)
		{
			container.add(label);
		}
		return label;
	}

	public static JLabel createLabel(Container container, String msg, Color backColor, Color foreColor, int x, int y,
		int w, int h)
	{
		JLabel label = createLabel(container, msg, x, y, w, h);
		label.setForeground(foreColor);
		label.setBackground(backColor);

		return label;
	}

	public static JTextField createText(Container container, String initialTxt, int x, int y, int w, int h,
		boolean readonly)
	{
		JTextField text;

		initialTxt = initialTxt.replaceAll("<HTML>", "");
		initialTxt = initialTxt.replaceAll("</HTML>", "");
		text = new JTextField(initialTxt);
		text.setBackground(ColorFactory.getWindowBackcolor());
		text.setForeground(ColorFactory.getWindowForecolor());

		text.setSize(w, h);
		text.setLocation(x, y);
		text.setEditable(!readonly);
		text.setBorder(null);

		if (container != null)
		{
			container.add(text);
		}
		return text;
	}

	public static JTextArea createTextArea(Container container, String initialTxt, int x, int y, int w, int h,
		boolean readonly)
	{
		JTextArea text;

		initialTxt = initialTxt.replaceAll("<HTML>", "");
		initialTxt = initialTxt.replaceAll("</HTML>", "");
		initialTxt = initialTxt.replaceAll("<br>", "\n");
		text = new JTextArea(initialTxt);
		text.setBackground(ColorFactory.getWindowBackcolor());
		text.setForeground(ColorFactory.getWindowForecolor());

		text.setSize(w, h);
		text.setLocation(x, y);
		text.setEditable(!readonly);
		text.setBorder(null);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);

		if (container != null)
		{
			container.add(text);
		}
		return text;
	}

	public static ButtonFrame createButtonFrame(Container container, String message, String iconPath, double x, double y)
	{
		ButtonFrame bframe = new ButtonFrame(message, iconPath, (int) x, (int) y);
		if (container != null)
		{
			container.add(bframe);
		}

		return bframe;
	}

	public static ToggleFrame createToggleFrame(Container container, String message, double x, double y,
		boolean selected)
	{
		ToggleFrame tframe = new ToggleFrame(message, (int) x, (int) y, selected);
		if (container != null)
		{
			container.add(tframe);
		}

		return tframe;
	}

	public static InputFrame createInputFrame(Container container, String message, double x, double y)
	{
		return createInputFrame(container, message, x, y, false);
	}

	public static InputFrame createInputFrame(Container container, String message, double x, double y, boolean readonly)
	{
		InputFrame bframe = new InputFrame(message, (int) x, (int) y, readonly);
		if (container != null)
		{
			container.add(bframe);
		}

		return bframe;
	}
}
