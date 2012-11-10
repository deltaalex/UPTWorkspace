package upt.io.log;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Used to log results in xml or text files.
 * 
 *         Creates a new output log
 * 
 * @param logDirectory
 *        - destination directory
 * @param logFileName
 *        - log name (without extension)
 * @param outputFormat
 *        - output format (text, XML)
 * @param overwrite
 *        - Whether to overwrite existing logs or append a time stamp to new
 *        ones
 */
public abstract class Log
{
	/**
	 * Path of logging directory and log file
	 */
	private String pathDir, pathFile;
	/**
	 * Determines if old logs are overwritten or if new logs append a time stamp
	 */
	private boolean overwrite;
	/**
	 * The output format
	 */
	private LOGFORMAT format;
	/**
	 * Concatenation of all results in the format: "header|value". <br>
	 * The length of this table must be %2
	 */
	protected String[] results;

	public Log(String logDirectory, String logFileName, LOGFORMAT outputFormat, boolean overwrite)
	{
		this.pathDir = logDirectory;
		this.format = outputFormat;
		this.overwrite = overwrite;

		this.pathFile = logFileName;
		if (logFileName.toLowerCase().endsWith(".txt"))
		{
			this.pathFile = logFileName.substring(0, logFileName.indexOf(".txt"));
		}
		if (logFileName.toLowerCase().endsWith(".xml"))
		{
			this.pathFile = logFileName.substring(0, logFileName.indexOf(".xml"));
		}
	}

	/**
	 * Save the current results to the log file <br>
	 * The results must be in the following format: results[0] = header0,
	 * results[1] = value0 , results[2] = header1 ... <br>
	 * Thus, the size of results must be even.
	 * 
	 * @param results
	 */
	public void setResults(String[] results)
	{
		if (results.length % 2 != 0)
		{
			throw new IllegalArgumentException("Results size must be even. Expected format is : header|value|...");
		}

		this.results = results;
	}

	/**
	 * Save the current results to the log file <br>
	 * The results must be in the following format:
	 * header1|value1|header2|value2|... <br>
	 * Thus, the size of results must be even.
	 * 
	 * @param results
	 */
	public void setResults(String results)
	{
		if (results.split("[|]+").length % 2 != 0)
		{
			throw new IllegalArgumentException("Results size must be even. Expected format is : header|value|...");
		}

		this.results = results.split("[|]+");
	}

	public void appendResults(String[] results)
	{
		if (results.length % 2 != 0)
		{
			throw new IllegalArgumentException("Results size must be even. Expected format is : header|value|...");
		}

		List<String> res = Arrays.asList(this.results);
		res.addAll(Arrays.asList(results));

		this.results = (String[]) res.toArray();
	}

	/**
	 * Saves the current results to the disk
	 */
	public abstract void saveLog();

	protected File getLogFile()
	{

		try
		{
			File directory = new File(pathDir);

			if (!directory.exists())
			{
				directory.mkdirs();
			}

			File log = new File(directory + "/" + pathFile + (format.equals(LOGFORMAT.TEXT) ? ".txt" : ".xml"));

			if (!overwrite)
			{
				log = new File(directory + "/" + pathFile + "_" + new Date().getTime()
					+ (format.equals(LOGFORMAT.TEXT) ? ".txt" : ".xml"));
			}

			return log;

		}
		catch (Exception e)
		{
			throw new IllegalAccessError("The given log path is not accesible : " + pathDir + "/" + pathFile);
		}
	}
}
