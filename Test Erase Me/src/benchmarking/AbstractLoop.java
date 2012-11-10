package benchmarking;

import java.awt.Color;
import java.util.Vector;

public abstract class AbstractLoop extends Thread {

	public static boolean VERBOSE = false;
	
	protected int m, n;
	private String id;
	private int iterations;
	
	protected Color[][] screen;
	
	public AbstractLoop(String id, int m, int n, int iterations)
	{
		this.id = id;
		this.m = m;
		this.n = n;
		this.iterations = iterations;
		screen = new Color[m][n];
		
		this.setPriority(MAX_PRIORITY);
	}
	
	// one frame
	public void run()
	{		
		Vector<Double> results = new Vector<Double>();
		System.out.println("Starting FPS test : " + id);

		while(iterations-- > 0)
		{
			long t0 = System.nanoTime();
			compute();
			long t1 = System.nanoTime();
			
			if(VERBOSE)
			{
				System.out.printf("FPS [%5s] : %5.1f\n", id, 1000000000.0/(t1-t0));
			}
			else
			{
				//System.out.print('.');
			}
			results.add(1000000000.0/(t1-t0));			
		}
		
		double avg = 0.0;
		for(Double d : results)
		{
			avg += d;
		}
		avg /= results.size();
		
		System.out.printf("\nAverage FPS [%5s] :  %6.3f\n", id, avg);
		System.out.println();		
	}	
	
	protected abstract void compute();
}
