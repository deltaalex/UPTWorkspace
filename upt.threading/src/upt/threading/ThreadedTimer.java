package upt.threading;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class manages a variable number of timer threads which are set to run
 * tasks periodically. Each timer thread will do the exact same task as the
 * others, but on a different data range. <br>
 * <b>Example</b><br>
 * Timer1 will monitor workers 1-100 <br>
 * Timer2 will monitor workers 101-200 <br>
 * etc. <br>
 */
public class ThreadedTimer
{
	private int NumTimers;
	private int interval;
	private Timer[] timers;
	private Random[] rand;
	private TimerTask[] tasks;

	public ThreadedTimer(int numberOfThreads, int interval)
	{
		this.NumTimers = numberOfThreads;
		this.interval = interval;
		timers = new Timer[numberOfThreads];
		rand = new Random[numberOfThreads];

		for (int i = 0; i < numberOfThreads; ++i)
		{
			// XXX set as Daemon - verify performance, CPU usage
			timers[i] = new Timer("Timer" + (i + 1), true);
			rand[i] = new Random();
		}
	}

	/**
	 * Set problem specific tasks.
	 * 
	 * @param tasks
	 *        Implemented in non-abstract layer
	 */
	public void setTasks(TimerTask[] tasks)
	{
		if (tasks.length != NumTimers)
		{
			throw new IllegalArgumentException("Expected " + NumTimers + " tasks instead of " + tasks.length);
		}
		else
		{
			this.tasks = tasks;
		}
	}

	/**
	 * Starts the timers. Each timer will have a thread pool of variable size
	 * with which it will accomplish the given tasks at each timer tick.
	 */
	public void startTimers()
	{
		for (int i = 0; i < NumTimers; ++i)
		{
			timers[i].scheduleAtFixedRate(tasks[i], 0, interval);
		}
	}

	/**
	 * Obtain a random number generator that is specific for each timer thread.
	 * 
	 * @param index
	 *        Index of timer on which the task should run
	 * @return
	 */
	public Random getRandomGenerator(int index)
	{
		if (index >= 0 && index < rand.length && rand != null)
		{
			return rand[index];
		}
		else
		{
			throw new IllegalArgumentException("Generator not initialized or bad index");
		}
	}

	/**
	 * Stops all timers from launching new tasks, but execution will fully stop
	 * when all active tasks will come to an end.
	 */
	public void shutdown()
	{
		for (int i = 0; i < NumTimers; ++i)
		{
			timers[i].cancel();
		}
	}
}
