package upt.twitter.product;

import upt.twitter.model.SimConfig;
import upt.twitter.model.SimData;
import upt.twitter.model.engine.Location;

public class TestConsole
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

		System.out.println("Started...");
		SimData.addTwitterTag("Obama", Location.CITY.get("Berlin"));

		// System.out.println("Started...");
		// SimData.addTwitterTag("Romney", Location.CITY.get("NewYork"));
		System.out.println("Done.");
	}
}
