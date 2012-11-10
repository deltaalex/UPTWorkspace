package upt.social.ui.middleware;

import java.util.Vector;

import upt.social.model.PollResults;
import upt.social.model.SocialNetwork;
import upt.social.simulation.threading.TimerLayer;
import upt.social.state.SimulationStopConditions;
import upt.social.ui.graphics.IPercentageDisplay;
import upt.social.ui.graphics.population.IPopulationDisplay;

public class UIWatchdog extends Watchdog
{

	// UI related
	private Vector<IPercentageDisplay> perDisplays;
	private Vector<IPopulationDisplay> popDisplays;
	private boolean displayedIntolerance;

	public UIWatchdog(TimerLayer timerManager, SocialNetwork network, int interval, boolean notifyOnComplete)
	{
		super(timerManager, network, interval, notifyOnComplete);

		perDisplays = new Vector<IPercentageDisplay>();
		popDisplays = new Vector<IPopulationDisplay>();
		displayedIntolerance = false;
	}

	@Override
	public void addPercentageDisplay(IPercentageDisplay display)
	{
		if (!perDisplays.contains(display))
		{
			perDisplays.add(display);
		}
	}

	@Override
	public void addPopulationDisplay(IPopulationDisplay display)
	{
		if (!popDisplays.contains(display))
		{
			popDisplays.add(display);
		}
	}

	@Override
	protected void printInfo(PollResults polls)
	{
		for (IPercentageDisplay display: perDisplays)
		{
			display.redraw(polls.getPositivePercentage(), polls.getVotersPercentage(),
				polls.getOpinionChangePercentage(), polls.getTolerance(), polls.getKurtosis());

			if (!displayedIntolerance && polls.getTolerance() <= SimulationStopConditions.SimulationToleranceThreshold)
			{
				display.highlightMessage("Pt =  " + getSimulationTimeMilis() + " ms", 3);
			}
		}

		if (polls.getTolerance() <= SimulationStopConditions.SimulationToleranceThreshold)
		{
			displayedIntolerance = true;
		}

		for (IPopulationDisplay display: popDisplays)
		{
			display.redraw(network.getNodes());
		}
	}
}
