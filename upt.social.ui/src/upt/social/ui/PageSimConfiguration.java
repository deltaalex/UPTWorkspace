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

import upt.listeners.ICallable;
import upt.listeners.ICallableKeyEvent;
import upt.listeners.mouse.MouseClickListener;
import upt.social.state.SimConfig;
import upt.social.state.SimulationStopConditions;
import upt.social.ui.graphics.SelectionFrameStubbornAgent;
import upt.social.ui.graphics.SelectionFrameStubbornAgent.StubbornAgentButton;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.help.HelpPage;
import upt.ui.utils.SystemUIConfig;
import upt.ui.widgets.ButtonFrame;
import upt.ui.widgets.InputFrame;

public class PageSimConfiguration extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bOk, bDefault, bHelp;
	private InputFrame iNumThreads, iCpu, iMinSleep, iMaxSleep, iMonitorInterval, iSimLength;
	private SelectionFrameStubbornAgent agentSelection;

	public PageSimConfiguration(MainFrame frame)
	{
		super(frame, UIConfig.TITLE_SIMCONFIG);
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		iCpu = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_CPU, WIDTH * 0.4 - InputFrame.W,
			HEIGHT * 0.1);
		registerTooltip(iCpu, UIConfig.TIP_SIMCONFIG_CPU, UIConfig.ICON_MESH);/*icon*/
		iCpu.setInput(SimConfig.CpuUsage);

		iNumThreads = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_THREADS, WIDTH * 0.6, HEIGHT * 0.1);
		registerTooltip(iNumThreads, UIConfig.TIP_SIMCONFIG_THREADS, UIConfig.ICON_MESH);/*icon*/
		iNumThreads.setInput(SimConfig.PoolThreads);

		iMinSleep = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_MINSLEEP, WIDTH * 0.4 - InputFrame.W,
			HEIGHT * 0.1 + ButtonFrame.H + 20);
		registerTooltip(iMinSleep, UIConfig.TIP_SIMCONFIG_MINSLEEP, UIConfig.ICON_OPEN);/*icon*/
		iMinSleep.setInput(SimConfig.MinSleep);

		iMaxSleep = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_MAXSLEEP, WIDTH * 0.6, HEIGHT * 0.1
			+ ButtonFrame.H + 20);
		registerTooltip(iMaxSleep, UIConfig.TIP_SIMCONFIG_MAXSLEEP, UIConfig.ICON_OPEN);/*icon*/
		iMaxSleep.setInput(SimConfig.MaxSleep);

		iMonitorInterval = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_MONITOR_INTERVAL, WIDTH * 0.4
			- InputFrame.W, HEIGHT * 0.1 + ButtonFrame.H * 3 + 20 * 3);
		registerTooltip(iMonitorInterval, UIConfig.TIP_SIMCONFIG_MONITOR_INTERVAL, UIConfig.ICON_OPEN);/*icon*/
		iMonitorInterval.setInput(SimConfig.MonitorInterval);

		iSimLength = ButtonFactory.createInputFrame(this, UIConfig.BTN_SIMCONFIG_SIMLENGTH, WIDTH * 0.6, HEIGHT * 0.1
			+ ButtonFrame.H * 3 + 20 * 3);
		registerTooltip(iSimLength, UIConfig.TIP_SIMCONFIG_SIMLENGTH, UIConfig.ICON_OPEN);/*icon*/
		iSimLength.setInput(SimulationStopConditions.SimulationLength);

		bOk = ButtonFactory.createButtonFrame(this, UIConfig.BTN_SIMCONFIG_OK, UIConfig.ICON_NEW, WIDTH / 2
			- ButtonFrame.W * 1.5, HEIGHT * 0.8 + 10);
		registerTooltip(bOk, UIConfig.TIP_SIMCONFIG_OK, UIConfig.ICON_NEW);

		bDefault = ButtonFactory.createButtonFrame(this, UIConfig.BTN_SIMCONFIG_DEFAULT, UIConfig.ICON_OPEN, WIDTH / 2
			- ButtonFrame.W / 2 * 0.5, HEIGHT * 0.8 + 10);
		registerTooltip(bDefault, UIConfig.TIP_SIMCONFIG_DEFAULT, UIConfig.ICON_OPEN);

		bHelp = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HELP, UIConfig.ICON_HELP, WIDTH / 2 + ButtonFrame.W,
			HEIGHT * 0.8 + 10);
		registerTooltip(bHelp, UIConfig.TIP_HOME_HELP, UIConfig.ICON_HELP);

		agentSelection = new SelectionFrameStubbornAgent(this, WIDTH - StubbornAgentButton.W - WIDTH * 0.05,
			HEIGHT * 0.05, HEIGHT * 0.75, SimConfig.MESH_WIDTH, SimConfig.MESH_HEIGHT);
		agentSelection.setVisible(false);

		// add button listeners
		addListeners();

		setVisible(true);
	}

	protected void addListeners()
	{
		bOk.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				if (!isError())
				{
					parentFrame.nextPage();
				}
			}
		}));

		// XXX
		bDefault.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				iCpu.setInput(8);
			}
		}));

		bHelp.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				new HelpPage(parentFrame, SystemUIConfig.HELP_FOLDER + "/page_simconfig.html");
			}
		}));

		iCpu.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iCpu, 25, 100);
				SimConfig.CpuUsage = result != null ? result : SimConfig.CpuUsage;
			}
		});

		iNumThreads.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iNumThreads, 2, 64);
				SimConfig.PoolThreads = result != null ? result : SimConfig.PoolThreads;
			}
		});

		iMinSleep.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iMinSleep, 1, SimConfig.MaxSleep);
				SimConfig.MinSleep = result != null ? result : SimConfig.MinSleep;
			}
		});

		iMaxSleep.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iMaxSleep, SimConfig.MinSleep, 100);
				SimConfig.MaxSleep = result != null ? result : SimConfig.MaxSleep;
			}
		});

		iMonitorInterval.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iMonitorInterval, 250, 10000);
				SimConfig.MonitorInterval = result != null ? result : SimConfig.MonitorInterval;
			}
		});

		iSimLength.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iSimLength, 10, 10000);
				SimulationStopConditions.SimulationLength = result != null ? result
					: SimulationStopConditions.SimulationLength;
			}
		});
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_SIMCONFIG;
	}

}
