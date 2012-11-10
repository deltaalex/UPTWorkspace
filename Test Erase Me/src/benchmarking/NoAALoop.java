package benchmarking;

import java.awt.Color;

public class NoAALoop extends AbstractLoop {
	
	public NoAALoop(String id, int m, int n, int iterations) {
		super(id, m, n, iterations);
	}

	@Override
	protected void compute() 
	{
		for(int i=0; i<m; ++i)
		{
			for(int j=0; j<n ;++j)
			{
				screen[i][j] = new Color(i%256, j%256, (i+j)%256);
			}
		}		
	}
}
