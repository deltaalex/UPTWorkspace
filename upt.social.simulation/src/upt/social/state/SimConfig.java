package upt.social.state;

public class SimConfig
{
	/**
	 * Creates a parameterized network
	 * 
	 * @param N
	 *        Number of <i>normal</i> nodes
	 * @param S
	 *        Number of stubborn agents
	 * @param density
	 *        Double value between (0,1). Density of links between nodes, scaled
	 *        to the network size. A value closer to 0 means less links.
	 * @param proFactor
	 *        Double value between (0,1). Determines the percentage of pro
	 *        agents from the stubborn agent population.
	 * @param testAndFix
	 *        Specify whether to check the connectivity of the graph. <br>
	 *        <blockquote> <i> Warning ! </i> A lower density has a higher
	 *        chance of generating a non-connected graph.</blockquote>
	 * @return The new network
	 */

	// network settings
	/**
	 * Number of <i>normal</i> nodes. <br>
	 * Total is NA+SA+AA.
	 */
	public static int NA = 1000;
	/**
	 * Number of stubborn agents. <br>
	 * Total is NA+SA+AA.
	 */
	public static int SA = 0;
	/**
	 * Number of absurd agents. <br>
	 * Total is NA+SA+AA.
	 */
	public static int AA = 0;

	/**
	 * If a mesh network is used, determines the width of the mesh.
	 */
	public static int MESH_WIDTH = 100;
	/**
	 * If a mesh network is used, determines the height of the mesh.
	 */
	public static int MESH_HEIGHT = 100;
	/**
	 * Sets the maximum width & height of a mesh.
	 */
	public static final int MESH_MAX_SIZE = 600;
	/**
	 * Sets the maximum number of nodes in a ring network
	 */
	public static final int RING_MAX_SIZE = 1000;
	/**
	 * Double value between (0,1). Represents the percentage of pro agents from
	 * the stubborn agent population. Is set indirectly by the user.
	 */
	public static double ProFactor = 0.5;
	/**
	 * Double value between (0,1). The closer to 1, the more long range links
	 * are inserted. A factor of 0.5 will add SQRT(W*H) links to the network,
	 * thus each node will have a long range links with the probability of
	 * 1/SQRT(W*H).
	 */
	public static double LongRangeDensity = 0.5;
	/**
	 * Defines the number of neighbors, to the left and right, a ring node will
	 * be connected to.
	 */
	public static int RingNeighbours = 1;
	/**
	 * String combination denoting the types of links used between nodes
	 */
	public static String LINKS = "HVDPDS";

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
	 * Minimum time that a node must sleep before changing his opinion again. <br>
	 * Expressed in simulation ticks (clock cycles).
	 */
	public static int MinSleep = 1;
	/**
	 * Maximum time that a node is allowed to sleep before changing his opinion
	 * again. <br>
	 * Expressed in simulation ticks (clock cycles).
	 */
	public static int MaxSleep = 50;

	// simulation settings
	/**
	 * Time interval at which population polling is done. Expressed in
	 * <b>ms</b>.
	 */
	public static int MonitorInterval = 500;
	/**
	 * Defines whether simple agents agents are enabled
	 */
	public static SIMMODEL SocialModel = SIMMODEL.DISCETE;
	/**
	 * Defines which network topology is being used
	 */
	public static TOPOLOGY NetworkModel = TOPOLOGY.MESH;
	/**
	 * Defines whether long range links are enabled
	 */
	public static boolean isSmallWorld = false;
	/**
	 * Determines whether a new society has a randomly formed opinion at the
	 * start of the simulation, or is set into the NONE state.
	 */
	public static boolean isOpinionated = false;
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
