import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashSet;

public class Puzzle8 extends Node<Puzzle8>{


  private int[][] values;
  private Puzzle8 goal;
  private int dim ;



  public Puzzle8(Puzzle8 parent, double pathCost, Puzzle8 goal, String action, int[][] values){
    super(parent, pathCost, action);

    this.values = values;
    this.dim = values.length;
    this.goal = goal;
    calculateHash();
    if(goal == null){
      this.heuristic = 0;
    }
    else{
      calculateHeuristicCost();
    }

  }


  protected boolean isComplete(){
    return hash.equals(goal.getHash());
  }

  public Puzzle8(Puzzle8 parent, double pathCost, Puzzle8 goal,String action, String s, int dim){
    super(parent, pathCost, action);

    this.goal = goal;
    values = new int[dim][dim];
    String[] data = s.split(" ");
		for(int i = 0 ; i < data.length; i++){
			int j = i/dim;
			int k = i % dim;
			values[j][k] = Integer.parseInt(data[i]);
		}
    this.dim = dim;

    calculateHash();
    if(goal == null){
      this.heuristic = 0;
    }
    else{
      calculateHeuristicCost();
    }

  }

  protected void calculateHash(){
    String s = "";
		int index = 0;
		for(int i = 0; i < dim; i++){
			for( int j = 0; j < dim; j++){
				s += Integer.toString(values[i][j]) + "|";
			}
		}
		hash = s;
  }

  public int[][] copy ()
	{
    int dim = values.length;
		int [][] child = new int[dim][dim];
		for(int i = 0; i < dim; i++)
		child[i] = Arrays.copyOf(values[i],dim);
		return child;
	}

  public int get(int i, int j){
		return values[i][j];
	}


  //generates the possible neighbors to the node
  public ArrayList<Puzzle8> neighbors(){
    ArrayList<Puzzle8> possibleMoves = new ArrayList<>();

	//find zero in parent board
	int[] coors = getCoor(0);
	int emptyIndexI = coors[0];
	int emptyIndexJ = coors[1];
	if(emptyIndexI+1 <dim)
	{
    Puzzle8 n = new Puzzle8(this,pathCost +1,goal,"Down",copy());
		n.swap(emptyIndexI, emptyIndexJ, emptyIndexI+1, emptyIndexJ);
		possibleMoves.add(n);
	}
	if(emptyIndexI-1 >=0)
	{

		Puzzle8 n = new Puzzle8(this,pathCost +1,goal,"Up",copy());
		n.swap(emptyIndexI, emptyIndexJ, emptyIndexI-1, emptyIndexJ);

		possibleMoves.add(n);
	}
	if(emptyIndexJ+1 <dim)
	{
		Puzzle8 n = new Puzzle8(this,pathCost +1,goal,"Right",copy());
		n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ+1);
		possibleMoves.add(n);
	}
	if(emptyIndexJ-1 >=0)
	{
		Puzzle8 n = new Puzzle8(this,pathCost +1,goal,"Left",copy());
		n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ-1);
		possibleMoves.add(n);
	}
	return possibleMoves;
  }

  //calculates whether the node is solvable or not if needed
  public boolean solvable(){
    int sum = 0;
	for(int i = 0; i < (dim*dim); i++){
		int f_i = i/dim;
		int f_j = i % dim;
		for(int j = i + 1; j < (dim*dim); j++){
			int s_i = j/dim;
			int s_j = j % dim;
			if(values[s_i][s_j] != 0 && values[f_i][f_j] > values[s_i][s_j]){
				sum++;
			}
		}
	}
	return (sum % 2 == 0);
  }

  //calculates the heuristic
  public void calculateHeuristicCost(){
    heuristic = pathCost + ManhattanDistance();
  }

  //returns the sum of total steps needed to match a passed state
	public int ManhattanDistance(){
		int sum = 0;
		for(int i = 0; i < dim; i++){
			for(int j = 0 ; j < dim; j++){
				int[] acoors = goal.getCoor(values[i][j]);
				sum += Math.abs(i - acoors[0]) + Math.abs(j - acoors[1]);
			}
		}
		return sum;
	}

  //returns the coors of a value in the puzzle
	public int[] getCoor(int v){
		int[] coor = new int[2];
		for(int i = 0; i < dim; i++)
		{
			for (int j = 0; j < dim; j++)
			{
				if(values[i][j] == v)
				{
					coor[0] = i;
					coor[1] = j;
					break;
				}
			}
		}
		return coor;
	}

  //swaps values at different coordinates ; recalculates the hash
	public void swap(int fi, int fj, int si, int sj){
		int t = values[fi][fj];
		values[fi][fj] = values[si][sj];
		values[si][sj] = t;
		calculateHash();
	}




}
