/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 3:16:50 PM </copyright>
 */
package upt.social.ui;

public class UIConfig
{
	// image paths
	public static final String ICON_EMPTY = "pictures/icons/_empty.png";
	public static final String ICON_NEW = "pictures/icons/new.png";
	public static final String ICON_OPEN = "pictures/icons/open.png";
	public static final String ICON_HELP = "pictures/icons/help.png";
	public static final String ICON_CONFIG = "pictures/icons/settings.png";
	public static final String ICON_DISCRETE = "pictures/icons/discrete.png";
	public static final String ICON_ANALOG = "pictures/icons/analog.png";
	public static final String ICON_COMPLEX = "pictures/icons/complex.png";
	public static final String ICON_MESH = "pictures/icons/mesh.png";
	public static final String ICON_MESHWRAP = "pictures/icons/meshwrapped.png";
	public static final String ICON_HYPER2D = "pictures/icons/hypercube2d.png";
	public static final String ICON_RANDOM = "pictures/icons/random.png";
	public static final String ICON_SCALEFREE = "pictures/icons/scalefree.png";
	public static final String ICON_RING = "pictures/icons/ring.png";
	public static final String ICON_RING_LAYERED = "pictures/icons/ringlayered.png";
	public static final String ICON_SWORLD = "pictures/icons/graph.png";
	public static final String ICON_SCROLL_X = "pictures/icons/scrollx.png";
	public static final String ICON_SCROLL_Y = "pictures/icons/scrolly.png";

	// page titles
	public static final String TITLE_HOME = "Welcome";
	public static final String TITLE_SIMCONFIG = "Simulation Configuration";
	public static final String TITLE_MODELSELECTION = "Social Model Selection";
	public static final String TITLE_NETSELECTION = "Network Selection";
	public static final String TITLE_NETCONFIG = "Network Configuration";
	public static final String TITLE_SIMULATION = "Simulation";

	// button texts
	public static final String BTN_HOME_NEW = "New Project";
	public static final String BTN_HOME_OPEN = "Open Project";
	public static final String BTN_HELP = "Help";
	public static final String BTN_ABOUT = "About";

	public static final String BTN_MODELSELECT_DISCRETE = "Discrete Model";
	public static final String BTN_MODELSELECT_ANALOG = "Analog Model";
	public static final String BTN_MODELSELECT_COMPLEX = "Complex Model";

	public static final String BTN_NETSELECT_MESH = "Mesh Network";
	public static final String BTN_NETSELECT_WRAPMESH = "Wrap-around Mesh";
	public static final String BTN_NETSELECT_HYPER2D = "Hyper-Cube";
	public static final String BTN_NETSELECT_RING = "Ring";
	public static final String BTN_NETSELECT_RING_LAYERED = "Layered Ring";
	public static final String BTN_NETSELECT_RANDOM = "Random Mesh";
	public static final String BTN_NETSELECT_SCALEFREE = "Scale-Free";
	public static final String BTN_NETSELECT_WSDD = "WSDD";

	public static final String BTN_MESH_GENERATE = "Generate Network";
	public static final String BTN_MESH_WIDTH = "Size";
	// public static final String BTN_MESH_HEIGHT = "Height";
	public static final String BTN_MESH_SWORLD = "Small-World ?";
	public static final String BTN_MESH_DENSITY = "Density";
	public static final String BTN_MESH_SAGENTS = "Stubborn agents";
	public static final String BTN_MESH_AAGENTS = "Absurd agents";
	public static final String BTN_MESH_NAGENTS = "Normal agents";

	public static final String BTN_RING_NEIGHBOURS = "Neighbours";

	public static final String BTN_SIMCONFIG_CPU = "CPU Usage";
	public static final String BTN_SIMCONFIG_THREADS = "Threads/Timer";
	public static final String BTN_SIMCONFIG_MINSLEEP = "Min Sleep";
	public static final String BTN_SIMCONFIG_MAXSLEEP = "Max Sleep";
	public static final String BTN_SIMCONFIG_CLOCK = "Clock";
	public static final String BTN_SIMCONFIG_MONITOR_INTERVAL = "Monitoring";
	public static final String BTN_SIMCONFIG_SIMLENGTH = "Duration";
	public static final String BTN_SIMCONFIG_OK = "Accept";
	public static final String BTN_SIMCONFIG_DEFAULT = "Set Defaults";

	// tool-tip texts
	public static final String TIP_HOME = "Choose one of the options below to start with";
	public static final String TIP_HOME_NEW = "Start a new simulation from scratch";
	public static final String TIP_HOME_OPEN = "<HTML>Open a previously run simulation<BR>with all the settings and results<HTML>";
	public static final String TIP_HOME_HELP = "Open the user manual";
	public static final String TIP_HOME_ABOUT = "Show credits";

	public static final String TIP_MODELSELECT = "Select the social model to be used in the simulation";
	public static final String TIP_MODELSELECT_DISCRETE = "<HTML>Simplest model.<br>Any normal agent completely takes over a friend's opinion without any personal decision.</HTML>";
	public static final String TIP_MODELSELECT_ANALOG = "<HTML>Variation of the discrete model.<br>Any normal agent takes over a friend's opinion with a specific trust factor, while keeping a proportion of his prior decision.</HTML>";
	public static final String TIP_MODELSELECT_COMPLEX = "<HTML>A human-based model.<br>Agents evolve with age, education, build charisma and have pertient opinions, making it a slower evolving and a more conservative model.</HTML>";

	public static final String TIP_NETSELECT = "Select the network architecture";
	public static final String TIP_NETSELECT_MESH = "<HTML>Choose a mesh type network.<br>Each node can only connect vertically and horizonatlly, having a maximum of 8 neighbors.<br>Useful for theoretical simulations.</HTML>";
	public static final String TIP_NETSELECT_WRAPMESH = "<HTML>Choose a mesh with wrap-around type network.<br>Each node can connect vertically and horizonatlly, while extremities are also coneccted.<br>Useful for theoretical simulations.</HTML>";
	public static final String TIP_NETSELECT_HYPER2D = "<HTML>Choose a 3D hyper cube type network.<br> The 3D cube consists of 4 x 2D meshes with nodes connected with their correspondents in each mesh.</HTML>";
	public static final String TIP_NETSELECT_RING = "<HTML>Choose a ring type network.<br>Each node is connected to two adjacent nodes found on the ring.</HTML>";
	public static final String TIP_NETSELECT_RING_LAYERED = "<HTML>Choose a layered ring type network.<br>Each node is connected to two adjacent nodes found on his ring.<br>Additionally, each node is linked to his correspondnet on the inner and outer rings.</HTML>";
	public static final String TIP_NETSELECT_RANDOM = "<HTML>Choose a random graph network.<br>The topology is structured as a mesh, but all links are random.</HTML>";
	public static final String TIP_NETSELECT_SCALEFREE = "<HTML>Choose a scale free graph network.<br>The topology is structured as a mesh, but all links present a power law distribution.</HTML>";
	public static final String TIP_NETSELECT_WSDD = "<HTML>Choose a small world graph network with degree distribution.<br>The topology is structured as a mesh, but nodes are clustered and links present a power law distribution.</HTML>";

	public static final String TIP_MESH = "Customize the mesh network";
	public static final String TIP_MESH_GENERATE = "Create a new social network using the current settings";
	public static final String TIP_MESH_WIDTH = "<HTML>Set the width of the mesh network.<br>The width is expressed in number of agents.</HTML>";
	public static final String TIP_MESH_HEIGHT = "<HTML>Set the height of the mesh network.<br>The height is expressed in number of agents.</HTML>";
	public static final String TIP_MESH_SAGENTS = "Set the number of stubborn agents that will be placed into the network";
	public static final String TIP_MESH_AAGENTS = "Set the number of absurd agents that will be placed into the network";
	public static final String TIP_MESH_NAGENTS = "The remaining number of normal agents";
	public static final String TIP_MESH_SWORLD = "<HTML>Enable the usage of ling range links.<br>Random nodes will have one or more links removed and, instead, a new link will be added to a random distant node.<br>This topology creates a more real-world social network.</HTML>";
	public static final String TIP_MESH_DENSITY = "<HTML>Represents the density of long ranged linkes between agents.<br>The closer to 1, the more distant friends an agent can have.</HTML>";

	public static final String TIP_RING = "Customize the ring network";
	public static final String TIP_RING_NEIGHBOURS = "<HTML>Set the number of neighbours for a node.<br>A node will be connected to this many nodes to the left and right of his position.</HTML>";

	public static final String TIP_SIMCONFIG = "Customize the simulation settings";
	public static final String TIP_SIMCONFIG_CPU = "<HTML> Value expressing percentage [1,100].<br>Defines the relative CPU usage that the simulation will try to keep as maximum threshold.</HTML>";
	public static final String TIP_SIMCONFIG_THREADS = "<HTML>Set the number of threads used for simulation.<br>A pool of threads is managed for processing data.</HTML>";
	public static final String TIP_SIMCONFIG_MINSLEEP = "<HTML>Defines the minimum time an agent must keep his opinion.<br>The sleep value is expressed in clock intervals.</HTML>";
	public static final String TIP_SIMCONFIG_MAXSLEEP = "<HTML>Defines the maximum time an agent can keep his opinion.<br>The sleep value is expressed in clock intervals.</HTML>";
	public static final String TIP_SIMCONFIG_CLOCK = "<HTML>Defines the simulation pace in milliseconds.<br>A lower clock simulates a faster evolving simulation but uses more CPU.</HTML>";
	public static final String TIP_SIMCONFIG_MONITOR_INTERVAL = "<HTML>Defines the rate at which the population is polled.<br>The interval is expressed in milliseconds and must be greater than the clock rate.</HTML>";
	public static final String TIP_SIMCONFIG_SIMLENGTH = "<HTML>Sets the length of the simulation.<br>The length is expressed in monitoring intervals.</HTML>";
	public static final String TIP_SIMCONFIG_OK = "Accept settings and continue to next page.";
	public static final String TIP_SIMCONFIG_DEFAULT = "Reset all settings to their defaults.";

	public static final String TIP_SIMULATION = "Start simulating with the current settings";

	public static final String TIP_HELP_HOME = "Some useful information about SocialSim";

	public static void loadPicturePaths(String path)
	{

	}

	public static void loadButtonTexts(String path)
	{

	}

	public static void loadTooltipTexts(String path)
	{

	}

}
