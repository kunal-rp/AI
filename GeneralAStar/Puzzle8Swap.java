import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashSet;

public class Puzzle8Swap extends Node<Puzzle8Swap>{

  private int[][] values;
  private Puzzle8Swap goal;
  private int dim ;

  public Puzzle8Swap(Puzzle8Swap parent, double pathCost, Puzzle8Swap goal, String action, int[][] values){
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

  public Puzzle8Swap(Puzzle8Swap parent, double pathCost, Puzzle8Swap goal,String action, String s, int dim){
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
				s += Integer.toString(values[i][j])+ "|";
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
  public ArrayList<Puzzle8Swap> neighbors(){
    ArrayList<Puzzle8Swap> possibleMoves = new ArrayList<>();
		HashSet<String> visited = new HashSet<>();
		//loop through every row
		for(int i = 0 ; i < dim; i++){
			//loop through every column
			for(int j = 0; j < dim; j++){
				int emptyIndexI = i;
				int emptyIndexJ = j;

				if(emptyIndexI+1 <dim)
				{
          String act = "Swap "+get(emptyIndexI, emptyIndexJ) + " & "+get(emptyIndexI+1, emptyIndexJ);
					Puzzle8Swap n = new Puzzle8Swap(this,pathCost +1,goal,act,copy());
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI+1, emptyIndexJ);
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexI-1 >=0)
				{
          String act = ("Swap "+get(emptyIndexI, emptyIndexJ) + " & "+get(emptyIndexI-1, emptyIndexJ));
          Puzzle8Swap n = new Puzzle8Swap(this,pathCost +1,goal,act,copy());
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI-1, emptyIndexJ);
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexJ+1 <dim)
				{
          String act = ("Swap "+get(emptyIndexI, emptyIndexJ) + " & "+get(emptyIndexI, emptyIndexJ+1));
					Puzzle8Swap n = new Puzzle8Swap(this,pathCost +1,goal,act,copy());
          n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ+1);
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexJ-1 >=0)
				{
          String act = ("Swap "+get(emptyIndexI, emptyIndexJ) + " & "+get(emptyIndexI, emptyIndexJ-1));
					Puzzle8Swap n = new Puzzle8Swap(this,pathCost +1,goal,act,copy());
          n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ-1);
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
			}
		}
		return possibleMoves;
  }

  //calculates whether the node is solvable or not if needed
  public boolean solvable(){
    return true;
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
