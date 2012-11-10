package dynamictable;

import java.util.Vector;

public class BuildInfo {

	public String name; 	// e.g. CESSAR Build 18.04.2011
	
	public Vector<TestInfo> tests;
	
	public BuildInfo()
	{
		tests = new Vector<TestInfo>();
	}
}
