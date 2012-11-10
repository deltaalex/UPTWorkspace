package password;

import java.awt.Container;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Random;

import javax.swing.JFrame;

public class Test extends JFrame {
	private static final long serialVersionUID = 1L;	
	private String password;
	private Random rand = new Random();
	private boolean changed = false;
	
	public Test()
	{
		setTitle("Enter password");
		Container container = this.getContentPane();
		container.setLayout(null);		
		setSize(300, 300);
		setLocation(500, 500);
		
		TextArea tarea = new TextArea();
		tarea.append("A password may only contain letters and digits");
		tarea.append("\nPress BACKSPACE to erase last letter");		
		tarea.append("\nPress DELETE to erase the whole password");		
		tarea.append("\nPress ENTER to submit password");
		tarea.append("\nPress ESCAPE to exit");
		tarea.setLocation(0, 80);
		tarea.setSize(300, 120);
		tarea.setEditable(false);		
		
		final Label lpass = new Label();
		lpass.setSize(200, 30);
		lpass.setLocation(50, 200);		
		
		
		final TextField tpass = new TextField();
		tpass.setLocation(50, 40);
		tpass.setSize(200, 30);
		
		password = "";
		tpass.addKeyListener(new KeyListener() {						
			public void keyTyped(KeyEvent e) {}			
			public void keyReleased(KeyEvent e) { }			
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode())				
				{
					case KeyEvent.VK_ESCAPE : System.exit(0);
					case KeyEvent.VK_ENTER : lpass.setText(password); break;
					default : 
						if(Character.isLetter(e.getKeyChar()) || 
							Character.isDigit(e.getKeyChar()))							
						{
							password += e.getKeyChar();
							changed = false; 							
						}
						else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						{
							int index = password.length()-1;
							password = password.substring(0, index >= 0 ? index : 0);
							changed = false; 							
						}
						else if(e.getKeyCode() == KeyEvent.VK_DELETE)
						{
							password = "";
							changed = false; 							
						}
						lpass.setText(password);
						break;
				}
			}
		});
		tpass.addTextListener(new TextListener() {			
			public void textValueChanged(TextEvent e) {
				if(!changed)
				{
					tpass.setText(obfuscatePassword(password));					
					changed = true;
				}
			}
		});
						
		container.add(tpass);
		container.add(tarea);
		container.add(lpass);
		setVisible(true);
	}
	
	private String obfuscatePassword(String password)
	{		
		char[] x = new char[] {'X', 'x', '*', 'o', '+'};
		String ss = "";
		for(int i=0;i<password.length();++i)
		{
			for(int j=0;j<rand.nextInt(2)+1;++j)
				ss += x[rand.nextInt(x.length)];
		}
		return ss;
	}
	
	public static void main(String[] args) {
		new Test();
	}
	
}
