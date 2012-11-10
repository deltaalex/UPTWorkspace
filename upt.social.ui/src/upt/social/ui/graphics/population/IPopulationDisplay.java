package upt.social.ui.graphics.population;

import java.util.Vector;

import upt.social.model.INode;
import upt.ui.utils.ITabbable;

public interface IPopulationDisplay extends ITabbable
{
	/**
	 * Colors a population display according to the given node states. <br>
	 */
	public void redraw(Vector<INode> nodes);

	/**
	 * Toggles the on/off visible status of the shape.
	 * 
	 * @param visible
	 *        Boolean value
	 */
	public void setVisible(boolean visible);

	/**
	 * Clears away any data, maintaining only GUI properties.
	 */
	public void reset();
}