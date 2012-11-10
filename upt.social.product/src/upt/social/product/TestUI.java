package upt.social.product;

import upt.loading.SytemLoaderManager;
import upt.loading.ui.SplashScreen;
import upt.social.state.SIMMODEL;
import upt.social.state.SimConfig;
import upt.social.state.SimulationStopConditions;
import upt.social.state.TOPOLOGY;
import upt.social.ui.PageManager;
import upt.ui.MainFrame;

public class TestUI {

	public static void main(String[] args) {
		// network settings
		// SimConfig.NA = 100;
		// SimConfig.SA = 0;
		SimConfig.MESH_WIDTH = 100;
		SimConfig.MESH_HEIGHT = 100;
		SimConfig.LongRangeDensity = 0.5;

		// threading settings
		SimConfig.PoolThreads = 8;
		SimConfig.CpuUsage = 80;
		SimConfig.MinSleep = 1;
		SimConfig.MaxSleep = 50;

		// simulation settings
		SimConfig.MonitorInterval = 500;
		SimConfig.SocialModel = SIMMODEL.ANALOG;
		SimConfig.NetworkModel = TOPOLOGY.WSDD;
		SimConfig.isOpinionated = false;
		SimConfig.isSmallWorld = false;

		// optional stop conditions
		SimulationStopConditions.SimulationRepeatEnabled = false;
		SimulationStopConditions.SimStopOnTolerance = false;
		SimulationStopConditions.SimulationIterations = 10;

		// log results
		SimConfig.EnableLogResults = false;

		// loading
		SytemLoaderManager.DELAY = 1;

		MainFrame frame = new MainFrame();
		PageManager.initializePages(frame);
		frame.chainPages(/*
						 * PageManager.getHomePage(), PageManager
						 * .getSimConfigPage(),
						 * PageManager.getModelSelectPage(),
						 * PageManager.getNetSelectPage(),
						 */

		PageManager.getMeshConfigPage(), PageManager.getSimulationPage());

		SplashScreen splash = new SplashScreen(frame);
		splash.start();
	}
}
