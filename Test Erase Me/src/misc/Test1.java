package misc;



public class Test1 {

	public static void main(String[] args) 
	{
		
		DrawFrame d = new DrawFrame();
		
		int w = d.getSize().width, h = d.getSize().height;
		int dx = w/4, dy = h/4; 
		
		for(int i=0;i<2;++i)		
		for(int j=0;j<2;++j)
		{
			//d.curves.add(new CurveArea((i+1)*dx, (j+1)*dy, (i+2)*dx, (j+2)*dy, j%2==0?-1:1));
		}
		
		int x1 = 100, x2 = w - 100;
		int y1 = 100, y2 = h - 100;
				
		
		for(int i=-1; i<=1; i+=2)
		{
			d.curves.add(new CurveArea(x1, y1, x1, y2, i));			
			d.curves.add(new CurveArea(x1, y2, x2, y2, i));
			d.curves.add(new CurveArea(x2, y2, x2, y1, i));
			d.curves.add(new CurveArea(x2, y1, x1, y1, i));
		}		
	}

}
