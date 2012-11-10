/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 6:43:40 PM </copyright>
 */
package noc.ui;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import noc.internal.Simulator;
import noc.ui.utils.ButtonFactory;
import noc.ui.utils.NocMapPanel;

public class PageSimulation extends AbstractPage
{
	private static final long serialVersionUID = 1L;

	private Button bRun, bRes;
	private TextArea lConsole;
	private NocMapPanel map;

	public PageSimulation(JFrame frame)
	{
		super(frame, "Simulation");
	}

	private void addListeners()
	{
		bRun.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e)
			{// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent e)
			{// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent e)
			{// TODO Auto-generated method stub
			}

			public void mouseEntered(MouseEvent e)
			{// TODO Auto-generated method stub
			}

			public void mouseClicked(MouseEvent e)
			{
				long t0 = System.nanoTime();
				new Simulator().run();
				long t1 = System.nanoTime();
				bRes.setLabel((t1 - t0) / 1000000 + " ms");
			}
		});
	}

	@Override
	protected void initUI()
	{
		bRun = ButtonFactory.createAndAddButton(this, "Start", 100, 50);
		bRes = ButtonFactory.createAndAddButton(this, "...", 100, 100);

		/*map = MapFactory.createNocMap(this, toolTip, 50, 250);
		map.setSize(300, 300);
		WIDTH = 2 * map.getLocation().x + map.getSize().width;
		HEIGHT = map.getLocation().y + map.getSize().height + 50;
		setSize(WIDTH, HEIGHT);*/

		// JLabel label = new JLabel(
		// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aa aaa");
		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.getViewport().add(map);
		// scrollPane.setLocation(20, 150);
		// scrollPane.setSize(100, 100);
		// add(scrollPane);

		addListeners();
	}
}
