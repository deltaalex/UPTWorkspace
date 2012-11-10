package upt.noc.structures;

import java.util.List;
import java.util.Vector;

public class ArchitectureConfiguration
{
	private Vector<NocNode> nodes;

	public ArchitectureConfiguration()
	{
		nodes = new Vector<NocNode>();
	}

	public void addNode(NocNode node)
	{
		nodes.add(node);
	}

	public List<NocNode> getNodes()
	{
		return nodes;
	}

	public void clearNodes()
	{
		nodes.clear();
	}

	public NocLink getNocLink(int fromIndex, int toIndex)
	{
		return new NocLink(nodes.get(fromIndex), nodes.get(toIndex));
	}
}
