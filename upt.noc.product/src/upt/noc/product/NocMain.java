package upt.noc.product;

import upt.loading.SytemLoaderManager;
import upt.loading.ui.SplashScreen;
import upt.noc.simulation.Simulator;
import upt.noc.ui.PageConfiguration;
import upt.noc.ui.PageHome;
import upt.noc.ui.PageSimulation;
import upt.ui.AbstractPage;
import upt.ui.MainFrame;

public class NocMain
{
	public static void main(String[] args)
	{
		try
		{
			Simulator.NumColumns = 6;
			Simulator.NumRows = 6;
			Simulator.Constraint = 10;
			Simulator.NumThreads = 8;
			SytemLoaderManager.DELAY = 1;

			// long t0 = System.nanoTime();
			// new Simulator().run();
			// long t1 = System.nanoTime();
			// System.out.println("TIME : " + (t1 - t0) / 1000000 + " ms");

			MainFrame frame = new MainFrame();
			AbstractPage homePage = new PageHome(frame);
			AbstractPage configurationPage = new PageConfiguration(frame);
			AbstractPage simulationPage = new PageSimulation(frame);
			frame.chainPages(/*homePage, configurationPage,*/simulationPage);

			SplashScreen splash = new SplashScreen(frame);
			splash.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
