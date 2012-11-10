package upt.social.ui.graphics;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import upt.social.state.SimConfig;
import upt.ui.factory.ColorFactory;

/**
 * Draws a custom sized bar which is proportionally filled with two custom
 * colors, represented by percentages. <br>
 * <i>Example: 75% vs. 25% => GGGGGGRR , G = green , R = red </i>
 * 
 */
public class PercenatageBar extends JPanel implements IPercentageDisplay
{
	private static final long serialVersionUID = 1L;

	private JLabel leftLabel, rightLabel;
	private int w, h;

	public PercenatageBar(Color leftColor, Color rightColor, double x, double y, double width, double height,
		double initialPercentage)
	{
		this.w = (int) width;
		this.h = (int) height;

		setLayout(null);
		setBackground(ColorFactory.getItemBackcolor());
		setLocation((int) x, (int) y);
		setSize(w, h);
		// setBorder(LineBorder.createGrayLineBorder());

		leftLabel = new JLabel((int) (100 * initialPercentage) + "%");
		leftLabel.setSize((int) (w * initialPercentage), h);
		leftLabel.setLocation(0, 0);
		leftLabel.setBackground(leftColor);
		leftLabel.setForeground(ColorFactory.WHITE);
		leftLabel.setBorder(LineBorder.createGrayLineBorder());
		leftLabel.setOpaque(true);
		leftLabel.setHorizontalAlignment(SwingConstants.CENTER);

		rightLabel = new JLabel((100 - (int) (100 * initialPercentage)) + "%");
		rightLabel.setSize(w - leftLabel.getSize().width, h);
		rightLabel.setLocation(leftLabel.getSize().width, 0);
		rightLabel.setBackground(rightColor);
		rightLabel.setForeground(ColorFactory.WHITE);
		rightLabel.setBorder(LineBorder.createGrayLineBorder());
		rightLabel.setOpaque(true);
		rightLabel.setHorizontalAlignment(SwingConstants.CENTER);

		add(leftLabel);
		add(rightLabel);

		// redraw(0.5);
	}

	private void redrawSingle(double percent)
	{
		double leftPercentage = (int) (1000 * percent) / 10.0;
		double rightPercentage = (int) (10 * (100.0 - leftPercentage)) / 10.0;

		leftLabel.setText(leftPercentage + " %");
		rightLabel.setText(rightPercentage + " %");

		leftLabel.setSize((int) (w * percent), h);
		rightLabel.setLocation((int) (w * percent), 0);
		rightLabel.setSize(w - leftLabel.getSize().width, h);

		repaint();
	}

	/**
	 * The PercentageBar is meant for one status only. Multiple percents are not
	 * supported, so only the first percentage is used. <br>
	 * 
	 * @See {@link #redraw(double)}
	 */
	public void redraw(double... percents)
	{
		if (percents.length > 0)
		{
			redrawSingle(percents[0]);
		}
	}

	@Override
	public void reset()
	{
		redrawSingle(SimConfig.ProFactor);
	}

	@Override
	public String getTabTitle()
	{
		return "Percentage Bar";
	}

	@Override
	@Deprecated
	public void toggleChart(int index)
	{
		/*nothing to hide*/
	}

	@Override
	public void highlightMessage(String message, int chartIndex)
	{
		/*nowhere to show it*/
	}
}