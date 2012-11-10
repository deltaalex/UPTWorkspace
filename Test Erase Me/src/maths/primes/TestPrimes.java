package maths.primes;

public class TestPrimes 
{
	public static void main(String[] args) 
	{
		NumberThread.NPrimes = 20;
		
		NumberGeneratorThread generator = new NumberGeneratorThread();
		NumberThread parentThread = new NumberThread();
		
		generator.setNextThread(parentThread);
		
		parentThread.start();
		generator.start();
	}
}
