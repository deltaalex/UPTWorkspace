package upt.noc.structures;

public class NocResult
{
	private double currRate;
	private int best0, best1;

	public NocResult(double currRate, int best0, int best1)
	{
		this.currRate = currRate;
		this.best0 = best0;
		this.best1 = best1;
	}

	public double getCurrRate()
	{
		return currRate;
	}

	public int getBest0()
	{
		return best0;
	}

	public int getBest1()
	{
		return best1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return best0 + "->" + best1 + " : " + currRate;
	}
}
