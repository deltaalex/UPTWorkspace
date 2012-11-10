/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 2, 2012 12:51:05 PM </copyright>
 */
package upt.twitter.model.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import twitter4j.GeoLocation;
import upt.twitter.model.SimConfig;

/**
 * Class encapsulating geographical location of tweets
 * 
 */
public class Location
{
	/**
	 * Stores latitude and longitude data
	 */
	private GeoLocation coordinates;
	/**
	 * Stores the radius of the area around the given coordinates
	 */
	private double radius;
	/**
	 * Stores the measurement units for the radius. <br>
	 * Can be "mi" or "km"
	 */
	private String unit;

	private static final String CityFile = "cities.txt";
	private static final String CountryFile = "countries.txt";

	public static final Map<String, Location> CITY;
	public static final Map<String, Location> COUNTRY;

	static
	{
		CITY = new HashMap<String, Location>();
		COUNTRY = new HashMap<String, Location>();

		loadFromFile(CITY, CityFile);
		loadFromFile(COUNTRY, CountryFile);
	}

	public Location(double latitude, double longitude, double radius)
	{
		coordinates = new GeoLocation(latitude, longitude);
		this.radius = radius;
		unit = "km";
	}

	/**
	 * @return the coordinates
	 */
	public GeoLocation getCoordinates()
	{
		return coordinates;
	}

	/**
	 * @return the radius
	 */
	public double getRadius()
	{
		return radius;
	}

	/**
	 * @return the unit
	 */
	public String getUnit()
	{
		return unit;
	}

	private static void loadFromFile(Map<String, Location> map, String fileName)
	{
		try
		{
			Scanner scan = new Scanner(new File(SimConfig.LOCATIONS_FOLDER + "/" + fileName));

			while (scan.hasNext())
			{
				String[] tokens = scan.nextLine().split(" ");
				try
				{
					Location location = new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]),
						Double.parseDouble(tokens[3]));

					map.put(tokens[0], location);
				}
				catch (NumberFormatException f)
				{
					System.err.println("Error ! Default location" + tokens[0] + " has some invalid coodinates.");
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Error ! The default locations folder was not found. No locations loaded.");
		}
	}
}