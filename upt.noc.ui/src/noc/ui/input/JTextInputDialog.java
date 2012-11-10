/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Jun 22, 2011 9:56:59 AM </copyright>
 */
package noc.ui.input;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import upt.listeners.ICallableKeyEvent;
import upt.listeners.keyboard.KeyPressListener;
import upt.ui.factory.ButtonFactory;

public class JTextInputDialog extends JFrame implements IUIInput
{
	private static final long serialVersionUID = 1L;
	private Container c;
	// private JLabel label;
	private JTextField text;

	public JTextInputDialog(String caption, int x, int y)
	{
		setUndecorated(true);
		c = getContentPane();
		setSize(300, 100);
		setLocation(x, y);
		setLayout(null);

		ButtonFactory.createLabel(c, caption, 10, 40, 100, 20);
		text = ButtonFactory.createText(c, "", 120, 40, 150, 20, false);
		text.addKeyListener(new KeyPressListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				quit();
			}
		}, KeyEvent.VK_ENTER));

		setVisible(true);
	}

	private void quit()
	{
		dispose();
	}

	@Override
	public void addKeyInputListener(KeyListener listener)
	{
		text.addKeyListener(listener);
	}

}
