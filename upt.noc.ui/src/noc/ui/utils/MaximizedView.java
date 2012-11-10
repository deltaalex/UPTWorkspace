/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 7:18:51 PM </copyright>
 */
package noc.ui.utils;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Scrollbar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import noc.ui.input.JSavePictureDialog;
import upt.listeners.ICallable;
import upt.listeners.mouse.MouseRightClickListener;

public class MaximizedView extends JFrame
{
	private static final long serialVersionUID = 1L;
	// private NocMapPanel map;
	// private ScrollBar scrollX, scrollY;
	// private JPanel scrollPanel, mapPanel;
	private int dX = 30, dY = 110;

	public MaximizedView(Rectangle bounds, JPanel master, JPanel mapPanel, Scrollbar scrollX, Scrollbar scrollY)
	{
		setMaximizedBounds(bounds);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		setSize(getToolkit().getScreenSize());
		Container c = getContentPane();

		// master.setSize();
		mapPanel.setSize(getSize().width - scrollY.getSize().width - dX, getSize().height - scrollX.getSize().height
			- dY);

		scrollX.setLocation(scrollX.getLocation().x, getSize().height - scrollX.getSize().height - dY);
		// scrollX.setSize(getSize().width - scrollY.getSize().width,
		// scrollX.getSize().height);

		scrollY.setLocation(getSize().width - scrollY.getSize().width - dX, scrollY.getLocation().y);
		// scrollY.setSize(scrollY.getSize().width, getSize().height -
		// scrollX.getSize().height);

		c.add(master);

		final JFrame _frame = this;
		mapPanel.addMouseListener(new MouseRightClickListener(new ICallable()
		{
			public void performUIAction()
			{
				JSavePictureDialog dialog = new JSavePictureDialog(_frame, "Picture Name:", getSize().width / 2 - 150,
					getSize().height / 2 - 50);
				dialog.open();
			}
		}));

		setVisible(true);
	}
}
