package DiGraph_A5;

public class Edge {
    public long edgeID;
    public String sourceLabel;
    public String destLabel;
    public long weight;
    public String edgeLabel;
    
    public Edge(long id, String slabel, String dlabel, long weight, String elabel) {
    	this.edgeID = id;
    	this.sourceLabel = slabel;
    	this.destLabel = dlabel;
    	this.weight = weight;
    	this.edgeLabel = elabel;
    }
	
	
}
