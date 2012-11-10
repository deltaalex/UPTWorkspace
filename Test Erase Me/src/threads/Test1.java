package threads;

public class Test1 {

	public static void main(String[] args) 
	{
		ConditionalBuffer buffer = new ConditionalBuffer();

		Integer i=0;
		try 
		{
			while(true)
			{
				buffer.put(i);
				System.out.println(buffer.take());
				i++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
