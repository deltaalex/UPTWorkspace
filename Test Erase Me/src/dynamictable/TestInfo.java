package dynamictable;


public class TestInfo implements Comparable<TestInfo> {

	public String name; 	// e.g. PerfProjectCreationR40Test
	
	public String compileTime;		// not used
	public String executionTime; 	// CPU Time
	public String performance; 	// Used Java Heap	
	
	@Override
	public String toString() {
		return name + " - " + executionTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TestInfo)
		{
			return name.equals(((TestInfo)(obj)).name);
		}
		return false;
	}
	
	@Override
	public int compareTo(TestInfo o) {
		return name.compareTo(o.name);
	}
}
