package upt.social.ui.graphics;

import upt.ui.utils.ITabbable;

public interface IPercentageDisplay extends ITabbable
{
	/**
	 * Fills the shape with color(s). Coloring is done depending on the
	 * percentages. A graph is drawn for each given percentage.
	 */
	public void redraw(double... percents);

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

	/**
	 * Interchanges the visibility of a given chart.
	 * 
	 * @param index
	 *        Index of the chart in the chart area
	 */
	public void toggleChart(int index);

	/**
	 * Highlights the given message on the chart area
	 * 
	 * @param message
	 *        Message to be displayed in a red-colored tag on the chart
	 * @param chartIndex
	 *        Index of chart over which the message should be displayed
	 */
	public void highlightMessage(String message, int chartIndex);

}