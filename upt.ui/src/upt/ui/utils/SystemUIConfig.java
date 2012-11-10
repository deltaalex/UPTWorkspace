/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 19, 2011 3:16:50 PM </copyright>
 */
package upt.ui.utils;

public class SystemUIConfig
{
	/**
	 * Mainframe background image path
	 */
	public static final String MAIN_BG_PATH = "pictures/bg_black_orange_1.png";
	/**
	 * Application LOGO icon path. Visible when no tool-tips are shown.
	 */
	public static final String LOGO_PATH = "pictures/logo/SigmaLogo2.png";
	/**
	 * Stores the path to the folder in which a 'pictures' folder is found.
	 */
	public static final String PICTURES_FOLDER = "pictures";
	/**
	 * Stores the path to the folder in which a 'config' folder is found.
	 */
	public static final String CONFIG_FOLDER = "config";
	/**
	 * Stores the path to the folder in which a 'help' folder is found.
	 */
	public static final String HELP_FOLDER = CONFIG_FOLDER + System.getProperty("file.separator") + "help";
	/**
	 * Stores the path to the folder in which a 'credits' folder is found.
	 */
	public static final String CREDITS_FOLDER = CONFIG_FOLDER + System.getProperty("file.separator") + "credits";
}
