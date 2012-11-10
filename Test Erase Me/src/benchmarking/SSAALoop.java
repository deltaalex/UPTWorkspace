package benchmarking;

import java.awt.Color;

public class SSAALoop extends AbstractLoop {

	private Color[][] bigScreen;
	
	public SSAALoop(String id, int m, int n, int iterations) {
		super(id, m, n, iterations);
		
		bigScreen = new Color[2*m][2*n];
	}

	@Override
	protected void compute() 
	{						
		for(int i=0; i<2*m; ++i)
		{
			for(int j=0; j<2*n ;++j)
			{
				bigScreen[i][j] = new Color(i%256, j%256, (i+j)%256);
			}
		}		
		
		for(int i=0; i<m; ++i)
		{
			for(int j=0; j<n ;++j)
			{
				screen[i][j] = new Color( (bigScreen[i/2][j/2].getRed() + bigScreen[i/2+1][j/2].getRed() + bigScreen[i/2][j/2+1].getRed() + bigScreen[i/2+1][j/2+1].getRed())/4,
								(bigScreen[i/2][j/2].getGreen() + bigScreen[i/2+1][j/2].getGreen() + bigScreen[i/2][j/2+1].getGreen() + bigScreen[i/2+1][j/2+1].getGreen())/4, 
								(bigScreen[i/2][j/2].getBlue() + bigScreen[i/2+1][j/2].getBlue() + bigScreen[i/2][j/2+1].getBlue() + bigScreen[i/2+1][j/2+1].getBlue())/4);
				
			}
		}
	}
}
