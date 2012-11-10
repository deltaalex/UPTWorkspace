/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 8, 2011 12:55:50 PM </copyright>
 */
package misc;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

class Testing extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage buf;

	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		// Create a round rectangle.
		RoundRectangle2D r = new RoundRectangle2D.Float(25, 35, 150, 150, 25, 25);
		// Create a texture rectangle the same size as the texture image.
		Rectangle2D tr = new Rectangle2D.Double(0, 0, buf.getWidth(), buf.getHeight());
		// Create the TexturePaint.
		TexturePaint tp = new TexturePaint(buf, tr);
		// Now fill the round rectangle.
		g2.setPaint(tp);
		g2.fill(r);

	}

	public static void main(String[] args)
	{
		Testing f = new Testing();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("PaintingAndStroking v1.0");
		f.setSize(300, 150);
		f.setVisible(true);
	}
}