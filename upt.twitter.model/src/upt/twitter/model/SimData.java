package upt.twitter.model;

import upt.twitter.model.engine.Location;
import upt.twitter.model.engine.TwitterEngine;

public class SimData
{
	/**
	 * The Twitter search engine instance
	 */
	public static final TwitterEngine engine;
	/**
	 * The database instance
	 */
	private final static StorageManager storage;

	static
	{
		storage = new StorageManager();
		engine = new TwitterEngine(storage);
	}

	public static void addTwitterTag(String newTwitterTag, Location location)
	{
		addTwitterTag(newTwitterTag, location, 0, null);
	}

	public static void addTwitterTag(String newTwitterTag, Location location, double radius)
	{
		addTwitterTag(newTwitterTag, location, radius, null);
	}

	public static void addTwitterTag(String newTwitterTag, Location location, double radius, String unit)
	{
		if (engine == null)
		{
			throw new IllegalStateException("The search engine is not running.");
		}

		engine.searchAllTweetsInRange(newTwitterTag, location, radius, unit);
	}
}
