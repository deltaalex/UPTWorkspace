/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package upt.social.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.social.model.INode;
import upt.social.state.SimConfig;
import upt.social.state.SimData;
import upt.social.state.SimulationStopConditions;
import upt.social.ui.graphics.DisplayFactory;
import upt.social.ui.graphics.IPercentageDisplay;
import upt.social.ui.graphics.population.IPopulationDisplay;
import upt.social.ui.middleware.UIWatchdog;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;
import upt.ui.factory.GradientFactory;
import upt.ui.utils.TabbedHeader;
import upt.ui.widgets.ButtonFrame;

public class PageSimulation extends AbstractPage
{
	private static final long serialVersionUID = 1L;

	private ButtonFrame bRun, bPause, bStop, bSave;

	private IPercentageDisplay percentageBar;
	private IPercentageDisplay percentageChart;
	private IPopulationDisplay populationPlot;
	private IPopulationDisplay populationTolerancePlot;
	private IPopulationDisplay populationEducationPlot;

	// private TabbedHeader tabs;

	private UIWatchdog dog;

	public PageSimulation(MainFrame frame)
	{
		super(frame, "Simulation");
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		bRun = ButtonFactory.createButtonFrame(this, "Run", UIConfig.ICON_OPEN, WIDTH * 0.02, HEIGHT * 0.05);
		bRun.setSize(100, 50); // ! space needed for map
		registerTooltip(bRun, "Start the simulation", UIConfig.ICON_OPEN);

		bPause = ButtonFactory.createButtonFrame(this, "Pause", UIConfig.ICON_CONFIG, WIDTH * 0.02, HEIGHT * 0.05
			+ ButtonFrame.H + 20);
		bPause.setSize(100, 50);
		registerTooltip(bPause, "Pause the simulation", UIConfig.ICON_CONFIG);
		bPause.setVisible(false);

		bStop = ButtonFactory.createButtonFrame(this, "Stop", UIConfig.ICON_NEW, WIDTH * 0.02, HEIGHT * 0.05
			+ ButtonFrame.H * 2 + 20 * 2);
		bStop.setSize(100, 50);
		registerTooltip(bPause, "Stop and reset the simulation", UIConfig.ICON_NEW);
		bStop.setVisible(false);

		bSave = ButtonFactory.createButtonFrame(this, "Save", UIConfig.ICON_OPEN, WIDTH - 0.02 * WIDTH - 100,
			HEIGHT * 0.05);
		registerTooltip(bSave, "<HTML>Save simulation settings and results for later use<HTML>", UIConfig.ICON_OPEN);

		double chartSize = WIDTH - 300; // 768-10-300 = 458

		percentageChart = DisplayFactory.createPercentageChart(this, WIDTH / 2 - chartSize / 2, HEIGHT / 2 - chartSize
			/ 2, chartSize, chartSize, "Elections",
			GradientFactory.createLinear(ColorFactory.GREEN, ColorFactory.RED, 0, chartSize), "Voters",
			GradientFactory.createLinear(ColorFactory.DARK_ORANGE, ColorFactory.DARK_ORANGE, 0, chartSize),
			"Opinion Change",
			GradientFactory.createLinear(ColorFactory.LIGHT_BLUE, ColorFactory.BLUE, chartSize / 2, chartSize),
			"Tolerance", GradientFactory.createLinear(ColorFactory.IVORY, ColorFactory.BLACK, 0, chartSize),
			"Kurtosis", GradientFactory.createLinear(ColorFactory.BLACK, ColorFactory.BLACK, 0, chartSize));

		populationPlot = DisplayFactory.createPopulationDisplay(this, (SimConfig.NA + SimConfig.SA + SimConfig.AA),
			WIDTH / 2 - chartSize / 2, HEIGHT / 2 - chartSize / 2, chartSize, chartSize, ColorFactory.LIGHT_GREEN,
			ColorFactory.DARK_GREEN, ColorFactory.LIGHT_RED, ColorFactory.DARK_RED, ColorFactory.LIGHT_GRAY);

		populationTolerancePlot = DisplayFactory.createPopulationToleranceDisplay(this,
			(SimConfig.NA + SimConfig.SA + SimConfig.AA), WIDTH / 2 - chartSize / 2, HEIGHT / 2 - chartSize / 2,
			chartSize, chartSize, ColorFactory.WHITE, ColorFactory.BLACK, ColorFactory.SKY_BLUE);

		populationEducationPlot = DisplayFactory.createPopulationEducationDisplay(this,
			(SimConfig.NA + SimConfig.SA + SimConfig.AA), WIDTH / 2 - chartSize / 2, HEIGHT / 2 - chartSize / 2,
			chartSize, chartSize, ColorFactory.BLACK, ColorFactory.WHITE, ColorFactory.DARK_ORANGE);

		percentageBar = DisplayFactory.createPercentageBar(this, ColorFactory.DARK_GREEN, ColorFactory.DARK_RED, WIDTH
			/ 2 - chartSize / 2, HEIGHT / 2 + chartSize / 2 + 13, chartSize, 20, SimConfig.ProFactor);

		TabbedHeader.createTabbedHeader(this, (int) (WIDTH / 2 - chartSize / 2),
			(int) (HEIGHT / 2 - chartSize / 2 - 35), (int) chartSize, 30, percentageChart, populationPlot,
			populationTolerancePlot, populationEducationPlot);

		percentageChart.setVisible(true);

		addListeners();

		initialized = true;
	}

	private void addListeners()
	{
		bRun.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				runSimulation();
				bRun.setVisible(false);
				bPause.setVisible(true);
				bStop.setVisible(true);
			}
		}));

		bPause.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				boolean paused = SimData.getTimerManager().pause();
				dog.pause();
				if (paused)
				{
					bPause.setText("Play");
				}
				else
				{
					bPause.setText("Pause");
				}
			}
		}));

		bStop.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				stopSimulation();
				bRun.setVisible(true);
				bPause.setVisible(false);
				bStop.setVisible(false);
			}
		}));

		bSave.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				try
				{
					saveResults();
				}
				catch (FileNotFoundException e)
				{
					System.err.println("Could not save results : " + e.getMessage());
				}
			}
		}));
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_SIMULATION;
	}

	/**
	 * Called to start a simulation with all predefined settings. Also measures
	 * the simulation time and obtains the inserted long range links.
	 */
	private void runSimulation()
	{
		if (SimulationStopConditions.SimulationRepeatEnabled)
		{
			if (SimulationStopConditions.SimulationIterations > 0)
			{
				SimulationStopConditions.SimulationIterations--;
			}
			else
			{
				System.out.println("DONE repeating simulation");
				return;
			}
		}

		// one normal run
		percentageBar.reset();
		percentageChart.reset();
		populationPlot.reset();
		populationTolerancePlot.reset();
		populationEducationPlot.reset();

		percentageChart.setVisible(true);
		populationPlot.setVisible(false);
		populationTolerancePlot.setVisible(false);
		populationEducationPlot.setVisible(false);

		SimData.reinitializeSocialNetwork();

		// Creates a watchdog thread which manages UI-data communication
		dog = new UIWatchdog(SimData.getTimerManager(), SimData.getNetwork(), SimConfig.MonitorInterval,
			SimulationStopConditions.SimulationRepeatEnabled);

		dog.addPercentageDisplay(percentageBar);
		dog.addPercentageDisplay(percentageChart);
		dog.addPopulationDisplay(populationPlot);
		dog.addPopulationDisplay(populationTolerancePlot);
		dog.addPopulationDisplay(populationEducationPlot);
		dog.start();

		SimData.getTimerManager().start();
	}

	/**
	 * Called to stop and reset the simulation. <br>
	 * Resets the watchdog & social network. Adds same agents to it.
	 */
	private void stopSimulation()
	{
		dog.shutdown();
	}

	/**
	 * Calling this method signals the simulation page that the current
	 * simulation has ended. <br>
	 * Usually called by the watchdog.
	 */
	public void notifySimulationComplete()
	{
		runSimulation();
	}

	private void saveResults() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new File("network.txt"));
		String line;

		for (INode node: SimData.getNetwork().getNodes())
		{
			line = node.getId() + " ";

			if (node.getId().toLowerCase().startsWith("s"))
			{
				line += node.getState() + " ";
			}

			for (INode friend: node.getFriends())
			{
				line += friend.getId() + " ";
			}
			pw.println(line.trim());
		}

		pw.close();
	}
}
