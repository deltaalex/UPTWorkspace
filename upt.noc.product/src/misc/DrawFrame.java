package misc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DrawFrame extends JFrame
{

	public Vector<CurveArea> curves;

	public DrawFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocation(200, 200);
		setUndecorated(true);

		JLabel label = new JLabel(
			"<HTML>aaa <b>Haha</b> haha <i>ii</i>iI <span style=\"color:blue\">abc</span> <a href=\"www.google.com\">Google</a><HTML>");

		label.setText("<HTML>  <p style=\"color: #0000FF; font-weight: bold; font-size: large\"> Lead Design</p> <p style=\"border-style: dashed; color: #000000; font-weight: bold; font-size: small\"> Alexandru Topirceanu</p> <p style=\"color: #000000; font-size: small\"> UPT Timisoara</p> <br /> <p>"
			+ "<img alt=\"\" src=\"e:\\0 Work\\Eclipse Workspace\\_NOC Workspace\\upt.noc.product\\pictures\\icons\\open.png \""
			+ "style=\"height: 63px; width: 78px\" /><table style=\"width:100%;\">"
			+ "<tr> <td class=\"style1\">a</td><td class=\"style1\"> b</td><td class=\"style1\"> b</td>"
			+ "</tr> <tr> <td> a</td> <td> &nbsp;</td> <td> &nbsp;</td> </tr> <tr> <td> &nbsp;</td> <td> b</td> <td>"
			+ "x</td> </tr> </table> </p> <a href=\"www.google.com\">Google</a></HTML>");
		getContentPane().add(label);

		setSize(300, 300);
		setVisible(true);

		curves = new Vector<CurveArea>();
		addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					System.exit(0);
				}

			}
		});
	}

	@Override
	public void paint(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));

		for (CurveArea curve: curves)
		{
			curve.draw(g2);
		}
	}
}
