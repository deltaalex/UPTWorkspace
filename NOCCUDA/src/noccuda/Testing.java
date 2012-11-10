/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 8, 2011 12:55:50 PM </copyright>
 */
package noccuda;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Testing extends JFrame
{
	private JScrollPane scrollPane;
	private static JPanel top;

	public Testing()
	{
		setTitle("Scrolling Pane Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setBackground(Color.gray);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		getContentPane().add(topPanel);

		JPanel inner = new JPanel();
		FlowLayout layout = new FlowLayout(2, 30, 30);
		inner.setLayout(null);

		for (int i = 0; i < 100; ++i)
		{
			JButton b = new JButton("N" + i);
			b.setSize(100, 100);
			b.setLocation(150 * (i % 10), 150 * (i / 10));
			inner.add(b);
		}
		inner.setLayout(layout);

		// Create a tabbed pane
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(inner);
		scrollPane.setLocation(20, 20);
		scrollPane.setSize(400, 400);

		// topPanel.add(MapFactory.createNocMap(topPanel, label, 20, 20));
		topPanel.add(scrollPane);
		topPanel.setVisible(false);

		top = topPanel;
	}

	public static void showit()
	{
		top.setVisible(true);
	}

	public static void main(String args[])
	{
		// Create an instance of the test application
		Testing mainFrame = new Testing();
		mainFrame.setVisible(true);

		showit();

	}
}
