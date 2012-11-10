/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Sep 29, 2011 5:14:07 PM </copyright>
 */
package upt.social.ui;

import upt.ui.AbstractPage;
import upt.ui.MainFrame;

/**
 * Stores all application pages.
 */
public class PageManager
{
	private static AbstractPage homePage;
	private static AbstractPage simConfigPage;
	private static AbstractPage modelSelectPage;
	private static AbstractPage netSelectPage;
	private static AbstractPage meshConfigPage;
	private static AbstractPage ringConfigPage;
	private static AbstractPage simulationPage;

	public static void initializePages(MainFrame frame)
	{
		homePage = new PageHome(frame);
		simConfigPage = new PageSimConfiguration(frame);
		modelSelectPage = new PageModelSelection(frame);
		netSelectPage = new PageNetworkSelection(frame);
		meshConfigPage = new PageMeshConfiguration(frame);
		ringConfigPage = new PageRingConfiguration(frame);
		simulationPage = new PageSimulation(frame);
	}

	/**
	 * @return the home page (1st)
	 */
	public static AbstractPage getHomePage()
	{
		return homePage;
	}

	/**
	 * @return the model selection page (2nd)
	 */
	public static AbstractPage getModelSelectPage()
	{
		return modelSelectPage;
	}

	/**
	 * @return the network selection page (2nd)
	 */
	public static AbstractPage getNetSelectPage()
	{
		return netSelectPage;
	}

	/**
	 * @return the mesh configuration page (3rd-A)
	 */
	public static AbstractPage getMeshConfigPage()
	{
		return meshConfigPage;
	}

	/**
	 * @return the ring configuration page (3rd-B)
	 */
	public static AbstractPage getRingConfigPage()
	{
		return ringConfigPage;
	}

	/**
	 * @return the simulation configuration page (4th)
	 */
	public static AbstractPage getSimConfigPage()
	{
		return simConfigPage;
	}

	/**
	 * @return the simulation page (5th)
	 */
	public static AbstractPage getSimulationPage()
	{
		return simulationPage;
	}
}
