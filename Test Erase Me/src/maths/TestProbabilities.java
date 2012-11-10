package maths;

import java.util.Random;

public class TestProbabilities 
{	
	
	public static void main(String[] args) 
	{
		Random rand = new Random();
		double[] p = new double[4];
		double[] count = new double[p.length];
		double num;
		
		p[0] = 0.2;
		p[1] = 0.1;
		p[2] = 0.65;
		p[3] = 0.05;
		
		for(int i=0; i<p.length; ++i)
		{
			count[i] = 0;
		}
		
		for(int i=0; i<10000; ++i)
		{						
			num = rand.nextDouble();
			count[getEvent(p, num)]++;
		}
		
		for(int i=0; i<count.length; ++i)
		{
			System.out.println(i + " - " + count[i] + " = " + (count[i] / 100) + "%");
		}		
	}
	
	private static int getEvent(double p[], double num)
	{
		double limit = 0;
		
		for(int i=0; i<p.length; ++i)
		{
			limit += p[i];
			if(num < limit)
				return i;				
		}
		return -1;
	}
}
