package piechart;

import java.awt.Color;

import javax.swing.JFrame;

//Class to hold a value for a slice
public class PieValue 
{
	private double value;
	private Color color;

	public PieValue(double value, Color color) {
		this.value = value;
		this.color = color;
	}
	
	public double getValue() {
		return value;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static void main(String[] args) 
	{
		// Show the component in a frame
		JFrame frame = new JFrame();		
		frame.getContentPane().add(new PieComponent());
		frame.setSize(300, 200);
		frame.setVisible(true);

	}

}
