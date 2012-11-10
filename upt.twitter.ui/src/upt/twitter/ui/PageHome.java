/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.twitter.ui;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.twitter.model.SimData;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.help.HelpPage;
import upt.ui.utils.SystemUIConfig;
import upt.ui.widgets.ButtonFrame;

public class PageHome extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bNew, bOpen, bHelp, bAbout;

	public PageHome(MainFrame frame)
	{
		super(frame, UIConfig.TITLE_HOME);
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bNew = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HOME_NEW, UIConfig.ICON_NEW, WIDTH * 0.2,
			HEIGHT * 0.25);
		registerTooltip(bNew, UIConfig.TIP_HOME_NEW, UIConfig.ICON_NEW);

		bOpen = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HOME_OPEN, UIConfig.ICON_OPEN, WIDTH - 0.2 * WIDTH
			- 150, HEIGHT * 0.25);
		registerTooltip(bOpen, UIConfig.TIP_HOME_OPEN, UIConfig.ICON_OPEN);

		bHelp = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HELP, UIConfig.ICON_HELP, WIDTH * 0.2, HEIGHT
			- HEIGHT * 0.25 - 50);
		registerTooltip(bHelp, UIConfig.TIP_HOME_HELP, UIConfig.ICON_HELP);

		bAbout = ButtonFactory.createButtonFrame(this, UIConfig.BTN_ABOUT, UIConfig.ICON_CONFIG, WIDTH - 0.2 * WIDTH
			- 150, HEIGHT - HEIGHT * 0.25 - 50);
		registerTooltip(bAbout, UIConfig.TIP_HOME_ABOUT, UIConfig.ICON_CONFIG);

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
				// parentFrame.nextPage();
				SimData.addTwitterTag("obama", null);
			}
		}));

		bHelp.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				new HelpPage(parentFrame, SystemUIConfig.HELP_FOLDER + "/page_home.html");
			}
		}));

		bAbout.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				new TwitterAboutView(parentFrame);
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_HOME;
	}
}
