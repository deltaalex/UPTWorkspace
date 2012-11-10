/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 13, 2011 5:32:16 PM </copyright>
 */
package noc.loading;

import noc.loading.ui.NocSplashScreen;

public class NocSytemLoaderManager extends Thread
{
	public static final int DELAY = 2000;
	private NocSplashScreen splash;

	public NocSytemLoaderManager(NocSplashScreen splash)
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
