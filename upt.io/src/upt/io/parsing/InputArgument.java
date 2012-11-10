/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 2, 2012 1:03:09 PM </copyright>
 */
package upt.io.parsing;

/**
 * Model of a java command line input argument <br>
 * Example: -d num_of_days <br>
 * Stores id, format, description
 * 
 * @author Alexander
 */
public class InputArgument
{
	private String id;
	private String value;

	public InputArgument(String id, String value)
	{
		this.id = id;
		this.value = value;
	}

	/**
	 * @return The value of the argument
	 */
	public String getValue()
	{
		return value;
	}
}
