/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 20, 2011 5:58:38 PM </copyright>
 */
package upt.social.model;

/**
 * Defines the generic types nodes can have.
 * 
 */
public enum NODETYPE
{
	NORMAL,
	/**
	 * Agents who start with an initial state and NEVER change it
	 */
	STUBBORN,
	/**
	 * Agents who always take the inverse opinion
	 */
	ABSURD
}
