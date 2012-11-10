/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 5, 2011 2:25:41 PM </copyright>
 */
package upt.social.ui.graphics;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import upt.listeners.ICallable;
import upt.listeners.ICallableKeyEvent;
import upt.listeners.keyboard.KeyTypeListener;
import upt.listeners.mouse.MouseClickListener;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.state.SimConfig;
import upt.social.ui.graphics.SelectionFrameStubbornAgent.StubbornAgentButton;
import upt.ui.factory.ButtonFactory;
import upt.ui.factory.ColorFactory;
import upt.ui.widgets.ScrolledView;

/**
 * Opens up a model frame in which stubborn agents can be selected, added,
 * removed and their position and status edited.
 */
public class SelectionFrameAbsurdAgent extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static final int W = AbsurdAgentButton.W + 4;
	private int h;
	private int maxX, maxY;
	private Vector<AbsurdAgentButton> buttons;
	private ScrolledView scroller;
	private int ERROR = 0;

	public SelectionFrameAbsurdAgent(Container container, double x, double y, double height, int maxX, int maxY)
	{
		this.h = (int) height;
		this.maxX = maxX;
		this.maxY = maxY;

		setLayout(null);
		setLocation((int) x, (int) y);
		setSize(W, h);
		setBackground(ColorFactory.getWindowBackcolor());
		setBorder(LineBorder.createBlackLineBorder());

		buttons = new Vector<AbsurdAgentButton>();
		buttons.add(new AbsurdAgentButton(this, 0, "Add agents", 2, 2, maxX, maxY, true));
		refreshLayout();

		scroller = new ScrolledView(this, x, y, SelectionFrameAbsurdAgent.W + 2 + 20, height,
			ScrolledView.SCROLL.VERTICAL);
		scroller.setVisible(false);

		container.add(scroller);
	}

	@Override
	public void setVisible(boolean aFlag)
	{
		super.setVisible(aFlag);
		scroller.setVisible(aFlag);
	}

	/**
	 * Reset the frame with new (min, max) values. <br>
	 * These values are used to generate the random coordinates
	 * 
	 * @param maxX
	 *        Mesh width
	 * @param maxY
	 *        Mesh height
	 */
	public void reset(int maxX, int maxY)
	{
		this.maxX = maxX;
		this.maxY = maxY;

		buttons.clear();
		buttons.add(new AbsurdAgentButton(this, 0, "Add agents", 2, 2, maxX, maxY, true));
		refreshLayout();
	}

	/**
	 * Reset the frame with a set of stored agent. <br>
	 */
	public void set(Vector<NodeCoordinate> agents)
	{
		buttons.clear();
		buttons.add(new AbsurdAgentButton(this, 0, "Add agents", 2, 2, maxX, maxY, true));

		for (int i = 1; i <= agents.size(); ++i)
		{
			buttons.add(new AbsurdAgentButton(this, i, "AAgent" + i, 2, 2 + i * AbsurdAgentButton.H,
				agents.get(i - 1).x, agents.get(i - 1).y, maxX, maxY, false));
		}
		refreshLayout();
	}

	public Vector<NodeCoordinate> getAgents()
	{
		Vector<NodeCoordinate> coordinates = new Vector<NodeCoordinate>();

		for (int i = 1; i < buttons.size(); ++i)
		{
			coordinates.add(buttons.get(i).getCoordinate());
		}

		return coordinates;
	}

	public void addAgent(int index)
	{
		AbsurdAgentButton button = null;
		boolean exists = true;
		// do not add same button twice
		while (exists)
		{
			if (buttons.size() >= SimConfig.MESH_WIDTH * SimConfig.MESH_HEIGHT - 1)
			{
				return;
			}
			exists = false;
			button = new AbsurdAgentButton(this, index, "AAgent" + (index + 1), 2, 2 + index * AbsurdAgentButton.H,
				maxX, maxY, false);

			for (AbsurdAgentButton b: buttons)
			{
				if (button.equals(b))
				{
					exists = true;
					break;
				}
			}

		}

		buttons.add(index, button);
		for (int i = index + 1; i < buttons.size(); ++i)
		{
			buttons.get(i).setLocation(2, 2 + i * StubbornAgentButton.H);
			buttons.get(i).setText("AAgent" + (i + 1));
			buttons.get(i).setIndex(i);
		}
		refreshLayout();
	}

	public void removeAgent(int index)
	{
		// list cannot be empty
		if (index == 0)
		{
			return;
		}

		buttons.remove(index);
		for (int i = index; i < buttons.size(); ++i)
		{
			buttons.get(i).setLocation(2, 2 + i * StubbornAgentButton.H);
			buttons.get(i).setText("AAgent" + (i + 1));
			buttons.get(i).setIndex(i);
		}
		refreshLayout();
	}

	private void refreshLayout()
	{
		removeAll();
		for (AbsurdAgentButton button: buttons)
		{
			add(button);
		}

		// increase height when needed
		if (buttons.lastElement().getLocation().y >= getSize().height)
		{
			setSize(getSize().width, getSize().height + h);
		}

		// decrease height when needed
		if (buttons.lastElement().getLocation().y < getSize().height - h)
		{
			setSize(getSize().width, getSize().height - h);
		}

		repaint();
	}

	public boolean isError()
	{
		return ERROR > 0;
	}

	public void errorIncrease()
	{
		ERROR++;
	}

	public void errorDecrease()
	{
		ERROR = Math.max(0, ERROR - 1);
	}

	/**
	 * One panel for editing one basurd agent<br>
	 * Format : String (Name, read-only) | X (int) | Y (int) | (drop-down) |
	 * Remove | Add New After Index list)
	 */
	public static class AbsurdAgentButton extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private int index;
		private SelectionFrameAbsurdAgent parent;
		private static final Random rand = new Random();
		private static int LW = 80, TW = 50, BW = 50;
		public static final int W = LW + TW + TW + BW + BW;
		public static final int H = 30;
		private int maxX, maxY;

		private JLabel lname;
		private JTextField tx, ty;
		private JButton bRemove, bAdd;

		public AbsurdAgentButton(SelectionFrameAbsurdAgent parent, int index, String message, int x, int y, int maxX,
			int maxY, boolean readonly)
		{
			setLayout(null);
			setBackground(ColorFactory.getItemBackcolor());
			setLocation(x, y);
			setSize(W, H);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			this.parent = parent;
			this.index = index;
			this.maxX = maxX;
			this.maxY = maxY;

			lname = new JLabel(message);
			lname.setSize(LW - 4, H - 4);
			lname.setLocation(2, 2);
			lname.setBackground(ColorFactory.getItemBackcolor());
			lname.setForeground(ColorFactory.getItemForecolor());
			lname.setVisible(true);

			tx = new JTextField("" + rand.nextInt(maxX));
			tx.setSize(TW - 4, H - 4);
			tx.setLocation(LW + 2, 2);
			tx.setForeground(ColorFactory.getItemForecolor());
			tx.setBorder(BorderFactory.createLineBorder(ColorFactory.getAxisColor(), 1));

			ty = new JTextField("" + rand.nextInt(maxY));
			ty.setSize(TW - 4, H - 4);
			ty.setLocation(LW + TW + 2, 2);
			ty.setForeground(ColorFactory.getItemForecolor());
			ty.setBorder(BorderFactory.createLineBorder(ColorFactory.getAxisColor(), 1));

			bRemove = ButtonFactory.createButton(readonly ? null : this, "-", LW + TW + TW + 2, 2, BW - 4, H - 4);

			bAdd = ButtonFactory.createButton(this, "+", LW + TW + TW + BW + 2, 2, BW - 4, H - 4);

			add(lname);
			if (!readonly)
			{
				add(tx);
				add(ty);
			}

			addListeners();
		}

		public AbsurdAgentButton(SelectionFrameAbsurdAgent parent, int index, String message, int x, int y,
			int currentX, int currentY, int maxX, int maxY, boolean readonly)
		{
			this(parent, index, message, x, y, maxX, maxY, readonly);
			tx.setText("" + currentX);
			ty.setText("" + currentY);
		}

		public void setIndex(int index)
		{
			this.index = index;
		}

		public void setHighlighted(JTextField field, boolean highlight)
		{
			if (highlight)
			{
				field.setBorder(BorderFactory.createLineBorder(ColorFactory.RED, 2));
			}
			else
			{
				field.setBorder(BorderFactory.createLineBorder(ColorFactory.getAxisColor(), 1));
			}
		}

		public void setText(String name)
		{
			lname.setText(name);
		}

		private Integer getValue(JTextField field)
		{
			Integer value = null;
			try
			{
				value = Integer.parseInt(field.getText().trim());
			}
			catch (NumberFormatException ex)
			{
				value = null;
			}
			return value;
		}

		public NodeCoordinate getCoordinate()
		{
			return new NodeCoordinate(Integer.parseInt(tx.getText().trim()), Integer.parseInt(ty.getText().trim()),
				STATE.NONE);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof StubbornAgentButton)
			{
				StubbornAgentButton button = (StubbornAgentButton) obj;
				return button.getCoordinate().x == getCoordinate().x && button.getCoordinate().y == getCoordinate().y;
			}
			return false;
		}

		private void addListeners()
		{
			bAdd.addMouseListener(new MouseClickListener(new ICallable()
			{
				public void performUIAction()
				{
					parent.addAgent(index + 1);
				}
			}));

			bRemove.addMouseListener(new MouseClickListener(new ICallable()
			{
				public void performUIAction()
				{
					parent.removeAgent(index);
				}
			}));

			tx.addKeyListener(new KeyTypeListener(new ICallableKeyEvent()
			{
				public void performUIAction(KeyEvent e)
				{
					Integer value = getValue(tx);
					if (value == null || value < 0 || value >= maxX)
					{
						setHighlighted(tx, true);
						parent.errorIncrease();
					}
					else
					{
						setHighlighted(tx, false);
						parent.errorDecrease();
					}
				}
			}));

			ty.addKeyListener(new KeyTypeListener(new ICallableKeyEvent()
			{
				public void performUIAction(KeyEvent e)
				{
					Integer value = getValue(ty);
					if (value == null || value < 0 || value >= maxY)
					{
						setHighlighted(ty, true);
						parent.errorIncrease();
					}
					else
					{
						setHighlighted(ty, false);
						parent.errorDecrease();
					}
				}
			}));
		}
	}
}
