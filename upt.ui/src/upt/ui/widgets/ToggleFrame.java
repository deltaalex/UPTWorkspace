/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 13, 2011 3:31:46 PM </copyright>
 */
package upt.ui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import upt.ui.factory.ColorFactory;

/**
 * Container with text label and radio button. <br>
 * Supports click (TRUE/FALSE) and tool-tips on hovering.
 */
public class ToggleFrame extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static int TW = 175 - 36, BW = 36;
	public static final int W = TW + BW;
	public static final int H = 40;

	private JLabel text;
	private JButton option;

	public ToggleFrame(String message, int x, int y, boolean selected)
	{
		setLayout(null);
		setBackground(ColorFactory.getItemBackcolor());
		setLocation(x, y);
		setSize(W, H);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		text = new JLabel(message);
		text.setSize(TW, H - 4);
		text.setLocation(2, 2);
		text.setBackground(ColorFactory.getItemBackcolor());
		text.setForeground(ColorFactory.getItemForecolor());

		option = new JButton();
		option.setSize(BW, BW);
		option.setLocation(TW + 2, 2);
		option.setBackground(selected ? ColorFactory.GREEN : ColorFactory.RED);
		option.setForeground(ColorFactory.getItemForecolor());

		option.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (option.getBackground().equals(ColorFactory.RED))
				{
					option.setBackground(ColorFactory.GREEN);
				}
				else
				{
					option.setBackground(ColorFactory.RED);
				}
			}
		});

		add(text);
		add(option);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l)
	{
		option.addMouseListener(l);
	}

	public boolean getOption()
	{
		return option.getBackground().equals(ColorFactory.GREEN);
	}
}