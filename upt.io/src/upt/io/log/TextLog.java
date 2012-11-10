package upt.io.log;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Alexander Used to log results in text format
 */
public class TextLog extends Log
{

	public TextLog(String logDirectory, String logFileName, boolean overwrite)
	{
		super(logDirectory, logFileName, LOGFORMAT.TEXT, overwrite);
	}

	@Override
	public void saveLog()
	{
		File log = getLogFile();
		try
		{
			PrintWriter pw = new PrintWriter(log);

			Calendar c = GregorianCalendar.getInstance();
			pw.println("Log created " + c.get(Calendar.DAY_OF_WEEK) + ", " + c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.MONTH) + " " + c.get(Calendar.YEAR) + " at " + c.get(Calendar.HOUR_OF_DAY) + ":"
				+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND));
			pw.println();

			for (int i = 0; i < results.length - 1; i += 2)
			{
				pw.println(results[i] + " : " + results[i + 1]);
			}

			pw.print("END");
			pw.close();
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Cannot write to log file");
		}
	}
}
