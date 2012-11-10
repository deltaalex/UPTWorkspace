/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 13, 2011 3:30:31 PM </copyright>
 */
package upt.ui.widgets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import upt.ui.factory.ColorFactory;

/**
 * Container with image button and text label. <br>
 * Supports click and tool-tips on hovering.
 */
public class ButtonFrame extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static int W = 50 + 10 + 125;
	public static int H = 50;

	private JLabel icon;
	private JLabel label;

	public ButtonFrame(String message, String iconPath, int x, int y)
	{
		setLayout(null);
		setBackground(ColorFactory.getItemBackcolor());
		setLocation(x, y);
		setSize(W, H);

		icon = new JLabel(new ImageIcon(iconPath));
		icon.setSize(50, 50);
		icon.setLocation(0, 0);
		// icon.setBorderPainted(false);

		label = new JLabel(message);
		label.setSize(125, 50);
		label.setLocation(60, 0);
		label.setBackground(ColorFactory.getItemBackcolor());
		label.setForeground(ColorFactory.getItemForecolor());

		add(icon);
		add(label);
	}

	public void setText(String text)
	{
		label.setText(text);
	}

}
