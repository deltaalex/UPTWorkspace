package regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegEx {

	 public static void main(String[] args) 
	 {
		 Scanner scan = new Scanner(System.in);
		 while(true) 
		 {
			 System.out.println("Regex : ");
		     Pattern pattern = Pattern.compile(scan.nextLine());		          

		     Matcher matcher = pattern.matcher(scan.nextLine());
		     System.out.println("Text : ");
		     
		     while (matcher.find()) 
		    	 System.out.println("I found the text "+matcher.group());		     		     		     
		    }
	 }
}
