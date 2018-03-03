import java.util.Comparator;

public class Com implements Comparator<PuzzleBoard> {


	//compare object needed for the PriorityQueue
	@Override
	public int compare(PuzzleBoard p0, PuzzleBoard p1) {
		if(p0.hurCost < p1.hurCost){
			return -1;
		}
		return 1;
	}

}
