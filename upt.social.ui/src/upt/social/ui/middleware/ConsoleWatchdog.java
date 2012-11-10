package upt.social.ui.middleware;

import upt.social.model.PollResults;
import upt.social.model.SocialNetwork;
import upt.social.simulation.threading.TimerLayer;
import upt.social.ui.graphics.IPercentageDisplay;
import upt.social.ui.graphics.population.IPopulationDisplay;

public class ConsoleWatchdog extends Watchdog
{

	public ConsoleWatchdog(TimerLayer timerManager, SocialNetwork network, int interval, boolean notifyOnComplete)
	{
		super(timerManager, network, interval, notifyOnComplete);
	}

	@Override
	public void addPercentageDisplay(IPercentageDisplay display)
	{
		// nothing
	}

	@Override
	public void addPopulationDisplay(IPopulationDisplay display)
	{
		// / nothing

	}

	@Override
	protected void printInfo(PollResults polls)
	{
		System.out.println(polls);
	}
}
