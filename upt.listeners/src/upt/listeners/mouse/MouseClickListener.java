package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseListener;
import upt.listeners.ICallable;

public class MouseClickListener extends AbstractMouseListener
{
	public MouseClickListener(ICallable action)
	{
		super(action);
	}

	// @Override
	// public void mouseClicked(MouseEvent e)
	// {
	// action.performUIAction();
	// }

	@Override
	public void mouseReleased(MouseEvent e)
	{
		action.performUIAction();
	}
}
