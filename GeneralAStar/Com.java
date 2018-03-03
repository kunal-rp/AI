import java.util.Comparator;

public class Com implements Comparator<Node> {


	//compare object needed for the PriorityQueue
	@Override
	public int compare(Node p0, Node p1) {
		if(p0.getHeuristicCost() < p1.getHeuristicCost()){
			return -1;
		}
		return 1;
	}

}
