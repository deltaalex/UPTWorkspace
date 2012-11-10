/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexanru Apr 28, 2012 3:49:05 PM </copyright>
 */
package upt.twitter.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import twitter4j.Tweet;

/**
 * Class used to store and load tweets and keep a track of any new data.
 */
public class StorageManager
{
	private final String DBRoot = "database/";
	private final String LogExtension = ".log";
	private final String NL = System.getProperty("line.separator");

	private final Map<String, Vector<String>> database;

	public StorageManager()
	{
		database = loadToMemory();
	}

	public int updateTweets(String tag, List<Tweet> tweets)
	{
		int newTweets = 0;

		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DBRoot + tag + LogExtension, true),
				"UTF-16"));

			// update databse and write to file
			if (database.containsKey(tag))
			{
				Vector<String> storedTweets = database.get(tag);

				for (Tweet tweet: tweets)
				{
					String parsedTweet = "@" + tweet.getFromUser() + " - " + tweet.getText();
					if (parsedTweet.contains("\n"))
					{
						parsedTweet = parsedTweet.substring(0, parsedTweet.indexOf("\n"));
					}

					if (!storedTweets.contains(parsedTweet))
					{
						storedTweets.add(parsedTweet);
						writer.append(parsedTweet);
						writer.append(NL);
						newTweets++;
					}
				}
			}
			// create new entry and file
			else
			{
				Vector<String> parsedTweets = new Vector<String>();
				for (Tweet tweet: tweets)
				{
					String parsedTweet = "@" + tweet.getFromUser() + " - " + tweet.getText();
					if (parsedTweet.contains("\n"))
					{
						parsedTweet = parsedTweet.substring(0, parsedTweet.indexOf("\n"));
					}

					parsedTweets.add(parsedTweet);
					writer.append(parsedTweet);
					writer.append(NL);
					newTweets++;
				}

				database.put(tag, parsedTweets);

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (IOException ignore)
				{
					/* ignore */
				}
			}
		}

		return newTweets;
	}

	public int getTweetCount(String tag)
	{
		if (database.containsKey(tag))
		{
			return database.get(tag).size();
		}
		else
		{
			return 0;
		}
	}

	private HashMap<String, Vector<String>> loadToMemory()
	{
		HashMap<String, Vector<String>> database = new HashMap<String, Vector<String>>();

		if (new File(DBRoot).exists() && new File(DBRoot).isDirectory())
		{
			File[] logs = new File(DBRoot).listFiles();

			for (File log: logs)
			{
				BufferedReader reader = null;
				try
				{
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(log), "UTF-16"));

					Vector<String> tweets = new Vector<String>();
					String tweet;
					while ((tweet = reader.readLine()) != null)
					{
						if (!tweets.contains(tweet))
						{
							tweets.add(tweet);
						}
					}

					database.put(log.getName().substring(0, log.getName().lastIndexOf(".")), tweets);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.exit(1);
				}
				finally
				{
					if (reader != null)
					{
						try
						{
							reader.close();
						}
						catch (IOException ignore)
						{
							/*ingore*/
						}
					}
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("Database root folder not found : " + DBRoot);
		}

		return database;
	}
}
