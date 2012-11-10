/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package noc.ui;

import java.awt.Button;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import noc.ui.utils.ButtonFactory;
import noc.ui.utils.ColorFactory;

public abstract class AbstractPage extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 768 - 10;
	public static int HEIGHT = 550;

	protected Container container;
	private String title;
	private JFrame parentFrame;

	protected boolean canBack, canNext, canExit;
	private Button bBack, bNext, bExit;
	protected JLabel toolTip;

	public AbstractPage(JFrame frame, String title)
	{
		this.title = "NOC Simulator - " + title;
		parentFrame = frame;
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(0, 0);
		setBackground(ColorFactory.getWindowBackcolor());
		setForeground(ColorFactory.getWindowForecolor());

		bBack = ButtonFactory.createAndAddButton(this, "Back", 20, HEIGHT - 80);
		bExit = ButtonFactory.createAndAddButton(this, "Exit", WIDTH / 2 - 50, HEIGHT - 80);
		bNext = ButtonFactory.createAndAddButton(this, "Next", WIDTH - 140, HEIGHT - 80);
		toolTip = ButtonFactory.createLabel(this, "Move mouse over objects for details", 20, 20, WIDTH - 40, 25);

		canNext = true;
		canBack = true;
		canExit = true;

		addListeners();
	}

	public void setPreviousPage(final AbstractPage page)
	{
		bBack.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e)
			{ /**/
			}

			public void mousePressed(MouseEvent e)
			{ /**/
			}

			public void mouseExited(MouseEvent e)
			{ /**/
			}

			public void mouseEntered(MouseEvent e)
			{ /**/
			}

			public void mouseClicked(MouseEvent e)
			{
				if (canBack)
				{
					setVisible(false);
					page.setVisible(true);
					parentFrame.setTitle(page.title);
				}
			}
		});
	}

	public void setNextPage(final AbstractPage page)
	{
		bNext.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e)
			{ /**/
			}

			public void mousePressed(MouseEvent e)
			{ /**/
			}

			public void mouseExited(MouseEvent e)
			{ /**/
			}

			public void mouseEntered(MouseEvent e)
			{ /**/
			}

			public void mouseClicked(MouseEvent e)
			{
				if (canNext)
				{
					setVisible(false);
					page.initUI();
					page.setVisible(true);
					parentFrame.setTitle(page.title);
				}
			}
		});
	}

	private void addListeners()
	{
		bExit.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e)
			{ /**/
			}

			public void mousePressed(MouseEvent e)
			{ /**/
			}

			public void mouseExited(MouseEvent e)
			{ /**/
			}

			public void mouseEntered(MouseEvent e)
			{ /**/
			}

			public void mouseClicked(MouseEvent e)
			{
				if (canExit)
				{
					System.exit(0);
				}
			}
		});
	}

	public String getTitle()
	{
		return title;
	}

	protected abstract void initUI();

}
