/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexandru Apr 28, 2012 3:58:35 PM </copyright>
 */
package upt.twitter.model.engine;

import java.util.Calendar;
import java.util.GregorianCalendar;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import upt.twitter.model.StorageManager;
import upt.twitter.model.timing.Counter;
import upt.twitter.model.timing.Cronometer;

/**
 * Launches queries on twitter for preset tags
 * 
 */
public class TwitterEngine
{
	private StorageManager storage;
	private Twitter twitter;

	public TwitterEngine(StorageManager storage)
	{
		if (storage == null)
		{
			throw new IllegalArgumentException("The database instance may not be null");
		}
		this.storage = storage;
		this.twitter = new TwitterFactory().getInstance();
	}

	public void searchAllTweetsInRange(String searchTag, Location location, double radius, String unit)
	{
		String queryString = "";
		Cronometer timer = new Cronometer();

		for (int day = -6; day <= 0; ++day)
		{
			Counter counter = new Counter();
			try
			{
				Query query = new Query(searchTag);
				query.setRpp(100);
				queryString = "Tag \"" + searchTag + "\" - ";
				// RO
				// query.setGeoCode(new GeoLocation(45.9, 24.8), 400, "km");
				// Paris
				// query.setGeoCode(new GeoLocation(48.856944, 2.351944), 10,
				// "km");
				// London
				// query.setGeoCode(new GeoLocation(51.508611, -0.128333), 10,
				// "km");
				// Berlin
				// query.setGeoCode(new GeoLocation(52.524167, 13.406111), 15,
				// "km");
				// New York
				// query.setGeoCode(new GeoLocation(40.746389, -73.990833), 8,
				// "km");

				// set location if defined
				if (location != null)
				{
					double _radius = radius <= 0 ? location.getRadius() : radius;
					String _unit = unit == null ? location.getUnit() : unit;

					query.setGeoCode(location.getCoordinates(), _radius, _unit);
					queryString += "location : " + location.getCoordinates().toString() + ", " + _radius + " " + _unit
						+ " radius";
				}

				// set time interval: [today-7 -> today]
				query.setSince(getToday(day));
				query.setUntil(getToday(day + 1));

				queryString += "\n\t" + "Date interval : " + query.getSince() + " to " + query.getUntil();

				timer.start();
				int i = 1;
				while (true)
				{
					query.setPage(i);
					QueryResult result = null;
					int count = 0;

					try
					{
						result = twitter.search(query);
						count = result.getTweets().size();
					}
					catch (TwitterException ee)
					{
						System.err.println("Too many tweets on " + getToday(day));
						count = 0;
					}

					counter.increment(count);

					if (count == 0)
					{
						break;
					}
					i++;
				}
				// System.out.println("[DEBUG] Done in " +
				// timer.stop(TimeUnit.MILLISECONDS) + " ms.");
				System.out.println(getToday(day) + " : Found a total of " + counter.getCount() + " tweets for tag \""
					+ searchTag + "\" over " + (i - 1) + " pages.");

			}
			catch (Exception te)
			{
				te.printStackTrace();
				System.out.println("Failed to search tweets: " + te.getMessage());
				System.exit(-1);
			}
		}

		System.out.println("\nFinished query: " + queryString);
	}

	private String getToday(int deltaDays)
	{
		Calendar c = GregorianCalendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, deltaDays);

		String date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);

		return date;
	}
}
