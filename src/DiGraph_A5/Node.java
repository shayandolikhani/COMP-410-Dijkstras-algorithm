package DiGraph_A5;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Node {
	
	public long nodeID;
	public String label;
	public boolean handled = false;
	public long dist;
	public HashSet<String> destinations;
	public HashSet<String> sources;
	public List<Edge> EdgeList; 

	
	
	public Node (long nodeID, String label) {
		this.nodeID = nodeID;
		this.label = label;
		this.destinations = new HashSet<String>();
		this.sources = new HashSet<String>();
		this.EdgeList = new ArrayList<Edge>();
	
	}
	
	
	
	
}
