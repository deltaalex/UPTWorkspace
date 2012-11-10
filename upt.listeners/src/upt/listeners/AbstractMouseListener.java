package upt.listeners;

import java.awt.event.MouseAdapter;

public abstract class AbstractMouseListener extends MouseAdapter
{
	protected ICallable action;

	public AbstractMouseListener(ICallable action)
	{
		this.action = action;
	}
}