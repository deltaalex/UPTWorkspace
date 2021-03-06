package noccuda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class TrafficGenerator
{

	public static void main(String[] args)
	{
		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(new File("traffic.config"));
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
			System.exit(1);
		}

		Random rand = new Random();
		int m = 10, n = 10;
		int N = m * n;

		for (int i = 0; i < m; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				int from = rand.nextInt(N);
				int to = rand.nextInt(N);
				while (to == from)
				{
					to = rand.nextInt(N);
				}

				pw.write("@NODE " + from);
				pw.println();
				pw.write("packet_to_destination_rate " + to + " " + rand.nextDouble() / 100.0);
				pw.println();
			}
		}
		pw.close();

		/*Scanner scan = null;
		try {
			scan = new Scanner(new File("traffic.txt"));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
			System.exit(2);
		}
		
		double sum = 0.0;
		while(scan.hasNextLine())
		{					
			String line = scan.nextLine().trim();			
			
			if(line.startsWith("packet_to_destination_rate"))
			{
				sum += Double.parseDouble(line.split(" ")[2]);
			}
		}
		
		System.out.println(sum);*/

	}

}
