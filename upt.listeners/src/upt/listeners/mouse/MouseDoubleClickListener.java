package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseListener;
import upt.listeners.ICallable;

public class MouseDoubleClickListener extends AbstractMouseListener
{
	public MouseDoubleClickListener(ICallable action)
	{
		super(action);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() > 1)
		{
			action.performUIAction();
		}
	}

}
