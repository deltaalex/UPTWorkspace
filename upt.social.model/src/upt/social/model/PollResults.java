package upt.social.model;

import java.util.Vector;

import upt.social.model.helpers.MathHelper;

/**
 * Encapsulates poll results : pro, against, total voters, total population. <br>
 * <i> Example : 1000 voters, 800 total votes (80%) : 600 vs. 200 : 75% vs. 25%
 * : 60% out of total</i>
 */
public class PollResults
{
	// timing
	private long day, elapsedTime;

	private int posVotes, negVotes;
	private int totalVotes, populationSize;
	private int modifications;
	private double kurtosis;
	// tolerances
	private double tolerance, staticTolerance, specificTolerance, dynamicTolerance;

	public PollResults(Vector<INode> nodes, long realTimeMs, long simulationDay)
	{
		Vector<Double> states = new Vector<Double>();
		day = simulationDay;
		elapsedTime = realTimeMs;
		posVotes = 0;
		negVotes = 0;
		populationSize = nodes.size();
		totalVotes = 0;
		modifications = 0;
		tolerance = 0;
		staticTolerance = 0;
		specificTolerance = 0;
		dynamicTolerance = 0;

		for (INode node: nodes)
		{
			// get vote statistics
			if (node.getState().equals(STATE.YES))
			{
				posVotes++;
			}
			else if (node.getState().equals(STATE.NO))
			{
				negVotes++;
			}

			// get node statistics
			if (node.isModified())
			{
				modifications++;
			}

			tolerance += node.getTolerance().getTolerance();
			staticTolerance += node.getTolerance().getStaticTolerance();
			specificTolerance += node.getTolerance().getSpecificTolerance();
			dynamicTolerance += node.getTolerance().getDynamicTolerance();

			states.add(node.getStateAsPercentage());
		}
		totalVotes = posVotes + negVotes;
		kurtosis = MathHelper.kurtosis(states);
		tolerance /= nodes.size();
		staticTolerance /= nodes.size();
		specificTolerance /= nodes.size();
		dynamicTolerance /= nodes.size();

		// System.out.println(staticTolerance + ", " + specificTolerance + ", "
		// + dynamicTolerance);
	}

	public int getPositiveVotes()
	{
		return posVotes;
	}

	public int getNegativeVotes()
	{
		return negVotes;
	}

	public int getTotalVotes()
	{
		return totalVotes;
	}

	public int getPopulationSize()
	{
		return populationSize;
	}

	public double getPositivePercentage()
	{
		return 1.0 * posVotes / (posVotes + negVotes);
	}

	public double getNegativePercentage()
	{
		return 1.0 * negVotes / (posVotes + negVotes);
	}

	public double getVotersPercentage()
	{
		if (populationSize != 0)
		{
			return 1.0 * totalVotes / populationSize;
		}
		else
		{
			return 0;
		}
	}

	// XXX
	public double getKurtosis()
	{
		// System.out.println(kurtosis);
		return kurtosis / 100.0 + 0.5;
	}

	public double getTolerance()
	{
		return tolerance;
	}

	public double getStaticTolerance()
	{
		return staticTolerance;
	}

	public double getSpecificTolerance()
	{
		return specificTolerance;
	}

	public double getDynamicTolerance()
	{
		return dynamicTolerance;
	}

	public int getOpinionChangers()
	{
		return modifications;
	}

	public double getOpinionChangePercentage()
	{
		if (populationSize != 0)
		{
			return 1.0 * modifications / populationSize;
		}
		return 0;
	}

	public long getSimulationDays()
	{
		return day;
	}

	public long getRealTime()
	{
		return elapsedTime;
	}

	// Example : 1000 voters, 800 total votes (80%) : 600 vs. 200 : 75% vs. 25%
	// : 60% out of total | delta 3%
	@Override
	public String toString()
	{
		return "Population|" + populationSize + "|Voters|" + totalVotes + "|Voted|"
			+ (100 * totalVotes / populationSize) + "%" + "|Positive|" + posVotes + "|Negative|" + negVotes
			+ "|PositivePercentage|" + 100 * getPositivePercentage() + "%" + "|NegativePercentage|"
			+ 100 * getNegativePercentage() + "%" + "|OverallPositivePercentage|" + (100.0 * posVotes / populationSize)
			+ "%" + "|OpinionChange|" + (100 * getOpinionChangePercentage()) + "%" + "|Tolerance|"
			+ (100 * getTolerance()) + "%" + "|SimulationDays|" + day + "|ElapsedTimeMs|" + elapsedTime;
	}
}
