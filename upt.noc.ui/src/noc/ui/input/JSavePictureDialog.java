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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextField;

import upt.listeners.ICallableKeyEvent;
import upt.listeners.keyboard.KeyPressListener;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;

public class JSavePictureDialog extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final String[] unnacepted = new String[] {"<", ">", ":", "\"", "\\", "/", "|", "?", "*"};
	private Container c;
	private JFrame target;
	private JTextField text;

	public JSavePictureDialog(JFrame target, String caption, int x, int y)
	{
		this.target = target;
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
				text.setForeground(ColorFactory.getWindowForecolor());
			}
		}));
		text.addKeyListener(new KeyPressListener(new ICallableKeyEvent()
		{
			public void performUIAction(KeyEvent e)
			{
				if (save())
				{
					quit();
				}
			}
		}, KeyEvent.VK_ENTER));
	}

	public void open()
	{
		setVisible(true);
	}

	private boolean save()
	{
		JFrame win = target;
		Dimension size = win.getSize();
		// BufferedImage image = new BufferedImage(size.width,
		// size.height, BufferedImage.TYPE_INT_RGB);
		final BufferedImage image = (BufferedImage) win.createImage(size.width, size.height);
		Graphics g = image.getGraphics();
		win.paint(g);
		g.dispose();

		try
		{
			String ss = text.getText().trim().toLowerCase();
			// cut out extension
			if (ss.endsWith(".png") || ss.endsWith(".jpg") || ss.endsWith(".bmp") || ss.endsWith(".gif"))
			{
				ss = ss.substring(0, ss.lastIndexOf('.'));
			}
			// verify if OK
			for (int i = 0; i < unnacepted.length; ++i)
			{
				if (ss.contains(unnacepted[i]))
				{
					text.setForeground(ColorFactory.RED);
					return false;
				}
			}

			ImageIO.write(image, "png", new File(ss + ".png"));
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Could not save picture for output. [" + e.getMessage() + "]");
		}
		return false;
	}

	private void quit()
	{
		dispose();
	}
}
