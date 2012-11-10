package upt.listeners.keyboard;

import java.awt.event.KeyEvent;

import upt.listeners.AbstractKeyListener;
import upt.listeners.ICallableKeyEvent;

public class KeyTypeListener extends AbstractKeyListener
{

	public KeyTypeListener(ICallableKeyEvent action, int keyCode)
	{
		super(action, keyCode);
	}

	public KeyTypeListener(ICallableKeyEvent action)
	{
		super(action);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		action.performUIAction(e);
		if (keyCode == -1)
		{
			action.performUIAction(e);
		}
		else if (e.getKeyCode() == keyCode)
		{
			action.performUIAction(e);
		}
	}

}
