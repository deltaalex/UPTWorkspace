package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseEventListener;
import upt.listeners.ICallableMouseEvent;

public class MouseEnterListener extends AbstractMouseEventListener
{
	public MouseEnterListener(ICallableMouseEvent action)
	{
		super(action);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		action.performUIAction(e);
	}
}
