package benchmarking;

public class TestLoop {

	
	public static void main(String[] args) 
	{
		int m = 50, n = 50, iterations = 200;
		
		AbstractLoop.VERBOSE = false;
		AbstractLoop loopNoAA = new NoAALoop("no_AA", m, n, iterations);
		AbstractLoop loopMSAA = new MSAALoop("MS_AA", m, n, iterations);
		AbstractLoop loopSSAA = new SSAALoop("SS_AA", m, n, iterations);
		
		loopNoAA.run();						
		loopMSAA.run();
		loopSSAA.run();		

	}

}
