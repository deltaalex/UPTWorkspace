package upt.listeners.mouse;

import java.awt.event.MouseEvent;

import upt.listeners.AbstractMouseEventListener;
import upt.listeners.ICallableMouseEvent;

public class MouseDragListener extends AbstractMouseEventListener
{
	public MouseDragListener(ICallableMouseEvent action)
	{
		super(action);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		action.performUIAction(e);
	}
}
