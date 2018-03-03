import java.util.Comparator;

public class Com implements Comparator<Board>{

	@Override
	public int compare(Board b0, Board b1) {
		if (b0.getFitness() < b1.getFitness())
			return -1;
		else if(b0.getFitness() > b1.getFitness())
			return 1;
		else
			return 0;
	}

}
