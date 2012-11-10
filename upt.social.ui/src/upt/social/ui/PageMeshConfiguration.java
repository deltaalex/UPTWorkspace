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
import java.util.Vector;

import upt.listeners.ICallable;
import upt.listeners.ICallableKeyEvent;
import upt.listeners.mouse.MouseClickListener;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.state.SimConfig;
import upt.social.state.SimData;
import upt.social.ui.graphics.SelectionFrameAbsurdAgent;
import upt.social.ui.graphics.SelectionFrameAbsurdAgent.AbsurdAgentButton;
import upt.social.ui.graphics.SelectionFrameStubbornAgent;
import upt.social.ui.graphics.SelectionFrameStubbornAgent.StubbornAgentButton;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;
import upt.ui.factory.ButtonFactory;
import upt.ui.widgets.ButtonFrame;
import upt.ui.widgets.InputFrame;
import upt.ui.widgets.ToggleFrame;

public class PageMeshConfiguration extends AbstractPage
{
	private static final long serialVersionUID = 1L;
	private ButtonFrame bGenerate, bHelp;
	private InputFrame iSizeW, iSAgents, iAAgents, iNAgents, iDensity;
	private ToggleFrame bSWorld, bOpinionated;
	private SelectionFrameStubbornAgent sAgentSelection;
	private SelectionFrameAbsurdAgent aAgentSelection;

	protected Vector<NodeCoordinate> stubbornAgents, absurdAgents;

	public PageMeshConfiguration(MainFrame frame)
	{
		super(frame, UIConfig.TITLE_NETCONFIG);

		stubbornAgents = new Vector<NodeCoordinate>();
		absurdAgents = new Vector<NodeCoordinate>();
	}

	@Override
	protected void initUI()
	{
		// create buttons and register tool tips
		iSizeW = ButtonFactory.createInputFrame(this, UIConfig.BTN_MESH_WIDTH, WIDTH * 0.05, HEIGHT * 0.05);
		registerTooltip(iSizeW, UIConfig.TIP_MESH_WIDTH, UIConfig.ICON_MESH);
		iSizeW.setInput(SimConfig.MESH_WIDTH);

		/** Displayed when small world is toggled true **/
		bSWorld = ButtonFactory.createToggleFrame(this, UIConfig.BTN_MESH_SWORLD, WIDTH * 0.05, HEIGHT * 0.05
			+ ButtonFrame.H * 1, false);
		registerTooltip(bSWorld, UIConfig.TIP_MESH_SWORLD, UIConfig.ICON_SWORLD);

		iDensity = ButtonFactory.createInputFrame(this, UIConfig.BTN_MESH_DENSITY, WIDTH * 0.05, HEIGHT * 0.05
			+ ButtonFrame.H * 2);
		registerTooltip(iDensity, UIConfig.TIP_MESH_DENSITY, UIConfig.ICON_SWORLD);
		iDensity.setInput(SimConfig.LongRangeDensity);
		iDensity.setVisible(false);

		bOpinionated = ButtonFactory.createToggleFrame(this, "Opinionated ?", WIDTH * 0.05, HEIGHT * 0.05
			+ ButtonFrame.H * 3, SimConfig.isOpinionated);
		registerTooltip(bOpinionated, "<HTML>Nodes have an initial opinion ?<HTML>", UIConfig.ICON_EMPTY);

		iSAgents = ButtonFactory.createInputFrame(this, UIConfig.BTN_MESH_SAGENTS, WIDTH * 0.05, HEIGHT * 0.8
			- ButtonFrame.H * 3, true);
		registerTooltip(iSAgents, UIConfig.TIP_MESH_SAGENTS, UIConfig.ICON_OPEN);
		iSAgents.setInput(SimConfig.SA);

		iAAgents = ButtonFactory.createInputFrame(this, UIConfig.BTN_MESH_AAGENTS, WIDTH * 0.05, HEIGHT * 0.8
			- ButtonFrame.H * 2, true);
		registerTooltip(iAAgents, UIConfig.TIP_MESH_AAGENTS, UIConfig.ICON_HELP);
		iAAgents.setInput(SimConfig.AA);

		iNAgents = ButtonFactory.createInputFrame(this, UIConfig.BTN_MESH_NAGENTS, WIDTH * 0.05, HEIGHT * 0.8
			- ButtonFrame.H, true);
		registerTooltip(iNAgents, UIConfig.TIP_MESH_NAGENTS, UIConfig.ICON_OPEN);
		iNAgents.setInput(SimConfig.MESH_WIDTH * SimConfig.MESH_HEIGHT - SimConfig.SA);

		bGenerate = ButtonFactory.createButtonFrame(this, UIConfig.BTN_MESH_GENERATE, UIConfig.ICON_OPEN, WIDTH * 0.1,
			HEIGHT * 0.8 + 10);
		registerTooltip(bGenerate, UIConfig.TIP_MESH_GENERATE, UIConfig.ICON_NEW);

		bHelp = ButtonFactory.createButtonFrame(this, UIConfig.BTN_HELP, UIConfig.ICON_HELP, WIDTH - 0.1 * WIDTH
			- InputFrame.W, HEIGHT * 0.8 + 10);
		registerTooltip(bHelp, UIConfig.TIP_HOME_HELP, UIConfig.ICON_HELP);

		sAgentSelection = new SelectionFrameStubbornAgent(this, WIDTH - StubbornAgentButton.W - WIDTH * 0.05,
			HEIGHT * 0.05, HEIGHT * 0.75, SimConfig.MESH_WIDTH, SimConfig.MESH_WIDTH);
		sAgentSelection.setVisible(false);

		aAgentSelection = new SelectionFrameAbsurdAgent(this, WIDTH - AbsurdAgentButton.W - WIDTH * 0.05,
			HEIGHT * 0.05, HEIGHT * 0.75, SimConfig.MESH_WIDTH, SimConfig.MESH_WIDTH);
		aAgentSelection.setVisible(false);

		reloadAgents();

		// add button listeners
		addListeners();

		setVisible(true);
	}

	protected void addListeners()
	{
		bGenerate.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				if (SimConfig.SA > 0 && !isError())
				{
					initializeMeshType();
					parentFrame.nextPage();
				}
			}
		}));

		iSizeW.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				Integer result = sizingHook(iSizeW, 2, SimConfig.MESH_MAX_SIZE);
				SimConfig.MESH_WIDTH = result != null ? result : SimConfig.MESH_WIDTH;
				SimConfig.MESH_HEIGHT = result != null ? result : SimConfig.MESH_WIDTH;

				resetAgents();
			}
		});

		bSWorld.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.isSmallWorld = !SimConfig.isSmallWorld;
				iDensity.setVisible(SimConfig.isSmallWorld);
			}
		}));

		iDensity.addKeyTypeListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				iDensity.setHighlighted(false);
				Double value = iDensity.getInputAsDouble();
				if (value != null && value > 0 && value <= 1)
				{
					SimConfig.LongRangeDensity = value;
					errorDecrease(iDensity);
				}
				else
				{
					iDensity.setHighlighted(true);
					errorIncrease(iDensity);
				}
			}
		});

		bOpinionated.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				SimConfig.isOpinionated = !SimConfig.isOpinionated;
			}
		}));

		iSAgents.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				// close agents view and save data
				if (sAgentSelection.isVisible())
				{
					if (!sAgentSelection.isError())
					{
						stubbornAgents = sAgentSelection.getAgents();
						SimConfig.SA = stubbornAgents.size();
						iSAgents.setInput(SimConfig.SA);
						iNAgents.setInput(SimConfig.MESH_HEIGHT * SimConfig.MESH_WIDTH - SimConfig.SA - SimConfig.AA
							+ "");
						sAgentSelection.setVisible(false);
						iSizeW.setReadOnly(false);
					}
				}
				// show agents view
				else
				{
					sAgentSelection.setVisible(true);
					sAgentSelection.repaint();
					aAgentSelection.setVisible(false);
					iSizeW.setReadOnly(true);
				}
			}
		}));

		iAAgents.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				// close absurd agents view and save data
				if (aAgentSelection.isVisible())
				{
					if (!aAgentSelection.isError())
					{
						absurdAgents = aAgentSelection.getAgents();
						SimConfig.AA = absurdAgents.size();
						iAAgents.setInput(SimConfig.AA);
						iNAgents.setInput(SimConfig.MESH_HEIGHT * SimConfig.MESH_WIDTH - SimConfig.SA - SimConfig.AA
							+ "");
						aAgentSelection.setVisible(false);
						iSizeW.setReadOnly(false);
					}
				}
				// show agents view
				else
				{
					aAgentSelection.setVisible(true);
					aAgentSelection.repaint();
					sAgentSelection.setVisible(false);
					iSizeW.setReadOnly(true);
				}
			}
		}));
	}

	private void resetAgents()
	{
		SimConfig.SA = 0;
		SimConfig.AA = 0;
		iSAgents.setInput(SimConfig.SA);
		iAAgents.setInput(SimConfig.AA);
		sAgentSelection.reset(SimConfig.MESH_WIDTH, SimConfig.MESH_WIDTH);
		aAgentSelection.reset(SimConfig.MESH_WIDTH, SimConfig.MESH_WIDTH);
	}

	private void reloadAgents()
	{
		if (SimData.getStubbornAgents() != null)
		{
			sAgentSelection.set(SimData.getStubbornAgents());
			stubbornAgents = SimData.getStubbornAgents();
		}
		if (SimData.getAbsurdAgents() != null)
		{
			aAgentSelection.set(SimData.getAbsurdAgents());
			absurdAgents = SimData.getAbsurdAgents();
		}
	}

	/***
	 * Initializes the mesh type according to the implementing page type (e.g.
	 * mesh, small-world)
	 */
	private void initializeMeshType()
	{
		absurdAgents.clear();
		stubbornAgents.clear();

		int N = 7;
		double DELTA = 1.0 * SimConfig.MESH_WIDTH / (N + 1);

		int p = 0;
		for (int i = 0; i < N; ++i)
		{
			for (int j = 0; j < N; ++j)
			{
				stubbornAgents.add(new NodeCoordinate((int) ((i + 1) * DELTA), (int) ((j + 1) * DELTA),
					(i * N + j + (N % 2 == 0 ? p : 0)) % 2 == 0 ? STATE.YES : STATE.NO));
			}
			p++;
		}

		// int ratio = 2;
		// for (int i = 0; i < SimConfig.MESH_WIDTH; ++i)
		// {
		// stubbornAgents.add(new NodeCoordinate(i, 0, i % ratio == 0 ?
		// STATE.YES : STATE.NO));
		// stubbornAgents.add(new NodeCoordinate(i, SimConfig.MESH_WIDTH - 1, i
		// % ratio == 0 ? STATE.YES : STATE.NO));
		// }
		// for (int i = 0; i < SimConfig.MESH_WIDTH; ++i)
		// {
		// stubbornAgents.add(new NodeCoordinate(0, i, i % ratio == 0 ?
		// STATE.YES : STATE.NO));
		// stubbornAgents.add(new NodeCoordinate(SimConfig.MESH_WIDTH - 1, i, i
		// % ratio == 0 ? STATE.YES : STATE.NO));
		// }

		SimData.initializeSocialNetwork(stubbornAgents, absurdAgents);
	}

	@Override
	public String getPageToolTip()
	{
		return UIConfig.TIP_MESH;
	}

}
