package maths;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import javax.naming.NoInitialContextException;

public class TestPI 
{
	private int totalhits;
	
	public void computeSerialPI(int npoints)
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
		
		double myPI = 4.0 * hits / npoints;		
		
		System.out.println("PI : " + myPI);
		//System.out.println("Delta : " + (myPI - Math.PI));
	}
	
	public void computeParallelPI(int nthreads, int npoints)
	{
		PIWorker[] workers = new PIWorker[nthreads];
		for(int i=0; i<nthreads-1; ++i)
		{
			workers[i] = new PIWorker(this, npoints/nthreads);
		}
		workers[nthreads-1] = new PIWorker(this, npoints - (nthreads-1)*npoints/nthreads);
		totalhits = 0;
				
		for(PIWorker worker : workers)
			worker.start();		
		
		for(PIWorker worker : workers)
			try {
				worker.join();
			} catch (InterruptedException e) {
				System.exit(1);
			}
				
		double myPI = 4.0 * totalhits / npoints;				
		
		System.out.println("PI : " + myPI);
		//System.out.println("Delta : " + (myPI - Math.PI));
	}
	
	public synchronized void addHits(int hits)
	{
		totalhits += hits;
	}
	
	
	public static void main(String[] args) 
	{
		int npoints = 50000000;
		int nthreads = 10;
		
		long t0 = System.nanoTime();		
		new TestPI().computeSerialPI(npoints);
		long t1 = System.nanoTime();
		
		System.out.println("Serial time : " + (t1-t0)/1000000000.0 + " s\n");
		
		t0 = System.nanoTime();		
		new TestPI().computeParallelPI(nthreads, npoints);
		t1 = System.nanoTime();
		
		System.out.println("Parallel time : " + (t1-t0)/1000000000.0 + " s\n");
	}
}
