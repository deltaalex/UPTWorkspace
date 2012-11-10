/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 13, 2011 3:33:07 PM </copyright>
 */
package upt.ui.widgets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import upt.listeners.ICallableKeyEvent;
import upt.ui.factory.ColorFactory;

/**
 * Container with text label and text field. <br>
 * Supports input validation and tool-tips on hovering. <br>
 * Can get/set input data, highlight, set as read-only.
 */
public class InputFrame extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static int LW = 100, TW = 75;
	public static final int W = LW + TW;
	public static final int H = 40;

	private JLabel label;
	private JTextField text;

	public InputFrame(String message, int x, int y)
	{
		this(message, x, y, false);
	}

	public InputFrame(String message, int x, int y, boolean readonly)
	{
		setLayout(null);
		setBackground(ColorFactory.getItemBackcolor());
		setLocation(x, y);
		setSize(W, H);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		label = new JLabel(message);
		label.setSize(LW - 4, H - 4);
		label.setLocation(2, 2);
		label.setBackground(ColorFactory.getItemBackcolor());
		label.setForeground(ColorFactory.getItemForecolor());

		text = new JTextField("", 1);
		text.setSize(TW - 4, H - 4);
		text.setLocation(LW + 2, 2);
		text.setForeground(ColorFactory.getItemForecolor());
		text.setBorder(BorderFactory.createLineBorder(ColorFactory.getAxisColor(), 1));
		text.setEditable(!readonly);

		add(label);
		add(text);
	}

	public void setHighlighted(boolean highlight)
	{
		if (highlight)
		{
			text.setBorder(BorderFactory.createLineBorder(ColorFactory.RED, 2));
		}
		else
		{
			text.setBorder(BorderFactory.createLineBorder(ColorFactory.getAxisColor(), 1));
		}
	}

	public void setReadOnly(boolean readonly)
	{
		text.setEditable(!readonly);
	}

	public void setInput(String text)
	{
		this.text.setText(text);
	}

	public void setInput(int value)
	{
		this.text.setText(value + "");
	}

	public void setInput(double value)
	{
		this.text.setText(value + "");
	}

	public String getInputAsString()
	{
		return text.getText().trim();
	}

	public Integer getInputAsInteger()
	{
		Integer result = null;
		try
		{
			result = Integer.parseInt(text.getText().trim());
		}
		catch (NumberFormatException e)
		{
			result = null;
		}

		return result;
	}

	public Double getInputAsDouble()
	{
		Double result = null;
		try
		{
			result = Double.parseDouble(text.getText().trim());
		}
		catch (NumberFormatException e)
		{
			result = null;
		}

		return result;
	}

	public void addKeyTypeListener(final ICallableKeyEvent action)
	{
		text.addKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				action.performUIAction(e);
			}

			public void keyTyped(KeyEvent e)
			{ /**/
			}

			public void keyPressed(KeyEvent e)
			{ /**/
			}
		});
	}
}