package maths.primes;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NumberThread extends Thread 
{
	private static int NThreads = 1;
	public static int NPrimes;
	
	private int value;
	private ConcurrentLinkedQueue<Integer> queue;
	
	protected boolean running;
	protected NumberThread nextThread;
	
	public NumberThread()
	{
		value = -1;
		queue = new ConcurrentLinkedQueue<Integer>();
		running = true;
	}
	
	@Override
	public void run() 
	{
		Integer number = null;
		while(running)
		{
			if(NThreads == NPrimes)
			{
				return ;				
			}
			
			number = queue.poll();
			
			if(number != null)
			{
				if(value == -1)
				{
					value = number;
					System.out.println(NThreads + " : " + value);
				}
				else 
				{					
					if(number % value != 0)
					{
						if(nextThread == null && NThreads < NPrimes)
						{
							nextThread = new NumberThread();
							NThreads++;
							nextThread.start();																
						}					
						nextThread.pushNumber(number);						
					}
				}								
			}
		}				
	}
	
	public void pushNumber(Integer number)
	{
		queue.offer(number);
	}
	
	public void setNextThread(NumberThread thread)
	{
		nextThread = thread;
	}
	
	public void stopRunning()
	{
		running = false;		
	}
}
