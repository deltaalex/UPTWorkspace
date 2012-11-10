package test_erase_me;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FirstExample  {

    private Display display;
    private Shell shell;
    
    private static int num = 0;
    private static int limit = 100000000;
    
    String url = "http://www.javacourses.com/hello.txt";

    public static void main(String args[]) {
       /*display = new Display();
       shell = new Shell(display);
       
       shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) shell.getDisplay().sleep();
	    }*/
    	
    	System.out.println("12|34|56|78".split("[|]+").length);
    	System.exit(1);
    	
    	long t0 = System.nanoTime();
    	findPrimes(limit);
    	long t1 = System.nanoTime() - t0;
    	System.out.println("Found "+num+" primes < "+limit+" in "+(t1/1000)+" ms.");
    }
    
    private static void printPrime(int bn)
	{
		//System.out.println(bn);
		num++;
	}

    private static void findPrimes(int topCandidate)
	{
		//int array[] = new int[topCandidate+2];
		ArrayList<Integer> array = new ArrayList<Integer>();	
		
		/* SET ALL BUT 0 AND 1 TO PRIME STATUS */
		int ss;
		for(ss = 0; ss <= topCandidate+1; ss++)
			array.add(1);
		array.set(0, 0);
		array.set(1, 0);
	
		/* MARK ALL THE NON-PRIMES */
		int thisFactor = 2;
		int lastSquare = 0;
		int thisSquare = 0;
		while(thisFactor * thisFactor <= topCandidate)
			{
			/* MARK THE MULTIPLES OF THIS FACTOR */
			int mark = thisFactor + thisFactor;
			while(mark <= topCandidate)
			{
				array.set(mark, 0);
				mark += thisFactor;
			}
	
			/* PRINT THE PROVEN PRIMES SO FAR */
			thisSquare = thisFactor * thisFactor;
			for(;lastSquare < thisSquare; lastSquare++)
				{
				if(array.get(lastSquare) != 0)
					printPrime(lastSquare);
				}
	
			/* SET thisFactor TO NEXT PRIME */
			thisFactor++;
			while(array.get(thisFactor) == 0) 
				thisFactor++;
			assert(thisFactor <= topCandidate);
			}
	
		/* PRINT THE REMAINING PRIMES */
		for(;lastSquare <= topCandidate; lastSquare++)
			{
			if(array.get(lastSquare) != 0) 
				printPrime(lastSquare);
			}		
	}
    
    


}
