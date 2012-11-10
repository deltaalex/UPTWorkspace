/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 1:36:17 PM </copyright>
 */
package upt.ui.help;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import upt.listeners.ICallable;
import upt.listeners.mouse.MouseClickListener;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;
import upt.ui.factory.FontFactory;

public class HelpPage extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton bOk;

	public HelpPage(JFrame parent, String contentLocation)
	{
		super(parent, "Help", true);

		Dimension parentSize = parent.getSize();
		Point p = parent.getLocation();
		setLocation(p.x + 5, p.y + parentSize.height / 4);
		setSize(parent.getSize().width - 10, 550);

		setUndecorated(true);
		setDefaultLookAndFeelDecorated(false);

		getContentPane().setBackground(ColorFactory.getWindowBackcolor());
		getContentPane().setForeground(ColorFactory.getWindowForecolor());
		setLayout(null);

		JLabel lTitle = ButtonFactory.createLabel(getContentPane(), "Help", getWidth() / 2 - 50, 40, 100, 20);
		FontFactory.setFontSize(lTitle, 20);

		String html = loadPageContent(contentLocation);

		ButtonFactory.createLabel(getContentPane(), html, 50, 30, getWidth() - 2 * 30, getHeight() - 100);

		bOk = ButtonFactory.createButton(getContentPane(), "Close", getWidth() / 2 - 50, getHeight() - 50, 100, 25);
		bOk.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 2, true));

		addListeners();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		bOk.setFocusable(true);

		setVisible(true);
	}

	private void addListeners()
	{
		bOk.addMouseListener(new MouseClickListener(new ICallable()
		{
			public void performUIAction()
			{
				setVisible(false);
				dispose();
			}
		}));
	}

	/**
	 * Loads a HTML content from a given file
	 * 
	 * @return string
	 */
	private String loadPageContent(String fullPath)
	{
		Scanner scanner = null;

		try
		{
			scanner = new Scanner(new File(fullPath));

			String content = "";
			while (scanner.hasNextLine())
			{
				content += scanner.nextLine();
			}

			return content;
		}
		catch (Exception e)
		{
			return "Error in loading help file";
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
		dispose();
	}
}
