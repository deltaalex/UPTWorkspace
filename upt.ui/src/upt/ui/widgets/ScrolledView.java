package upt.ui.widgets;

import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import upt.ui.factory.ColorFactory;

/**
 * Contains a scrollable panel. <br>
 */
public class ScrolledView extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static enum SCROLL
	{
		HORIZONTAL, VERTICAL, BOTH
	}

	private JPanel scrollPanel;
	private Scrollbar scrollX, scrollY;
	private static final int ScrollThickness = 20;

	public ScrolledView(final JPanel mapPanel, double x, double y, final double width, final double height,
		SCROLL scroll)
	{
		setLocation((int) x, (int) y);
		setSize((int) width, (int) height);
		setLayout(null);
		setBorder(LineBorder.createBlackLineBorder());

		setBackground(ColorFactory.getItemBackcolor());
		setForeground(ColorFactory.getItemForecolor());

		scrollPanel = new JPanel();
		scrollPanel.setLocation(1, 1);
		scrollPanel.setLayout(null);
		scrollPanel.setBackground(ColorFactory.getItemBackcolor());
		scrollPanel.setForeground(ColorFactory.getItemForecolor());
		if (scroll.equals(SCROLL.HORIZONTAL))
		{
			scrollPanel.setSize((int) width - 2, (int) height - 2 - ScrollThickness);
		}
		else if (scroll.equals(SCROLL.VERTICAL))
		{
			scrollPanel.setSize((int) width - 2 - ScrollThickness, (int) height - 2);
		}
		else if (scroll.equals(SCROLL.BOTH))
		{
			scrollPanel.setSize((int) width - 2 - ScrollThickness, (int) height - 2 - ScrollThickness);
		}

		add(scrollPanel);

		scrollPanel.add(mapPanel);
		mapPanel.setLocation(0, 0);
		mapPanel.setSize(scrollPanel.getSize());

		if (scroll.equals(SCROLL.BOTH) || scroll.equals(SCROLL.HORIZONTAL))
		{
			scrollX = new Scrollbar(Scrollbar.HORIZONTAL, 0, (100 * ((int) width - 2) / mapPanel.getSize().width) - 1,
				0, 100);
			scrollX.setLocation(0, (int) (height - ScrollThickness));
			scrollX.setSize((int) (width - ScrollThickness), 20);
			scrollX.setBackground(ColorFactory.IVORY);
			scrollX.addAdjustmentListener(new AdjustmentListener()
			{
				public void adjustmentValueChanged(AdjustmentEvent e)
				{
					Scrollbar sb = (Scrollbar) e.getSource();
					sb.setVisibleAmount(Math.max(10, (int) (100 * (width - 2) / mapPanel.getSize().width) - 1));
					double dx = 1.0 * sb.getValue() / sb.getMaximum();
					int windowX = (int) (1.0 * mapPanel.getSize().width * dx);
					mapPanel.setLocation(-windowX, mapPanel.getLocation().y);
				}
			});
			add(scrollX);
		}

		if (scroll.equals(SCROLL.BOTH) || scroll.equals(SCROLL.VERTICAL))
		{
			scrollY = new Scrollbar(Scrollbar.VERTICAL, 0, (100 * ((int) height - 2) / mapPanel.getSize().height) - 1,
				0, 100);
			scrollY.setLocation((int) (width - ScrollThickness), 0);
			scrollY.setSize(20, (int) (height - ScrollThickness));
			scrollY.setBackground(ColorFactory.IVORY);
			scrollY.addAdjustmentListener(new AdjustmentListener()
			{
				public void adjustmentValueChanged(AdjustmentEvent e)
				{
					Scrollbar sb = (Scrollbar) e.getSource();
					sb.setVisibleAmount(Math.max(10, (int) (100 * (height - 2) / mapPanel.getSize().height) - 1));
					double dy = 1.0 * sb.getValue() / sb.getMaximum();
					int windowY = (int) (1.0 * mapPanel.getSize().height * dy);
					mapPanel.setLocation(mapPanel.getLocation().x, -windowY);
				}
			});
			add(scrollY);
		}

	}
}