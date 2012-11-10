package upt.twitter.model.timing;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import upt.threading.ThreadPool;
import upt.twitter.model.SimConfig;
import upt.twitter.model.StorageManager;

/**
 * Manages a thread pool for launching tasks that launch Twitter queries
 * 
 * @author Alexander
 */
public class TimerLayer extends Thread
{
	private boolean RUNNING, PAUSED;
	/**
	 * A pool thread is used to launch tasks
	 */
	private ThreadPool threadPool;
	private StorageManager storage;

	public TimerLayer(StorageManager storage, int numberOfPoolThreads)
	{
		this.storage = storage;

		threadPool = new ThreadPool(numberOfPoolThreads);
	}

	/**
	 * Starts the ThreadPool and launched the simulation.
	 */
	@Override
	public void run()
	{
		final Lock lock = new ReentrantLock();

		RUNNING = true;
		PAUSED = false;
		long time0, time1;

		time0 = System.nanoTime();
		while (RUNNING)
		{
			// loops while paused
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

			time0 = System.nanoTime();
			// final Counter counter = new Counter(0,
			// network.getNodes().size());
			//
			// for (final INode node: network.getNodes())
			// {
			// threadPool.runTask(new Runnable()
			// {
			// public void run()
			// {
			// node.changeState();
			//
			// lock.lock();
			// counter.increment();
			// lock.unlock();
			// }
			// });
			// }
			// network.addDay();

			// while (!counter.isMax())
			// {
			// /*wait*/
			// }

			time1 = System.nanoTime();

			// waits in order to keep CPU usage lower
			try
			{
				long sleep = Math.max((110 - SimConfig.CpuUsage) * (time1 - time0) / 1000000 / 100, 0);
				sleep(sleep);
			}
			catch (InterruptedException e1)
			{ /**/}

			// /XXX debug
			// 1 sec ~ 3200 days (on 20x20 mesh)
			// System.out.println(1.3 * (time1 - time0) / 1000000);
		}
	}

	/**
	 * Stops the thread pool. Waits until running tasks are finished.
	 */
	public void shutdown()
	{
		RUNNING = false;
		threadPool.join();
	}

	/**
	 * Immediately stops the thread pool and tasks that are running.
	 */
	public void shutdownImmediately()
	{
		RUNNING = false;
		threadPool.close();
	}

	/**
	 * Pauses and unpauses the simulation
	 * 
	 * @return Current pause status
	 */
	public boolean pause()
	{
		PAUSED = !PAUSED;
		return PAUSED;
	}
}
