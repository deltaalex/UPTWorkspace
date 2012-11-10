package dynamictable;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Test extends JFrame {
	private static final long serialVersionUID = 1L;
	private Vector<BuildInfo> builds;
	private Vector<TestInfo> allTests;
	private static final int H = 30;
	private static final int W = 300;
	
	private JScrollPane scrollPane;
	
	public Test()
	{
		setTitle("Results");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = this.getContentPane();
		container.setLayout(null);		
		setSize(500, 500);
		setLocation(400, 200);
		
		Reader reader = new Reader();
		try {		
			String dirFilter = ".*";
			String fileFilter = "build.xml";
			builds = reader.loadData("data", dirFilter, fileFilter);
			mergeForecasts();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(W*builds.size(), (allTests.size()+1) * H);
		mainPanel.setLocation(0, 0);
		mainPanel.setLayout(new BorderLayout());
		
		for(int i=0; i<builds.size(); ++i)
		{
			BuildInfo build = builds.get(i);
			JPanel panel = new JPanel();			
			panel.setBorder(BorderFactory.createBevelBorder(1));
			panel.setSize(W, (allTests.size()+1) * H);
			panel.setLocation(W*i, 0);
			//panel.setLayout(new BorderLayout(5,5));
			
			Label ltitle = new Label(build.name);
			ltitle.setForeground(Color.BLUE);
			ltitle.setSize(panel.getSize().width, H);
			ltitle.setLocation(0, 0);
			panel.add(ltitle);
						
			for(int j=0; j<allTests.size(); ++j)
			{
				String msg = allTests.get(j).name + " - ";
				for(TestInfo test : build.tests)
				{
					if(test.equals(allTests.get(j)))
						msg = test.toString();
				}	
					
				Label lcity = new Label(msg);
				lcity.setSize(panel.getSize().width, H);
				lcity.setLocation(0, H + j*H);
				panel.add(lcity);				
			}
			
			mainPanel.add(panel);			
		}
							
		scrollPane = new JScrollPane(mainPanel);
		scrollPane.setSize(getSize().width, (allTests.size()+1) * H);
		scrollPane.setLocation(0, 0);
		
		//container.add(scrollPane);					
		setVisible(true);
	}

	public static void main(String[] args) {
		 new Test();
	}
	
	@Override
	public void paint(Graphics g) {		
		super.paint(g);
		Graphics2D g2 = (Graphics2D)getGraphics();
		
		int n = 100;
		int stroke = 2;
		int dist = 2; 
		
		drawGradientLines(g2, new ColorGradient() {
			public int getR(int i, int N, float p) {
				return Math.max(0, Math.min(255, (int)(p*255*i/N)));
			}			
			public int getG(int i, int N, float p) {				
				return Math.max(0, Math.min(255, (int)(p*255*i/N)));
			}			
			public int getB(int i, int N, float p) {				
				return Math.max(0, Math.min(255, (int)(255 - p*255*i/N)));
			}
		}, 1.3f, n, 10, 20, 200, 20, stroke, dist);
		
		drawGradientLines(g2, new ColorGradient() {
			public int getR(int i, int N, float p) {
				return 255;
			}			
			public int getG(int i, int N, float p) {			
				return Math.max(0, Math.min(255, (int)(p*(255 - 255*i/N))));
			}			
			public int getB(int i, int N, float p) {			
				return 0;
			}
		}, 1.3f, n, 10, 220, 200, 220, stroke, dist);
		
		
	}
	
	private void drawGradientLines(Graphics2D graph, ColorGradient grad, float pGrad, int nLines, int x1, int y1, int x2, int y2, int lineStroke, int lineDist)
	{
		for (int i=1; i<=nLines; ++i)
		{		
			graph.setColor(new Color(grad.getR(i, nLines, pGrad),grad.getG(i, nLines, pGrad),grad.getB(i, nLines, pGrad)));
			graph.setStroke(new BasicStroke(lineStroke));
			graph.drawLine(x1, y1+lineDist*i, x2, y2+lineDist*i);
		}
	}
	
	interface ColorGradient {
		int getR(int i, int N, float p);
		int getG(int i, int N, float p);
		int getB(int i, int N, float p);
	}
	
	private void mergeForecasts()
	{
		SortedSet<TestInfo> all = new TreeSet<TestInfo>();
		
		for(BuildInfo f : builds)
			for(TestInfo c : f.tests)
				all.add(c);
		
		allTests = new Vector<TestInfo>();
		
		allTests.addAll(all);
	}

}
