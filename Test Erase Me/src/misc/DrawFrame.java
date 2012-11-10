package misc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawFrame extends JFrame implements AdjustmentListener {
	
	public Vector<CurveArea> curves;
	private double dx, dy;
	private boolean synch = true;
	private Scrollbar sb_y, sb_x; 
	private JButton bSave;
	
	public DrawFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		setUndecorated(true);
		setLayout(null);
		setSize(300, 300);
		setVisible(true);	
				
		JRadioButton radio = new JRadioButton("Synch");
		radio.setLocation(0, getHeight() - 30);
		radio.setSize(100, 20);
		radio.setSelected(synch);
		getContentPane().add(radio);
		
		bSave = new JButton("Save");
		bSave.addActionListener(new BtnListener());
		bSave.setLocation(getWidth()-75, getHeight()-30);
		bSave.setSize(75, 20);
	    getContentPane().add(bSave);
		
		radio.addChangeListener(new ChangeListener() {	
			public void stateChanged(ChangeEvent e) {
				if(e.getSource() != null)
				{
					synch = ((JRadioButton)e.getSource()).isSelected();
				}				
			}
		});
		
		sb_y =  new Scrollbar
		  (Scrollbar.VERTICAL, 0, 30, -100, 100);		
		sb_y.setLocation(0, 20);
		sb_y.setSize(20, 200);
		sb_y.addAdjustmentListener(this);
		getContentPane().add(sb_y);
		
		sb_x =  new Scrollbar
		  (Scrollbar.HORIZONTAL, 0, 30, -100, 100);		
		sb_x.setLocation(20, 0);
		sb_x.setSize(200, 20);
		sb_x.addAdjustmentListener(this);
		getContentPane().add(sb_x);				
	
		curves = new Vector<CurveArea>();
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
					System.exit(0);
				
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
		
		for(CurveArea curve : curves)
			curve.draw(g2, dx, dy, synch);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		Scrollbar sb = (Scrollbar)e.getSource();
		if(sb.equals(sb_x))
			dx = sb.getValue();
		else if(sb.equals(sb_y))
			dy = sb.getValue();
		repaint();
	}
	
	private class BtnListener implements ActionListener
	  {
	    public void actionPerformed(ActionEvent arg0)
	    {
	      JFrame win = (JFrame)SwingUtilities.getWindowAncestor(getContentPane());
	      Dimension size = win.getSize();
	      //BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
	      BufferedImage image = (BufferedImage)win.createImage(size.width, size.height);
	      Graphics g = image.getGraphics();
	      win.paint(g);
	      g.dispose();
	      try
	      {
	        ImageIO.write(image, "png", new File("MyFrame.png"));
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	    }
	  }
}
