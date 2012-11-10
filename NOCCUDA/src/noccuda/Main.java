package noccuda;

import noc.internal.Simulator;
import noc.internal.threading.ThreadPool;
import noc.loading.ui.NocSplashScreen;
import noc.ui.AbstractPage;
import noc.ui.NocFrame;
import noc.ui.PageConfiguration;
import noc.ui.PageHome;
import noc.ui.PageSimulation;

public class Main
{

	public static void main(String[] args)
	{
		Simulator.NumColumns = 10;
		Simulator.NumRows = 10;
		Simulator.Constraint = 10;
		ThreadPool.NumThreads = 8;

		// long t0 = System.nanoTime();
		// new Simulator().run();
		// long t1 = System.nanoTime();
		// System.out.println("TIME : " + (t1 - t0) / 1000000 + " ms");

		NocFrame frame = new NocFrame();
		AbstractPage homePage = new PageHome(frame);
		AbstractPage configurationPage = new PageConfiguration(frame);
		AbstractPage simulationPage = new PageSimulation(frame);
		frame.chainPages(simulationPage, homePage, configurationPage, simulationPage);

		NocSplashScreen splash = new NocSplashScreen(frame);
		splash.start();
	}
}
