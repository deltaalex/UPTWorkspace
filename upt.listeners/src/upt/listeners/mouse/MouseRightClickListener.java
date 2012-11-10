package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseListener;
import upt.listeners.ICallable;

public class MouseRightClickListener extends AbstractMouseListener
{
	public MouseRightClickListener(ICallable action)
	{
		super(action);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			action.performUIAction();
		}
	}

}
