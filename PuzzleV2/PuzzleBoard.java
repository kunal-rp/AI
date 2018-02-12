import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashSet;

public class PuzzleBoard {

	protected int[][] values = new int[3][3];
	protected PuzzleBoard parent = null;
	protected int pathCost;
	protected int hash = 0;
	protected int hurCost;
	protected PuzzleBoard goal;
	protected String action = "";


	//accepts the String value as input for the puzzle's values
	public PuzzleBoard(String s,PuzzleBoard par, PuzzleBoard go, int pc){
		String []data = s.split("");
		for(int i = 0 ; i < data.length; i++){
			int j = i/3;
			int k = i % 3;
			values[j][k] = Integer.parseInt(data[i]);
		}
		calculateHash();
		parent = par;
		goal = go;
		pathCost = pc;
	}

	//also accepts array of ints as values
	public PuzzleBoard(int[][] v,PuzzleBoard par, PuzzleBoard go, int pc){
		values = v;
		parent = par;
		goal = go;
		pathCost = pc;
		calculateHash();
	}

	//calculates the hach value of the puzzle
	public void calculateHash(){
		String s = "";
		int index = 0;
		for(int i = 0; i < 3; i++){
			for( int j = 0; j < 3; j++){
				s += Integer.toString(values[i][j]);
			}
		}
		hash = Integer.parseInt(s);
	}

	//action used to backtrack what steps need to be made
	public void setAction(String a){
		action = a;
	}

	public String getAction(){
		return action;
	}

	public int getHash(){
		return hash;
	}

	public PuzzleBoard getParent(){
		return parent;
	}


	public int getPathCost(){
		return pathCost;
	}

	public int[][] getBoard(){
		return values;
	}

	//sets the heuristic of the puzzle
	public void setHur(boolean b){
		if(b){
			hurCost = pathCost + misplacedTiles(goal);
		}
		else{
			hurCost = pathCost + ManhattanDistance(goal);
		}
	}


	public void printBoard(){
		System.out.println(Arrays.deepToString(values));
	}

	public void printDetails(){
		System.out.print(" Hash:"+hash);
		System.out.print(" Board :"+Arrays.deepToString(values));
		System.out.print(" Parent:"+parent);
		System.out.print(" PathCost:"+pathCost);
		System.out.println();

	}


	//returns the coors of a value in the puzzle
	public int[] getCoor(int v){
		int[] coor = new int[2];
		for(int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
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

	public int get(int i, int j){
		return values[i][j];
	}

	public void set(int i, int j, int v){
		values[i][j] = v;
	}


	//swaps values at different coordinates ; recalculates the hash
	public void swap(int fi, int fj, int si, int sj){
		int t = values[fi][fj];
		values[fi][fj] = values[si][sj];
		values[si][sj] = t;
		calculateHash();
	}

	//return if the function is solvable
	public boolean solvable(){
		int sum = 0;
		for(int i = 0; i < 9; i++){
			int f_i = i/3;
			int f_j = i % 3;
			for(int j = i + 1; j < 9; j++){
				int s_i = j/3;
				int s_j = j % 3;
				if(values[f_i][f_j] > values[s_i][s_j]){
					sum++;
				}
			}
		}
		return (sum % 2 == 0);
	}

	public int[][] copy ()
	{
		int [][] child = new int[3][3];
		for(int i = 0; i < 3; i++)
		child[i] = Arrays.copyOf(values[i],3);
		return child;
	}

	//gets all possible puzzles ; max 4 possible
	public ArrayList<PuzzleBoard> possible(){
		ArrayList<PuzzleBoard> possibleMoves = new ArrayList<>();
		HashSet<Integer> visited = new HashSet<>();
		//loop through every row
		for(int i = 0 ; i < 3; i++){
			//loop through every column
			for(int j = 0; j < 3; j++){
				int emptyIndexI = i;
				int emptyIndexJ = j;

				if(emptyIndexI+1 <3)
				{
					PuzzleBoard n = new PuzzleBoard(copy(),this,goal,pathCost+1);
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI+1, emptyIndexJ);
					n.setAction("Swap "+n.get(emptyIndexI, emptyIndexJ) + " & "+n.get(emptyIndexI+1, emptyIndexJ));
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexI-1 >=0)
				{

					PuzzleBoard n = new PuzzleBoard(copy(),this,goal,pathCost+1);
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI-1, emptyIndexJ);
					n.setAction("Swap "+n.get(emptyIndexI, emptyIndexJ) + " & "+n.get(emptyIndexI-1, emptyIndexJ));
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexJ+1 <3)
				{
					PuzzleBoard n = new PuzzleBoard(copy(),this,goal,pathCost+1);
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ+1);
					n.setAction("Swap "+n.get(emptyIndexI, emptyIndexJ) + " & "+n.get(emptyIndexI, emptyIndexJ+1));
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
				if(emptyIndexJ-1 >=0)
				{
					PuzzleBoard n = new PuzzleBoard(copy(),this,goal,pathCost+1);
					n.swap(emptyIndexI, emptyIndexJ, emptyIndexI, emptyIndexJ-1);
					n.setAction("Swap "+n.get(emptyIndexI, emptyIndexJ) + " & "+n.get(emptyIndexI, emptyIndexJ-1));
					if(!visited.contains(n.getHash())){
						possibleMoves.add(n);
						visited.add(n.getHash());
					}
				}
			}
		}
		return possibleMoves;
	}

	//returns the number of misplaces values comapred to a passed state
	public int misplacedTiles(PuzzleBoard p){
		int sum = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0 ; j < 3; j++){
				if(values[i][j] != p.get(i,j)){
					sum++;
				}
			}
		}
		return sum;
	}

	//returns the sum of total steps needed to match a passed state
	public int ManhattanDistance(PuzzleBoard p){
		int sum = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0 ; j < 3; j++){
				int[] acoors = p.getCoor(values[i][j]);
				sum += Math.abs(i - acoors[0]) + Math.abs(j - acoors[1]);
			}
		}
		return sum;
	}
}
