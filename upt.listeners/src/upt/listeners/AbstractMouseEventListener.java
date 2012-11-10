package upt.listeners;

import java.awt.event.MouseAdapter;

public abstract class AbstractMouseEventListener extends MouseAdapter
{
	protected ICallableMouseEvent action;

	public AbstractMouseEventListener(ICallableMouseEvent action)
	{
		this.action = action;
	}
}
