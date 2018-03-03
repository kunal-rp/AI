import java.util.ArrayList;
import java.util.Arrays;

public class Board extends State<Board>{

  public static final double WIN = Double.POSITIVE_INFINITY;
  public static final double LOSS = Double.NEGATIVE_INFINITY;

  final int DIM = 8;
  //0 == nothing, 1 == player, -1 == computer
  int[][] values;

  public Board(){
    values = new int[DIM][DIM];
    for(int i = 0; i < DIM; i++){
      for(int j = 0; j < DIM; i++){
        values[i][j] = 0;
      }
    }
    evaluate();
  }

  public Board(int[][] v){
    values = v;
    evaluate();
  }

  public int[][] getBoard(){
    return values;
  }

  //override
  protected void evaluate(){
    boolean good = false;
    //looks for 4 of one type horizontally

    for(int i = 0; i < (DIM); i++){
      for(int j = 0; j <= (DIM)/2; j++){
        if(values[i][j] != 0 && !good){
          if(values[i][j] == values[i][j+1] && values[i][j] == values[i][j+2] && values[i][j] == values[i][j+3]){
            if(values[i][j] == 1){
              good = true;
              evaluationValue =  WIN;
              utilityValue = WIN;
            }
            else if(values[i][j] == -1){
              good = true;
              evaluationValue =  LOSS;
              utilityValue = LOSS;
            }
          }
        }
      }
    }
    //looks for 4 of one type vertically

    for(int j = 0; j < (DIM); j++){
      for(int i = 0; i <= (DIM)/2; i++){
        if(values[i][j] != 0 && !good){
          if(values[i][j] == values[i+1][j] && values[i][j] == values[i+2][j] && values[i][j] == values[i+3][j]){
            if(values[i][j] == 1){
              good = true;
              evaluationValue =  WIN;
              utilityValue = WIN;
            }
            else if(values[i][j] == -1){
              good = true;
              evaluationValue =  LOSS;
              utilityValue = LOSS;
            }
          }
        }
      }
    }
    if(!good){
      evaluationValue = 1.0;
    }
  }

  //override
  //indicates when the state is a finished State
   protected boolean isFinishedState(){
    return (utilityValue == WIN || utilityValue == LOSS);
  }

  /*gets all children states
  @param turn : indicates whose turn it is; computer or player
  */
  protected ArrayList<Board> getChildren(boolean turn){
    int cv;
    if(turn){
      cv = 1;
    }else{
      cv = -1;
    }
    ArrayList<Board> t = new ArrayList<>();

    int[][] temp = copy();

    for(int i =0; i < DIM; i++){
      for(int j = 0; j < DIM; j++){
        if(temp[i][j] == 0){
          int[][] a = copy();
          a[i][j] = cv;
          Board b = new Board(a);
          int[] move = {i,j};
          b.setMove(move);
          t.add(b);
        }
      }
    }
    return t;
  }

  private int[][] copy() {
		int[][] child = new int[values.length][];
		for (int i = 0; i < values.length; i++)
			child[i] = Arrays.copyOf(values[i], values.length);
		return child;
	}

  public void printBoard(){
    for(int i =0; i < DIM; i++){
      for(int j = 0; j < DIM; j++){
        System.out.print(values[i][j] + " ");
      }
      System.out.println();
    }
  }


}
