/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexanru Apr 28, 2012 3:44:57 PM </copyright>
 */

package upt.twitter.model.timing;

import java.util.concurrent.TimeUnit;

/**
 * Basic timer class used to measure elapsed time
 */
public class Cronometer
{

	private long tstart, tstop;

	public Cronometer()
	{
		tstart = 0;
		tstop = 0;
	}

	public void start()
	{
		tstart = System.nanoTime();
	}

	public long stop(TimeUnit unit)
	{
		tstop = System.nanoTime();

		if (unit.equals(TimeUnit.NANOSECONDS))
		{
			return (tstop - tstart);
		}
		else if (unit.equals(TimeUnit.MICROSECONDS))
		{
			return (tstop - tstart) / 1000;
		}
		else if (unit.equals(TimeUnit.MILLISECONDS))
		{
			return (tstop - tstart) / 1000000;
		}
		else if (unit.equals(TimeUnit.SECONDS))
		{
			return (tstop - tstart) / 1000000000;
		}
		else
		{
			throw new UnsupportedOperationException("Cannot return time in format : " + unit);
		}
	}

}
