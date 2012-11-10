/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 7, 2011 10:35:15 AM </copyright>
 */
package noc.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import noc.ui.utils.ColorFactory;
import noc.ui.utils.ImagePanel;

public class NocFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 768;
	public static final int HEIGHT = 800;
	private static final Point FramePoint = new Point(5, 200);
	private static Rectangle FrameBounds;
	private ImagePanel container;
	private AbstractPage[] pages;

	public NocFrame()
	{
		setTitle("NOC Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(400, 100);

		container = new ImagePanel("pictures/bg_black_orange_1.png");
		container.setBackground(ColorFactory.getWindowBackcolor());
		container.setForeground(ColorFactory.getWindowForecolor());
		this.getContentPane().add(container);
		addListeners();

		this.repaint();
	}

	public void chainPages(AbstractPage... pages)
	{
		this.pages = pages;
		for (AbstractPage page: pages)
		{
			container.add(page);
			page.setLocation(FramePoint);
			page.setVisible(false);
		}
		FrameBounds = pages[0].getBounds();

		if (pages.length < 2)
		{
			return;
		}

		pages[0].setNextPage(pages[1]);
		for (int i = 1; i < pages.length - 1; ++i)
		{
			pages[i].setPreviousPage(pages[i - 1]);
			pages[i].setNextPage(pages[i + 1]);
		}
		pages[pages.length - 1].setPreviousPage(pages[pages.length - 2]);
	}

	public void initialize()
	{
		setVisible(true);
		pages[0].initUI();
		pages[0].setVisible(true);
		setTitle(pages[0].getTitle());
	}

	private Point pos;

	private void addListeners()
	{
		container.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent arg0)
			{/**/
			}

			public void mousePressed(MouseEvent e)
			{
				pos = e.getLocationOnScreen();
			}

			public void mouseExited(MouseEvent arg0)
			{/**/
			}

			public void mouseEntered(MouseEvent arg0)
			{/**/
			}

			public void mouseClicked(MouseEvent arg0)
			{/**/
			}
		});

		container.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseMoved(MouseEvent e)
			{/**/
			}

			public void mouseDragged(MouseEvent e)
			{
				if (!FrameBounds.contains(e.getPoint()))
				{
					Point newLoc = new Point(getLocation().x + (e.getLocationOnScreen().x - pos.x), getLocation().y
						+ (e.getLocationOnScreen().y - pos.y));
					setLocation(newLoc);
					pos = e.getLocationOnScreen();
				}
			}
		});
	}

}
