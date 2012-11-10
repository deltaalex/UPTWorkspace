/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 7, 2011 10:35:15 AM </copyright>
 */
package upt.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import upt.listeners.ICallable;
import upt.listeners.ICallableMouseEvent;
import upt.listeners.mouse.MouseClickListener;
import upt.listeners.mouse.MouseDragListener;
import upt.listeners.mouse.MousePressListener;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;
import upt.ui.factory.FontFactory;
import upt.ui.utils.ImagePanel;
import upt.ui.utils.SystemUIConfig;
import upt.ui.utils.TooltipHelper;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 768;
	public static final int HEIGHT = 800;
	private static final Point FramePoint = new Point(5, 200);
	private static Rectangle FrameBounds;
	private ImagePanel container;
	public final TooltipHelper tipHelper;

	private Vector<AbstractPage> pages;
	private int currentPage;
	private JButton bBack, bExit;
	public final JLabel mToolTip, iToolTip;
	private final JLabel pageToolTip;
	private final JLabel pageTitle;

	public MainFrame()
	{
		setTitle("Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setLocation(400, 100);

		// initialize background panel
		container = new ImagePanel(SystemUIConfig.MAIN_BG_PATH);
		container.setBackground(ColorFactory.getWindowBackcolor());
		container.setForeground(ColorFactory.getWindowForecolor());

		// add common UI
		bBack = ButtonFactory.createAndAddButton(container, "Back", 20, HEIGHT - 38);
		bExit = ButtonFactory.createAndAddButton(container, "Exit", WIDTH - 120, HEIGHT - 38);

		// text tool tip for button hovering
		mToolTip = ButtonFactory.createLabel(container, "", 34, 120, WIDTH - 40, 50);
		mToolTip.setForeground(ColorFactory.DARK_ORANGE);
		FontFactory.setFontSize(mToolTip, 14);
		// icon tool tip for button hovering
		iToolTip = ButtonFactory.createLabel(container, "", 34, 62, 50, 50);
		iToolTip.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 2, false));
		iToolTip.setVisible(false);
		// text info on current page
		pageToolTip = ButtonFactory.createLabel(container, "Page description", WIDTH / 2 + 80, 110, WIDTH / 2, 25);
		pageToolTip.setAlignmentY(RIGHT_ALIGNMENT);
		pageToolTip.setForeground(ColorFactory.DARK_ORANGE);
		FontFactory.setFontSize(pageToolTip, 14);
		// title of current page
		pageTitle = ButtonFactory.createLabel(container, "Page title", WIDTH / 2 - 150, 60, 300, 50);
		pageTitle.setForeground(ColorFactory.DARK_ORANGE);
		FontFactory.setFontSize(pageTitle, 24);
		pageTitle.setHorizontalAlignment(SwingConstants.CENTER);

		tipHelper = new TooltipHelper(mToolTip, iToolTip);

		this.getContentPane().add(container);
		addListeners();

		this.repaint();
	}

	public void chainPages(AbstractPage... pages)
	{
		this.pages = new Vector<AbstractPage>();

		for (AbstractPage page: pages)
		{
			this.pages.add(page);
			container.add(page);
			page.setLocation(FramePoint);
			page.setVisible(false);
		}
		FrameBounds = pages[0].getBounds();

		currentPage = 0;
	}

	/**
	 * Inserts a new page into the chain at a specific position. <br>
	 * <b>Repeated calls on this method will have no further effect. The page
	 * can be reinserted only after it is removed!</b>
	 * 
	 * @param insertAfter
	 *        The newPage will be inserted immediately after this page.
	 * @param newPage
	 *        The new page to insert
	 */
	public void insertIntoChainAfterOnce(AbstractPage insertAfter, AbstractPage newPage)
	{
		int index = pages.indexOf(insertAfter) + 1;

		if (!pages.get(index).equals(newPage))
		{
			pages.add(index, newPage);
			container.add(newPage);
			newPage.setLocation(FramePoint);
			newPage.setVisible(false);
		}
	}

	/**
	 * Removes a page from the page chain.
	 * 
	 * @param toRemove
	 *        The specified page
	 */
	public void removeFromChain(AbstractPage toRemove)
	{
		pages.remove(toRemove);
	}

	public void initialize()
	{
		setVisible(true);
		pages.get(currentPage).checkAndInitUI();
		pageTitle.setText(pages.get(currentPage).getTitle());
		pageToolTip.setText(pages.get(currentPage).getPageToolTip());
		bBack.setVisible(false);
	}

	private Point pos;

	private void addListeners()
	{
		bBack.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				previousPage();

			}
		}));

		container.addMouseListener(new MousePressListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				pos = e.getLocationOnScreen();
			}
		}));

		container.addMouseMotionListener(new MouseDragListener(new ICallableMouseEvent()
		{
			public void performUIAction(MouseEvent e)
			{
				if (!FrameBounds.contains(e.getPoint()))
				{
					Point newLoc = new Point(getLocation().x + (e.getLocationOnScreen().x - pos.x), getLocation().y
						+ (e.getLocationOnScreen().y - pos.y));
					setLocation(newLoc);
					pos = e.getLocationOnScreen();
				}
			}
		}));

		bExit.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				if (pages.get(currentPage).canExit())
				{
					System.exit(0);
				}
			}
		}));
	}

	public void nextPage()
	{
		pages.get(currentPage).setVisible(false);
		currentPage++;
		pages.get(currentPage).checkAndInitUI();
		setTitle(pages.get(currentPage).getTitle());
		pageTitle.setText(pages.get(currentPage).getTitle());
		pageToolTip.setText(pages.get(currentPage).getPageToolTip());

		if (pages.get(currentPage).canBack() && currentPage > 0)
		{
			bBack.setVisible(true);
		}
		else
		{
			bBack.setVisible(false);
		}
	}

	public void previousPage()
	{
		pages.get(currentPage).setVisible(false);
		currentPage--;
		pages.get(currentPage).setVisible(true);
		setTitle(pages.get(currentPage).getTitle());
		pageTitle.setText(pages.get(currentPage).getTitle());
		pageToolTip.setText(pages.get(currentPage).getPageToolTip());

		if (pages.get(currentPage).canBack() && currentPage > 0)
		{
			bBack.setVisible(true);
		}
		else
		{
			bBack.setVisible(false);
		}
	}

}
