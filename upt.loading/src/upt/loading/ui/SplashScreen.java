/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 13, 2011 5:31:33 PM </copyright>
 */
package upt.loading.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import upt.loading.SytemLoaderManager;
import upt.ui.MainFrame;
import upt.ui.factory.ColorFactory;
import upt.ui.utils.ImagePanel;

public class SplashScreen extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 200;
	private ImagePanel container;
	private JLabel animation;

	private SytemLoaderManager loader;
	private MainFrame mainFrame;

	public SplashScreen(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(size.width / 2 - WIDTH / 2, size.height / 2 - HEIGHT / 2);

		container = new ImagePanel("pictures/splash/splash_black_orange_simple.png");
		getContentPane().add(container);
		container.setBorder(new LineBorder(ColorFactory.DARK_ORANGE, 3, true));

		Label lloading = new Label("Loading ...");
		lloading.setLocation(20, 40);
		lloading.setSize(100, 20);
		lloading.setBackground(Color.BLACK);
		lloading.setForeground(ColorFactory.DARK_ORANGE);
		container.add(lloading);

		animation = new JLabel(new ImageIcon("pictures/splash/loading/load_circle_bar.gif"));
		animation.setLocation(20, HEIGHT / 2 - 25);
		animation.setSize(60, 60);
		container.add(animation);

		loader = new SytemLoaderManager(this);
	}

	public void start()
	{
		setVisible(true);

		loader.run();
	}

	public void stop()
	{
		dispose();
		mainFrame.initialize();
	}
}
