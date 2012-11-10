/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.noc.ui;

import java.util.Vector;

import noc.ui.utils.MapFactory;
import noc.ui.utils.NocMapPanel;
import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.noc.simulation.Simulator;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;

public class PageSimulation extends AbstractPage
{
	private static final long serialVersionUID = 1L;

	private ButtonFrame bRun, bSave;
	private NocMapPanel map;

	public PageSimulation(MainFrame frame)
	{
		super(frame, "Simulation");
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bRun = ButtonFactory.createButtonFrame(this, "Run", UIConfig.ICON_CONFIG, WIDTH * 0.02, HEIGHT * 0.05);
		bRun.setSize(100, 50); // ! space needed for map
		registerTooltip(bRun, "Start the simulation", UIConfig.ICON_CONFIG);

		bSave = ButtonFactory.createButtonFrame(this, "Save", UIConfig.ICON_OPEN, WIDTH - 0.02 * WIDTH - 100,
			HEIGHT * 0.05);
		registerTooltip(bSave, "<HTML>Save simulation settings and results for later use<HTML>", UIConfig.ICON_OPEN);

		addListeners();

		setVisible(true);
	}

	private void addListeners()
	{
		bRun.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				Vector<Integer> nocLinks = runSimulation();
				if (nocLinks != null)
				{
					showResult(nocLinks);
				}
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TTIP_SIM;
	}

	/**
	 * Called to start a simulation with all predefined settings. Also measures
	 * the simulation time and obtains the inserted long range links.
	 */
	private Vector<Integer> runSimulation()
	{
		try
		{
			long t0 = System.nanoTime();
			Simulator simulator = new Simulator();
			simulator.run();
			long t1 = System.nanoTime();
			System.out.println((t1 - t0) / 1000000 + " ms");

			return simulator.getNocLinks();
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private void showResult(Vector<Integer> links)
	{
		double mapSize = WIDTH - WIDTH * 0.02 - 100 - 25 - 0.02 * WIDTH - 100 - 25 - 5;

		MapFactory factory = new MapFactory();
		map = factory.createScrolledNocMap(this, parentFrame.mToolTip, WIDTH * 0.02 + 100 + 25, HEIGHT * 0.05, mapSize,
			mapSize);
		factory.initializeMap();
		map.setLongRangeLinks(links);

		repaint();
		// map.setVisible(false);
	}
}
