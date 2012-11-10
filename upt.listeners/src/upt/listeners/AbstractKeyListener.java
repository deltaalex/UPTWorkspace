package upt.listeners;

import java.awt.event.KeyAdapter;

public abstract class AbstractKeyListener extends KeyAdapter
{
	protected ICallableKeyEvent action;
	protected int keyCode;

	public AbstractKeyListener(ICallableKeyEvent action, int keyCode)
	{
		this.action = action;
		this.keyCode = keyCode;
	}

	public AbstractKeyListener(ICallableKeyEvent action)
	{
		this.action = action;
		keyCode = -1;
	}

}
