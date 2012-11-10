/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Feb 17, 2012 11:37:41 AM </copyright>
 */
package upt.social.model.analog;

/**
 * @author Alexander Define the interaction model between agents <br>
 * <br>
 *         - CONFIDENCE_ONLY : each nodes uses only its individual confidence in
 *         each node to change its opinion. <br>
 *         - CREDIBILITY_ONLY : each nodes uses only the friend's credibility to
 *         change its opinion. <br>
 *         - TOLERANCE_ONLY : each nodes uses only its individual tolerance to
 *         change its opinion. <br>
 *         - CONFIDENCE_TOLERANCE : each nodes uses both its individual
 *         tolerance and confidence to change its opinion <br>
 *         - CREDIBILITY_TOLERANCE : each nodes uses its tolerance and the
 *         friend's credibility to change its opinion <br>
 */
public enum CommModel
{
	CONFIDENCE_ONLY, TOLERANCE_ONLY, CREDIBILITY_ONLY, CONFIDENCE_TOLERANCE, CREDIBILITY_TOLERANCE
}
