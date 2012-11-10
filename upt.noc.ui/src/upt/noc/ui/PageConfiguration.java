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

public class PageConfiguration extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bNew, bOpen, bSettings, bHelp;

	public PageConfiguration(MainFrame frame)
	{
		super(frame, "Configuration");
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bNew = ButtonFactory.createButtonFrame(this, "<HTML>Generate New Traffic Configuration<HTML>",
			UIConfig.ICON_NEW, WIDTH * 0.2, HEIGHT * 0.25);
		registerTooltip(bNew, "Create a new user-customized network traffic configuraton file", UIConfig.ICON_NEW);

		bOpen = ButtonFactory.createButtonFrame(this, "Open Traffic File", UIConfig.ICON_OPEN, WIDTH - 0.2 * WIDTH
			- 150, HEIGHT * 0.25);
		registerTooltip(bOpen, "Use an existing *.nst traffic file", UIConfig.ICON_OPEN);

		bHelp = ButtonFactory.createButtonFrame(this, "Help", UIConfig.ICON_HELP, WIDTH * 0.2, HEIGHT - HEIGHT * 0.25
			- 50);
		registerTooltip(bHelp, "<HTML>Shows what a traffic configuration means and how a new one can be created<HTML>",
			UIConfig.ICON_HELP);

		bSettings = ButtonFactory.createButtonFrame(this, "Settings", UIConfig.ICON_CONFIG, WIDTH - 0.2 * WIDTH - 150,
			HEIGHT - HEIGHT * 0.25 - 50);
		registerTooltip(bSettings, "Customize traffic generation", UIConfig.ICON_CONFIG);

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

		bSettings.addMouseListener(new MouseClickListener(new ICallable()
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
		return UIConfig.TTIP_CFG;
	}

}
