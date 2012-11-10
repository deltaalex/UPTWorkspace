/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Nov 27, 2011 11:22:08 AM </copyright>
 */
package upt.social.simulation.threading;

/**
 * Final representation of an encapsulated value used in concurrency. <br>
 * Holds one integer which can be incremented, decremented and compared to a min
 * and max value.
 * 
 * 
 */
public class Counter
{
	private int count;
	private int min, max;

	public Counter(int max)
	{
		count = 0;
		this.max = max;
	}

	public Counter(int count, int max)
	{
		this.count = count;
		this.max = max;
	}

	public Counter(int count, int min, int max)
	{
		this.count = count;
		this.min = min;
		this.max = max;
	}

	public synchronized void increment()
	{
		count++;
	}

	public synchronized void increment(int factor)
	{
		count += factor;
	}

	public synchronized void decrement()
	{
		count--;
	}

	public synchronized void decrement(int factor)
	{
		count -= factor;
	}

	public synchronized boolean isMax()
	{
		return count >= max;
	}

	public synchronized boolean isMin()
	{
		return count <= min;
	}
}
