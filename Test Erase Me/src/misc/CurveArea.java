package misc;

import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

public class CurveArea {

	private double x1, y1;
	private double x2, y2;
	private double zenith;
	private double delta = 0.25;
	
	public CurveArea(double x1, double y1, double x2, double y2, double orientation)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.zenith = orientation > 0 ? 1 : -1;
	}
	
	public void draw(Graphics2D g2, double dx, double dy, boolean synch)
	{
		double _x1 = (x1 <= x2) ? x1 : x2;
		double _x2 = (x1 <= x2) ? x2 : x1;
		
		double _y1 = (y1 <= y2) ? y1 : y2;
		double _y2 = (y1 <= y2) ? y2 : y1;
		
		double _mx = (_x2-_x1)/2 + _x1 + zenith*delta*(x2-x1);
		double _my = (_y2-_y1)/2 + _y1 - zenith*delta*(y2-y1);
		
		if (_mx == _x1 && _mx == _x2)
		{
			_mx += zenith*delta*_my;
			_my = (_y2-_y1)/2 + _y1;
		}
		else if(_my == _y1 && _my == _y2)
		{			
			_mx = (_x2-_x1)/2 + _x1;
			_my += zenith*delta*_mx;
		}
					
		QuadCurve2D q = null;
		if(synch)
			q = new QuadCurve2D.Double(x1+dx, y1+dy, _mx+dx, _my+dy, x2+dx, y2+dy);
		else
			q = new QuadCurve2D.Double(x1+dx, y1+dy, _mx, _my, x2+dx, y2+dy);
		
		g2.draw(q);			
	}
}
