/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.social.ui;

import java.awt.event.KeyEvent;

import upt.listeners.ICallableKeyEvent;
import upt.social.state.SimConfig;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;
import upt.ui.widgets.InputFrame;

public class PageRingConfiguration extends PageMeshConfiguration
{
	private static final long serialVersionUID = 1L;
	private InputFrame iNeighbours;

	public PageRingConfiguration(MainFrame frame)
	{
		super(frame);
	}

	@Override
	protected void initUI()
	{
		iNeighbours = ButtonFactory.createInputFrame(this, UIConfig.BTN_RING_NEIGHBOURS, WIDTH * 0.05, HEIGHT * 0.8
			- ButtonFrame.H * 4);
		registerTooltip(iNeighbours, UIConfig.TIP_RING_NEIGHBOURS, UIConfig.ICON_RING);
		iNeighbours.setInput(SimConfig.RingNeighbours);

		super.initUI();
	}

	@Override
	protected void addListeners()
	{
		super.addListeners();

		iNeighbours.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iNeighbours, 1, SimConfig.MESH_WIDTH / 2 - 1);
				SimConfig.RingNeighbours = result != null ? result : SimConfig.RingNeighbours;
			}
		});

	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_RING;
	}

}
