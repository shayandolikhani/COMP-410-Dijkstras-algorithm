package DiGraph_A5;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;


public class DiGraph implements DiGraphInterface {
	
  public List<Node> nodeList; // this to iterate through one at a time
  public HashMap<String, Node> nodes; 
  public HashSet<Long> nodeIDs;
  public HashSet<Long> edgeIDs;
  public int numEdges;

  // in here go all your data and methods for the graph

  public DiGraph() { // default constructor
    // explicitly include this
    // we need to have the default constructor
    // if you then write others, this one will still be there
	  nodes = new HashMap<String, Node>();
	  nodeIDs = new HashSet<Long>();
	  edgeIDs = new HashSet<Long>();
	  nodeList = new ArrayList<Node>();

  }

@Override
public boolean addNode(long idNum, String label) {
	if (idNum < 0) {
		return false;
	}
	
	if (nodes.containsKey(label) || nodeIDs.contains(idNum)) {
		return false;
	}
	
	Node node = new Node(idNum, label);
	nodes.put(label, node);
	nodeIDs.add(idNum);
	nodeList.add(node);
	
	return true;
	
	
}

@Override
public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
	if (idNum < 0 || edgeIDs.contains(idNum)) {
		return false;
	}
	
	if (!(nodes.containsKey(sLabel)) || !(nodes.containsKey(dLabel))) {
		return false;
	}
	
	Node sourceNode = nodes.get(sLabel);
	if (sourceNode.destinations.contains(dLabel)) {
		return false;
	}
	
	
	Edge edge = new Edge (idNum, sLabel, dLabel, weight, eLabel);
	edgeIDs.add(idNum);
	
	
	
	sourceNode.EdgeList.add(edge);
	sourceNode.destinations.add(dLabel);
	
	Node destinationNode = nodes.get(dLabel);
	destinationNode.sources.add(sLabel);
	
	numEdges++;
	return true;
	
	
}

@Override
public boolean delNode(String label) {
	if (!nodes.containsKey(label)) {
		return false;
	}
	
	Node node = nodes.get(label);
	// for each edge in edge list, if edgeID is in graph EdgeID list, remove from idlist and decrement
	for (Edge edge: node.EdgeList) {
		edgeIDs.remove(edge.edgeID);
		this.numEdges--;
	}
	
	node.EdgeList.removeAll(node.EdgeList);

	
	//remove in edges
	// for each of the in edges, find their source node, delete all the out edges that connect to this node
	//remove EdgeIDs as well
	// decrement num edges if match found 
	for (String source: node.sources) {
		Node sourceNode = nodes.get(source);
		for (Edge edge: sourceNode.EdgeList) {
			if (edge.destLabel == label) {
				sourceNode.destinations.remove(label);
				this.edgeIDs.remove(edge.edgeID);
				this.numEdges--;
				sourceNode.EdgeList.remove(edge);
			}
		}
	}
	
	
	nodeIDs.remove(node.nodeID);
	nodeList.remove(node);
	nodes.remove(label);
	
	
	return true;
	
}

@Override
public boolean delEdge(String sLabel, String dLabel) {
	Node sourceNode = nodes.get(sLabel);
	Node destinationNode = nodes.get(dLabel);
	
	//if there is an edge between the two, remove and return true
	if (sourceNode != null && destinationNode != null) {
	if (sourceNode.destinations.contains(dLabel)) {
		for (Edge edge: sourceNode.EdgeList) {
			if (edge.destLabel == dLabel) {
				sourceNode.destinations.remove(dLabel);
				this.edgeIDs.remove(edge.edgeID);
				sourceNode.EdgeList.remove(edge);
				destinationNode.sources.remove(sLabel);
				numEdges--;
				return true;
			}
		}
	}
	}
	return false;
}

@Override
public long numNodes() {
	return nodes.size();
}

@Override
public long numEdges() {
	return numEdges;
}

@Override
public ShortestPathInfo[] shortestPath(String label) {
	List<ShortestPathInfo> shortestpaths = new ArrayList<ShortestPathInfo>();
	Edge edge;
	Node node;
	EntryPair entrypair;
	MinBinHeap queue = new MinBinHeap();
	long pathLength = 0;
	Node startNode = nodes.get(label);
	startNode.dist = 0;
	EntryPair startpair = new EntryPair(startNode, pathLength);
	queue.insert(startpair);
	
	while (queue.size() > 0) {
		entrypair = queue.getMin();
		queue.delMin();
		node = entrypair.getValue();
		if (nodes.get(node.label).handled == true) {
			continue;
		}
		
		nodes.get(node.label).handled = true;
		ShortestPathInfo newshortPath = new ShortestPathInfo(node.label, node.dist);
		shortestpaths.add(newshortPath);
		
		//get first out edge 
		edge = nodes.get(node.label).EdgeList.get(0);
		ListIterator<Edge> iterator = nodes.get(node.label).EdgeList.listIterator();
		
		while (edge != null) {
			if (!nodes.get(edge.destLabel).handled) {
				pathLength = nodes.get(node.label).dist + edge.weight;
				if (pathLength < nodes.get(edge.destLabel).dist) {
					nodes.get(edge.destLabel).dist = pathLength;
					EntryPair newEntry = new EntryPair(nodes.get(edge.destLabel), pathLength);
					queue.insert(newEntry);
				}
			}
			if (iterator.hasNext()) {
				edge = iterator.next();
			} else {
				break;
			}
			// edge = next edge in the list
		}
	}
	
	ShortestPathInfo[] shortestpaths2 = new ShortestPathInfo[shortestpaths.size()];
	shortestpaths2 = shortestpaths.toArray(shortestpaths2);
	return shortestpaths2;
}

  
  // rest of your code to implement the various operations
}