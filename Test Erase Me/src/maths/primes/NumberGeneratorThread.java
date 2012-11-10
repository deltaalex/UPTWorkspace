package maths.primes;

public class NumberGeneratorThread extends NumberThread 
{	
	@Override
	public void run() 
	{
		if(nextThread == null)
		{
			System.err.println("Next thread not initialized");
			System.exit(1);
		}
		
		int number = 2;
		while(running)
		{
			nextThread.pushNumber(number);
			number++;
		}			
	}
	
	
}
