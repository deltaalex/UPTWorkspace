package upt.twitter.model;

public class SimConfig
{
	/**
	 * Stores the path to the folder in which a 'locations' folder is found.
	 */
	public static final String LOCATIONS_FOLDER = "config" + System.getProperty("file.separator") + "locations";

	// threading settings
	/**
	 * Integer value expressing percentage [1,100]. Defines the relative CPU
	 * usage that the simulation will try to keep as maximum threshold.
	 */
	public static int CpuUsage = 50;
	/**
	 * Number of threads in the simulation thread pool.
	 */
	public static int PoolThreads = 8;
	/**
	 * // simulation settings /** Time interval at which population polling is
	 * done. Expressed in <b>ms</b>.
	 */
	public static int MonitorInterval = 500;

	/**
	 * Determines if the simulation results are logged to a file
	 */
	public static boolean EnableLogResults = false;
	/**
	 * Directory path for the output log file. <br>
	 * User only if <b>EnableLogResults</b> is enabled.
	 */
	public static String LogRootPath = System.getProperty("user.home") + "/Desktop/logs";
	public static String LogFileName = "socialsim";
}
