/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 15, 2011 1:36:17 PM </copyright>
 */
package upt.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;
import upt.ui.factory.FontFactory;

public abstract class AbstractAboutView extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton bok;

	/**
	 * Fills the view with credits text. Implemented by each application logic.
	 * 
	 * @return HTML formatted string
	 */
	protected abstract String getContentPage();

	public AbstractAboutView(JFrame parent, String title)
	{
		super(parent, title, true);

		Dimension parentSize = parent.getSize();
		Point p = parent.getLocation();
		setLocation(p.x + 5, p.y + parentSize.height / 4);
		setSize(parent.getSize().width - 10, 550);

		setUndecorated(true);
		setDefaultLookAndFeelDecorated(false);

		getContentPane().setBackground(ColorFactory.getWindowBackcolor());
		getContentPane().setForeground(ColorFactory.getWindowForecolor());
		setLayout(null);

		JLabel lTitle = ButtonFactory.createLabel(getContentPane(), title, getWidth() / 2 - 50, 40, 100, 20);
		FontFactory.setFontSize(lTitle, 20);

		String html = loadPageContent(getContentPage());

		ButtonFactory.createLabel(getContentPane(), html, 50, 30, getWidth() - 2 * 30, getHeight() - 100);

		bok = ButtonFactory.createButton(getContentPane(), "Close", getWidth() / 2 - 50, getHeight() - 50, 100, 25);
		bok.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 2, true));

		addListeners();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		bok.setFocusable(true);

		setVisible(true);
	}

	private void addListeners()
	{
		bok.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent arg0)
			{ /**/
			}

			public void mousePressed(MouseEvent arg0)
			{ /**/
			}

			public void mouseExited(MouseEvent arg0)
			{ /**/
			}

			public void mouseEntered(MouseEvent arg0)
			{ /**/
			}

			public void mouseClicked(MouseEvent arg0)
			{
				setVisible(false);
				dispose();
			}
		});
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
