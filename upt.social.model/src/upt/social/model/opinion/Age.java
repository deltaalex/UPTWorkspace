/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander Oct 10, 2011 4:50:32 PM </copyright>
 */
package upt.social.model.opinion;

import java.util.Random;

/**
 * Stores calendar data and education details
 */
public class Age
{
	public static final int MAX_AGE = 80;

	private int days, months, years;
	private boolean dead;
	private double education;

	private Random rand;

	public Age(Random rand)
	{
		this(rand, 1, 1, rand.nextInt(25) + 1);
	}

	public Age(Random rand, int days, int months, int years)
	{
		this.days = days;
		this.months = months;
		this.years = years;
		this.rand = rand;
		dead = false;

		// initialize education for random age persons
		education = Education.NONE;
		if (years >= Education.BAC_AGE)
		{
			if (Education.isBac(rand))
			{
				education = Education.BAC;
				if (years >= Education.BACHELOR_AGE)
				{
					if (Education.isBachelor(rand))
					{
						education = Education.BACHELOR;
						if (years >= Education.MASTER_AGE)
						{
							if (Education.isMaster(rand))
							{
								education = Education.MASTER;
								if (years >= Education.PHD_AGE)
								{
									if (Education.isPhD(rand))
									{
										education = Education.PHD;
										if (years >= Education.ACAD_AGE)
										{
											if (Education.isAcad(rand))
											{
												education = Education.ACAD;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Increases age by <1> day.
	 */
	public void increaseAge()
	{
		if (!dead)
		{
			days++;
			if (days > 30)
			{
				days = 1;
				months++;
				if (months > 12)
				{
					months = 1;
					years++;
					updateEducation();
				}
			}

			if (years >= MAX_AGE)
			{
				years = MAX_AGE;
				dead = true;
			}
		}
	}

	/**
	 * Get a persons education level
	 * 
	 * @return Education
	 */
	public double getEducation()
	{
		if (education == 0)
		{
			System.err.println("error");
		}
		return education;
	}

	/**
	 * Get the age of a node rounded to years.
	 * 
	 * @return Years
	 */
	public int getYears()
	{
		return years;
	}

	/**
	 * Get the age of the node relative to the maximum age of a node.
	 * 
	 * @return Percent [0, 1]
	 */
	public double getAgeAsPercent()
	{
		return 1.0 * years / MAX_AGE;
	}

	/**
	 * Get the education level of a node as a percent relative to the maximum
	 * education of a node.
	 * 
	 * @return educational level [0, 1]
	 */
	public double getEducationAsPercent()
	{
		return 1.0 * education / Education.MAX_EDUCATION;
	}

	private void updateEducation()
	{
		if (days == 1 && months == 1 && years == Education.ACAD_AGE && education == Education.PHD)
		{
			if (Education.isAcad(rand))
			{
				education = Education.ACAD;
			}
		}

		if (days == 1 && months == 1 && years == Education.PHD_AGE && education == Education.MASTER)
		{
			if (Education.isPhD(rand))
			{
				education = Education.PHD;
			}
		}

		if (days == 1 && months == 1 && years == Education.MASTER_AGE && education == Education.BACHELOR)
		{
			if (Education.isMaster(rand))
			{
				education = Education.MASTER;
			}
		}

		if (days == 1 && months == 1 && years == Education.BACHELOR_AGE && education == Education.BAC)
		{
			if (Education.isBachelor(rand))
			{
				education = Education.BACHELOR;
			}
		}

		if (days == 1 && months == 1 && years == Education.BAC_AGE)
		{
			if (Education.isBac(rand))
			{
				education = Education.BAC;
			}
		}

		if (education == 0)
		{
			education = Education.NONE;
		}
	}
}
