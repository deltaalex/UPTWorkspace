package maths;

import java.util.Random;

public class PIWorker extends Thread 
{
	private int npoints;
	private TestPI parent;
	
	public PIWorker(TestPI parent, int npoints)
	{
		this.parent = parent;
		this.npoints = npoints;
	}
	
	@Override
	public void run() 
	{
		Random rand = new Random();
		double x, y;
		int hits = 0;
		
		for(int i=0; i<npoints; ++i)
		{
			x = rand.nextDouble();
			y = rand.nextDouble();
			
			if((x-0.5)*(x-0.5) + (y-0.5)*(y-0.5) <= 0.25)
				hits++;										
		}
		
		parent.addHits(hits);		
	}
}
