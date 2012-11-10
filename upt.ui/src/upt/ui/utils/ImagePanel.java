/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 13, 2011 6:22:14 PM </copyright>
 */
package upt.ui.utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Image img;

	public ImagePanel(String img)
	{
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img)
	{
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		setBorder(LineBorder.createGrayLineBorder());
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

}
