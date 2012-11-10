/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 12:47:37 PM </copyright>
 */
package upt.ui.utils;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TooltipHelper
{
	/**
	 * Prints the tool-tip text
	 */
	private JLabel msgLabel;
	/**
	 * Shows the tool-tip image
	 */
	private JLabel iconLabel;
	/**
	 * Application LOGO. Is shown whenever the no tool-tips are displayed.
	 */
	private final JLabel logoLabel;

	public TooltipHelper(JLabel msgLabel, JLabel iconLabel)
	{
		this.msgLabel = msgLabel;
		this.iconLabel = iconLabel;

		logoLabel = new JLabel(new ImageIcon(SystemUIConfig.LOGO_PATH));
		logoLabel.setSize(100, 50);
		logoLabel.setLocation(iconLabel.getLocation().x - 33, iconLabel.getLocation().y);
		iconLabel.getParent().add(logoLabel);
	}

	public void registerTooltip(Component component, String message, String iconPath)
	{
		component.addMouseListener(new ToolTipListener(component, msgLabel, iconLabel, message, iconPath));
	}

	class ToolTipListener extends MouseAdapter
	{
		private Component component;
		private final JLabel label;
		private final JLabel iconLabel;
		private final String message;
		private final ImageIcon icon;
		private Point point;
		private int DX = 5, DY = 2;

		public ToolTipListener(Component component, JLabel label, JLabel iconLabel, String message, String iconPath)
		{
			this.component = component;
			this.label = label;
			this.iconLabel = iconLabel;
			this.message = message;
			icon = new ImageIcon(iconPath);
			point = component.getLocation();
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			label.setText(message);
			logoLabel.setVisible(false);
			iconLabel.setVisible(true);
			iconLabel.setIcon(icon);
			component.setLocation(point.x + DX, point.y + DY);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			label.setText("");
			iconLabel.setVisible(false);
			logoLabel.setVisible(true);
			component.setLocation(point);
		}
	}
}
