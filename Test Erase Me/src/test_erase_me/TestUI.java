package test_erase_me;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestUI extends JFrame 
{
	private static final long serialVersionUID = 1L;

	private static final String PAGE  = "                                                                                                                                 ";
	private static final String PAGE2 = ".................................................................................................................................";
	private final JScrollPane pane;
	private final MyPanel panel; 
	private Graphics2D graph;
	
	public TestUI()
	{
		super("scroooooooool bar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setLocation(300, 100);
		setLayout(null);
		
		panel = new MyPanel();
		panel.setLocation(0, 0);
		panel.add(label);
		label.setLocation(300, 0);

		pane = new JScrollPane(panel);
		pane.setSize(400, 428);
		pane.setLocation(30, 30);
	

		getContentPane().add(pane);
		getContentPane().add(new JButton("aaa"));
		setVisible(true);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Drawer(this), 1000, 100);		
	}
	
	public void redraw()
	{			
		panel.validate();
		panel.repaint();
		pane.validate();
		panel.addPoint(400*(k/10) + 400/10, 100 + 200*(k % 2));	
		label.setText(space.toString());
	}
	
	class MyPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		
		private Vector<Point> points;
		
		public MyPanel()
		{
			points = new Vector<Point>();
			points.add(new Point(0,0));			
		}
		
		public void addPoint(int x, int y)
		{
			points.add(new Point(x, y));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
		
			//g.drawImage(image, 0, 0, null);
			for(int i=0;i<points.size()-1;++i)
			{
				g2.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);								
			}
		}
		
		
	}

	private int k = 0;
	private BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	private JLabel label = new JLabel(PAGE);
	private StringBuffer space = new StringBuffer();
	class Drawer extends TimerTask 
	{
		private TestUI frame;		
		
		public Drawer(TestUI frame)
		{
			this.frame = frame;
		}
		
		@Override
		public void run() 
		{
			/**
			 * 0 -> 400
			 * 10 -> 800
			 * 20 -> 1200
			 * 30 -> 1600
			 */
			if(k%10==0)
			{
				image = new BufferedImage(400 * (1+k/10), 400, BufferedImage.TYPE_INT_RGB);
				space.append(PAGE);
			}			
			
			frame.redraw();
			System.out.println(k);			
						
			k++;
		}

	}

	public static void main(String[] args)	{
		new TestUI();
	}
}
