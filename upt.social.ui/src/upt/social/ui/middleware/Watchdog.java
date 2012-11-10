package upt.social.ui.middleware;

import upt.social.model.PollResults;
import upt.social.model.SocialNetwork;
import upt.social.simulation.threading.TimerLayer;
import upt.social.state.SimData;
import upt.social.ui.PageManager;
import upt.social.ui.PageSimulation;
import upt.social.ui.graphics.IPercentageDisplay;
import upt.social.ui.graphics.population.IPopulationDisplay;

/**
 * Independent daemon thread that monitors the network.
 */
public abstract class Watchdog extends Thread
{
	// monitored network
	protected SocialNetwork network;
	// interval at which the thread polls the network
	private int interval;
	// determine whether the watchdog notifies the simulation ending
	private boolean notifyOnComplete;
	// keeps the watchdog alive
	protected boolean RUNNING, PAUSED;

	private TimerLayer timerManager;

	public Watchdog(TimerLayer timerManager, SocialNetwork network, int interval, boolean notifyOnComplete)
	{
		this.timerManager = timerManager;
		this.network = network;
		this.interval = interval;
		this.notifyOnComplete = notifyOnComplete;
	}

	private int k;

	@Override
	public void run()
	{
		RUNNING = true;
		PAUSED = false;
		k = 0;

		while (RUNNING)
		{
			if (PAUSED)
			{
				try
				{
					sleep(1000);
					continue;
				}
				catch (InterruptedException e)
				{/**/}
			}

			PollResults polls = network.getPolls(getSimulationTimeMilis());
			// prints polls as implemented in subclasses
			printInfo(polls);

			try
			{
				sleep(interval);
			}
			catch (InterruptedException e)
			{
				System.err.println("The watchdog has problems sleeping !");
			}

			k++;
			if (SimData.isSimulationGoalReached(polls, k))
			{
				shutdown();
				if (notifyOnComplete)
				{
					((PageSimulation) PageManager.getSimulationPage()).notifySimulationComplete();
				}
			}
		}
	}

	public boolean pause()
	{
		PAUSED = !PAUSED;
		return PAUSED;
	}

	public void shutdown()
	{
		PAUSED = false;
		RUNNING = false;
		timerManager.shutdown();
	}

	protected long getSimulationTimeMilis()
	{
		return k * interval;
	}

	protected abstract void printInfo(PollResults polls);

	public abstract void addPercentageDisplay(IPercentageDisplay display);

	public abstract void addPopulationDisplay(IPopulationDisplay display);
}
