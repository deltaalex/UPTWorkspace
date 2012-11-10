/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.noc.ui;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.noc.ui.assist.NocAboutView;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;

public class PageHome extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bNew, bOpen, bHelp, bAbout;

	public PageHome(MainFrame frame)
	{
		super(frame, "Welcome");
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bNew = ButtonFactory.createButtonFrame(this, "New Project", UIConfig.ICON_NEW, WIDTH * 0.2, HEIGHT * 0.25);
		registerTooltip(bNew, "Start a new simulation from scratch", UIConfig.ICON_NEW);

		bOpen = ButtonFactory.createButtonFrame(this, "Open Project", UIConfig.ICON_OPEN, WIDTH - 0.2 * WIDTH - 150,
			HEIGHT * 0.25);
		registerTooltip(bOpen, "<HTML>Open a previously run simulation<BR>with all the settings and results<HTML>",
			UIConfig.ICON_OPEN);

		bHelp = ButtonFactory.createButtonFrame(this, "Help", UIConfig.ICON_HELP, WIDTH * 0.2, HEIGHT - HEIGHT * 0.25
			- 50);
		registerTooltip(bHelp, "Open the user manual", UIConfig.ICON_HELP);

		bAbout = ButtonFactory.createButtonFrame(this, "About", UIConfig.ICON_CONFIG, WIDTH - 0.2 * WIDTH - 150, HEIGHT
			- HEIGHT * 0.25 - 50);
		registerTooltip(bAbout, "Show credits", UIConfig.ICON_CONFIG);

		// add button listeners
		addListeners();

		setVisible(true);
	}

	private void addListeners()
	{
		bNew.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				parentFrame.nextPage();
			}
		}));

		bAbout.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				new NocAboutView(parentFrame);
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TTIP_HOME;
	}
}
