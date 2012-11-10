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
import upt.social.state.SimConfig;
import upt.social.state.TOPOLOGY;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;
import upt.ui.widgets.InputFrame;

public class PageNetworkSelection extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bMesh, bWrapMesh, bHyper2D, bRandom, bScaleFree, bWSDD, bRing, bLayerRing, bHelp;

	public PageNetworkSelection(MainFrame frame)
	{
		super(frame, UIConfig.TITLE_NETSELECTION);
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bMesh = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_MESH, UIConfig.ICON_MESH, WIDTH * 0.4
			- InputFrame.W, HEIGHT * 0.1);
		registerTooltip(bMesh, UIConfig.TIP_NETSELECT_MESH, UIConfig.ICON_MESH);

		bWrapMesh = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_WRAPMESH, UIConfig.ICON_MESHWRAP,
			WIDTH * 0.6, HEIGHT * 0.1);
		registerTooltip(bWrapMesh, UIConfig.TIP_NETSELECT_WRAPMESH, UIConfig.ICON_MESHWRAP);

		bHyper2D = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_HYPER2D, UIConfig.ICON_HYPER2D, WIDTH
			* 0.4 - InputFrame.W, HEIGHT * 0.1 + 2 * ButtonFrame.H);
		registerTooltip(bHyper2D, UIConfig.TIP_NETSELECT_HYPER2D, UIConfig.ICON_HYPER2D);

		bRandom = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_RANDOM, UIConfig.ICON_RANDOM,
			WIDTH * 0.6, HEIGHT * 0.1 + 2 * ButtonFrame.H);
		registerTooltip(bRandom, UIConfig.TIP_NETSELECT_RANDOM, UIConfig.ICON_RANDOM);

		bScaleFree = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_SCALEFREE, UIConfig.ICON_SCALEFREE,
			WIDTH * 0.4 - InputFrame.W, HEIGHT * 0.1 + 4 * ButtonFrame.H);
		registerTooltip(bScaleFree, UIConfig.TIP_NETSELECT_SCALEFREE, UIConfig.ICON_SCALEFREE);

		bWSDD = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_WSDD, UIConfig.ICON_EMPTY, WIDTH * 0.6,
			HEIGHT * 0.1 + 4 * ButtonFrame.H);
		registerTooltip(bWSDD, UIConfig.TIP_NETSELECT_WSDD, UIConfig.ICON_EMPTY);

		bRing = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_RING, UIConfig.ICON_RING, WIDTH * 0.4
			- InputFrame.W, HEIGHT * 0.1 + 6 * ButtonFrame.H);
		registerTooltip(bRing, UIConfig.TIP_NETSELECT_RING, UIConfig.ICON_RING);

		bLayerRing = ButtonFactory.createButtonFrame(this, UIConfig.BTN_NETSELECT_RING_LAYERED,
			UIConfig.ICON_RING_LAYERED, WIDTH * 0.6, HEIGHT * 0.1 + 6 * ButtonFrame.H);
		registerTooltip(bLayerRing, UIConfig.TIP_NETSELECT_RING_LAYERED, UIConfig.ICON_RING);

		bHelp = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HELP, UIConfig.ICON_HELP, WIDTH / 2 - ButtonFrame.W
			/ 2, HEIGHT - 100);
		registerTooltip(bHelp, UIConfig.TIP_HOME_HELP, UIConfig.ICON_HELP);

		// add button listeners
		addListeners();

		setVisible(true);
	}

	private void addListeners()
	{
		bMesh.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.MESH;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bWrapMesh.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.MESHWRAP;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bHyper2D.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.HYPERCUBE2D;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bRandom.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.RANDOM;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bScaleFree.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.SCALEFREE;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bWSDD.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.WSDD;
				parentFrame.removeFromChain(PageManager.getRingConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getMeshConfigPage());
				parentFrame.nextPage();
			}
		}));

		bRing.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.NetworkModel = TOPOLOGY.RING;
				parentFrame.removeFromChain(PageManager.getMeshConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getRingConfigPage());
				parentFrame.nextPage();
			}
		}));

		bLayerRing.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				System.exit(1);
				SimConfig.NetworkModel = TOPOLOGY.RINGLAYERED;
				parentFrame.removeFromChain(PageManager.getMeshConfigPage());
				parentFrame.insertIntoChainAfterOnce(PageManager.getNetSelectPage(), PageManager.getRingConfigPage());
				parentFrame.nextPage();
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_NETSELECT;
	}
}
