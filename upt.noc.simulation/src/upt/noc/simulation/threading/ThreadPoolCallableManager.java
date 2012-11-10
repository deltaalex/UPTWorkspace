package upt.noc.simulation.threading;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import upt.noc.structures.NocResult;

public class ThreadPoolCallableManager
{
	private static int numTasks = 10000;
	private static int numThreads = 64;

	private ExecutorService pool;
	private Set<Future<NocResult>> set;

	public ThreadPoolCallableManager()
	{
		pool = Executors.newFixedThreadPool(numThreads);
		set = new HashSet<Future<NocResult>>();
	}

	public void addNocRunnable(Callable<NocResult> callable)
	{
		Future<NocResult> future = pool.submit(callable);
		set.add(future);
	}

	public NocResult getBestNocResult(double minRate) throws InterruptedException, ExecutionException
	{
		NocResult result;
		NocResult bestResult = null;

		for (Future<NocResult> future: set)
		{
			result = future.get();
			// A better configuration is found
			if (result.getCurrRate() < minRate)
			{
				minRate = result.getCurrRate();
				bestResult = result;
			}
		}
		// pool.shutdown();
		return bestResult;
	}

	public void shutdown()
	{
		pool.shutdown();
	}

	public static void main(String args[]) throws Exception
	{

		long t0 = System.nanoTime();

		ExecutorService pool = Executors.newFixedThreadPool(numThreads);
		Set<Future<NocResult>> set = new HashSet<Future<NocResult>>();

		for (int i = 0; i < numTasks; ++i)
		{
			Callable<NocResult> callable = new RandomNocCallable(i);
			Future<NocResult> future = pool.submit(callable);
			set.add(future);
		}

		for (Future<NocResult> future: set)
		{
			future.get().getCurrRate();
		}
		long t1 = System.nanoTime();
		System.out.println((t1 - t0) / 1000000);

		pool.shutdown();
	}

	private static class RandomNocCallable implements Callable<NocResult>
	{
		private int value;

		RandomNocCallable(int value)
		{
			this.value = value;
		}

		public NocResult call()
		{
			try
			{
				Thread.sleep(0, 63);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new NocResult(value, 1, 2);
		}
	}
}
