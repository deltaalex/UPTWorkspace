/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 13, 2011 10:09:08 AM </copyright>
 */
package upt.social.model.complex;

import upt.social.model.INode;

/**
 * Detailing interface for analog nodes
 */
public interface IComplexNode extends INode
{
	/**
	 * Get node opinion modification factor. A node's opinion is modified by
	 * this value.
	 * 
	 * @return The opinion of the node
	 */
	public double getOpinion();

	/**
	 * Get the age of a node as a percent relative to the maximum age of a node.
	 * 
	 * @return age value [0, 1]
	 */
	public double getAgeAsPercent();

	/**
	 * Get the education level of a node as a percent relative to the maximum
	 * education of a node.
	 * 
	 * @return educational level [0, 1]
	 */
	public double getEducationAsPercent();
}
