package benchmarking;

import java.awt.Color;

public class MSAALoop extends AbstractLoop {
	
	public MSAALoop(String id, int m, int n, int iterations) {
		super(id, m, n, iterations);
	}

	@Override
	protected void compute() 
	{
		Color c1, c2, c3 , c4; 
		for(int i=0; i<m; ++i)
		{
			for(int j=0; j<n ;++j)
			{
				c1 = new Color(i%256, j%256, (i+j)%256);
				c2 = new Color(j%256, i%256, (i+j)%256);
				c3 = new Color(i%256, i%256, j%256);
				c4 = new Color(j%256, j%256, i%256);
				
				screen[i][j] = new Color( (c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed())/4,
								(c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen())/4,
								(c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue())/4);
			}
		}		
	}
}
