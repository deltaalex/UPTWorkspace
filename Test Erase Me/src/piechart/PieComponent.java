package piechart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class PieComponent extends JComponent 
{
	private static final long serialVersionUID = 1L;
	
	PieValue[] slices = new PieValue[4];	

	public PieComponent() 
	{
		slices[0] = new PieValue(25, Color.red);
		slices[1] = new PieValue(33, Color.green);
		slices[2] = new PieValue(20, Color.pink);
		slices[3] = new PieValue(15, Color.blue);
	}
	
	// slices is an array of values that represent the size of each slice.
	public void drawPie(Graphics2D g, Rectangle area, PieValue[] slices) {
		// Get total value of all slices
		double total = 0.0D;
		for (int i=0; i<slices.length; i++) {
			total += slices[i].getValue();
		}

		// Draw each pie slice
		double curValue = 0.0D;
		int startAngle = 0;
		for (int i=0; i<slices.length; i++) {
			// Compute the start and stop angles
			startAngle = (int)(curValue * 360 / total);
			int arcAngle = (int)(slices[i].getValue() * 360 / total);

			// Ensure that rounding errors do not leave a gap between the first and last slice
			if (i == slices.length-1) {
				arcAngle = 360 - startAngle;
			}

			// Set the color and draw a filled arc
			g.setColor(slices[i].getColor());
			g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);

			curValue += slices[i].getValue();
		}
	}

	// This method is called whenever the contents needs to be painted
	public void paint(Graphics g) {
		// Draw the pie
		drawPie((Graphics2D)g, getBounds(), slices);
	}
}