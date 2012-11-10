package misc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestFrame extends JFrame {
	
	public TestFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton button = new JButton("click me");
		getContentPane().add(button);
		
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setEnabled(false);
				new TestDialog(null).open();
				setEnabled(true);
				
			}
		});
		
		pack();
		setVisible(true);
	}
}
