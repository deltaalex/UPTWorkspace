/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 13, 2011 5:32:16 PM </copyright>
 */
package upt.loading;

import upt.loading.ui.SplashScreen;

public class SytemLoaderManager extends Thread
{
	public static int DELAY = 2000;
	private SplashScreen splash;

	public SytemLoaderManager(SplashScreen splash)
	{
		this.splash = splash;
	}

	@Override
	public void run()
	{
		try
		{
			sleep(DELAY);
			splash.stop();
		}
		catch (InterruptedException e)
		{ /*ignore*/}

	}
}
