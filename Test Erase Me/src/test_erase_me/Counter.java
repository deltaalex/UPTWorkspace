package test_erase_me;

import java.io.File;

public class Counter {
	
	public static void main(String[] args) {
		String rootWA   = "d:\\CAR\\A4022\\01-WA\\";
		String rootSnap = "d:\\CAR\\A4022\\02-Snap\\";
		
		scanPath(rootWA);
		scanPath(rootSnap);
		
	}
	
	private static void scanPath(String root)
	{
		File file = new File(root);
		if(!file.exists())
		{
			System.out.println("Path " + root + " does not exist.");
			return;
		}
		
		System.out.println("Scanning for path " + root + " ... ");
				
		System.out.println("Files found : " + countRecursive(file));		
	}
	
	private static int countRecursive(File root)
	{
		if(root == null)
			return 0;
		
		int _count = 0;
		File[] files = root.listFiles();
		
		for(File file : files)
		{
			if(file.isDirectory())
				_count += countRecursive(file);
			else
				_count++;
		}
		
		return _count;			
	}
}
