package upt.twitter.product;

import upt.loading.SytemLoaderManager;
import upt.loading.ui.SplashScreen;
import upt.twitter.model.SimConfig;
import upt.twitter.ui.PageManager;
import upt.ui.MainFrame;

public class TestUI
{
	public static void main(String[] args)
	{
		// threading settings
		SimConfig.PoolThreads = 8;
		SimConfig.CpuUsage = 80;

		// simulation settings

		// optional stop conditions

		// log results
		SimConfig.EnableLogResults = false;

		MainFrame frame = new MainFrame();
		PageManager.initializePages(frame);
		frame.chainPages(PageManager.getHomePage());

		// loading
		SytemLoaderManager.DELAY = 1;
		SplashScreen splash = new SplashScreen(frame);
		splash.start();

	}
}
