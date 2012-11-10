package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseEventListener;
import upt.listeners.ICallableMouseEvent;

public class MousePressListener extends AbstractMouseEventListener
{
	public MousePressListener(ICallableMouseEvent action)
	{
		super(action);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		action.performUIAction(e);
	}

}
