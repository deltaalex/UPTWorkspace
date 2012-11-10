package upt.listeners.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import upt.listeners.AbstractKeyListener;
import upt.listeners.ICallableKeyEvent;

public class KeyPressListener extends AbstractKeyListener implements KeyListener
{

	public KeyPressListener(ICallableKeyEvent action, int keyCode)
	{
		super(action, keyCode);
	}

	public KeyPressListener(ICallableKeyEvent action)
	{
		super(action);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
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
