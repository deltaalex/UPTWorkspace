/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 6, 2011 2:12:02 PM </copyright>
 */
package upt.noc.simulation.threading;

import java.util.Vector;

import upt.noc.state.NocReader;
import upt.noc.state.NocWriter;
import upt.noc.structures.ArchitectureConfiguration;
import upt.noc.structures.NocResult;


public class NocWorker extends Thread
{
	private NocResult[] results;
	private int id;
	private int m, n, count;
	private int numRows, numColumns;
	private ArchitectureConfiguration trafConfig;
	private Vector<Integer> longLinks;
	private Vector<Integer> _longLinks;
	private NocReader reader;
	private NocWriter writer;

	public NocWorker(NocResult[] results, ArchitectureConfiguration trafficConfig,
		Vector<Integer> longLinks, NocReader reader, NocWriter writer, int id, int numRows, int numCols,
		int m, int n, int count)
	{
		this.results = results;
		this.trafConfig = trafficConfig;
		this.writer = writer;
		this.reader = reader;
		this.id = id;
		this.numRows = numRows;
		this.numColumns = numCols;
		this.m = m;
		this.n = n;
		this.count = count;
		this.longLinks = longLinks;
	}

	@Override
	public void run()
	{
		_longLinks = new Vector<Integer>();
		for (int link: longLinks)
		{
			_longLinks.add(link);
		}

		_longLinks.add(m);
		_longLinks.add(n);

		// Write architecture config file with routing info
		// to the architecture_file
		StringBuilder consoleBuffer = new StringBuilder();
		ArchitectureConfiguration archConfig = writer.generateArchConfiguration(numRows, numColumns, count,
			_longLinks, consoleBuffer);

		// Evaluate current configuration
		// Free packet delay of the current configuration
		double curr_rate = reader.getFreePacketDelay(numRows, numColumns, trafConfig, archConfig,
			new StringBuilder());

		results[id] = new NocResult(curr_rate, m, n);
	}
}
