/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.social.ui;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.social.state.SIMMODEL;
import upt.social.state.SimConfig;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;

public class PageModelSelection extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bDiscrete, bAnalog, bComplex, bHelp;

	public PageModelSelection(MainFrame frame)
	{
		super(frame, UIConfig.TITLE_MODELSELECTION);
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bDiscrete = ButtonFactory.createButtonFrame(this, UIConfig.BTN_MODELSELECT_DISCRETE, UIConfig.ICON_DISCRETE,
			WIDTH / 2 - ButtonFrame.W / 2, 100);
		registerTooltip(bDiscrete, UIConfig.TIP_MODELSELECT_DISCRETE, UIConfig.ICON_DISCRETE);

		bAnalog = ButtonFactory.createButtonFrame(this, UIConfig.BTN_MODELSELECT_ANALOG, UIConfig.ICON_ANALOG, WIDTH
			/ 2 - ButtonFrame.W / 2, 200);
		registerTooltip(bAnalog, UIConfig.TIP_MODELSELECT_ANALOG, UIConfig.ICON_ANALOG);

		bComplex = ButtonFactory.createButtonFrame(this, UIConfig.BTN_MODELSELECT_COMPLEX, UIConfig.ICON_COMPLEX, WIDTH
			/ 2 - ButtonFrame.W / 2, 300);
		registerTooltip(bComplex, UIConfig.TIP_MODELSELECT_COMPLEX, UIConfig.ICON_COMPLEX);

		bHelp = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HELP, UIConfig.ICON_HELP, WIDTH / 2 - ButtonFrame.W
			/ 2, HEIGHT - 100);
		registerTooltip(bHelp, UIConfig.TIP_HOME_HELP, UIConfig.ICON_HELP);

		// add button listeners
		addListeners();

		setVisible(true);
	}

	private void addListeners()
	{
		bDiscrete.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.SocialModel = SIMMODEL.DISCETE;
				parentFrame.nextPage();
			}
		}));

		bAnalog.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.SocialModel = SIMMODEL.ANALOG;
				parentFrame.nextPage();
			}
		}));

		bComplex.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.SocialModel = SIMMODEL.COMPLEX;
				parentFrame.nextPage();
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_MODELSELECT;
	}
}
