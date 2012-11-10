/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.ui;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

import upt.ui.factory.ColorFactory;
import upt.ui.widgets.InputFrame;

public abstract class AbstractPage extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = MainFrame.WIDTH - 10;
	public static final int HEIGHT = MainFrame.HEIGHT - 200 - 50;
	private int ERROR = 0;

	protected final MainFrame parentFrame;
	/**
	 * Title printed in the center of the upper part of the application. Is set
	 * by each page.
	 */
	private final String title;

	protected boolean canBack, canNext, canExit;
	protected boolean initialized;

	private Vector<JComponent> validators;

	public AbstractPage(MainFrame frame, String title)
	{
		this.title = title;
		parentFrame = frame;
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(0, 0);
		setBackground(ColorFactory.getWindowBackcolor());
		setForeground(ColorFactory.getWindowForecolor());

		canNext = true;
		canBack = true;
		canExit = true;
		initialized = false;

		validators = new Vector<JComponent>();
	}

	public String getTitle()
	{
		return title;
	}

	public abstract String getPageToolTip();

	public boolean canNext()
	{
		return canNext;
	}

	public boolean canBack()
	{
		return canBack;
	}

	public boolean canExit()
	{
		return canExit;
	}

	public void errorIncrease(JComponent item)
	{
		if (!validators.contains(item))
		{
			validators.add(item);
			ERROR++;
		}
	}

	public void errorDecrease(JComponent item)

	{
		if (validators.contains(item))
		{
			validators.remove(item);
			ERROR = Math.max(0, ERROR - 1);
		}
	}

	public boolean isError()
	{
		return ERROR > 0;
	}

	/**
	 * Used for validating input frames.
	 * 
	 * @param input
	 *        The source widget.
	 * @param gEqualThan
	 *        The minimum value allowed (greater or equal)
	 * @param sEqualThan
	 *        The maximum value allowed (smaller or equal)
	 * @return A new value or null in case of a validation error
	 */
	protected Integer sizingHook(InputFrame input, int gEqualThan, int sEqualThan)
	{
		input.setHighlighted(false);
		Integer value = input.getInputAsInteger();
		// if value is OK save value(s)
		if (value != null && value >= gEqualThan && value <= sEqualThan)
		{
			errorDecrease(input);
			return value;
		}
		// else signal error
		else
		{
			input.setHighlighted(true);
			errorIncrease(input);
		}
		return null;
	}

	protected void registerTooltip(Component component, String message, String iconPath)
	{
		parentFrame.tipHelper.registerTooltip(component, message, iconPath);
	}

	/**
	 * Initializes the page UI if it hasn't been initialized yet. <br>
	 * Makes the page visible.
	 */
	protected void checkAndInitUI()
	{
		if (initialized)
		{
			removeAll();
		}
		initUI();
		initialized = true;
		setVisible(true);
	}

	/**
	 * Populates each page with widgets, controls, information etc.
	 */
	protected abstract void initUI();
}
