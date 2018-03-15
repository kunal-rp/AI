import java.util.ArrayList;

public class ABP{

  Board initial;
  int depth;
  boolean max;

  //gets the initial board state, the max depth requested, and whether or not to perform max or min operation
  public ABP(Board initial, int depth, boolean max){
    this.initial = initial;
    this.depth = depth;
    this.max = max;
  }

  //calls this to start alpha beta pruning
  public Board initialRun(long time){
    return run(Double.MAX_VALUE,-Double.MAX_VALUE,System.currentTimeMillis() + (time*1000));
  }

  //requires alpha, beta, and end time
  public Board run(double alpha, double beta,long endTime){
    //is the initial Board a finished state? if so break and return the state
    //the utility value would have already been set to +- infinity
    if(initial.isFinishedState()){
      System.out.println(depth + " Finished State MEET : "+ initial.getUtilityValue() + " | "+ initial.getMove()[0] + " , "+initial.getMove()[1]);
      return initial;
    }
    //the state is a regular state
    else{
      //if he max depth is reached or the time runs out
      if( System.currentTimeMillis() == endTime ||depth == 0){
        //updates the current state's utility value to it's estimate value
        initial.setUtilityValue(initial.getEstimateValue());
        return initial;
      }

      //if max operation is performed
      if(max){
        //the best state that will be found from the children
        Board bestState = initial;
        //the current best value
        double bestVal = -Double.MAX_VALUE;
        //corresponds to the best state
        int[] bestMove = initial.getMove();

        //get the current state's children
        ArrayList<Board>children = initial.getChildren(true);
        for(Board c : children){
          //set up the alpha beta pruning for the current state
          ABP a = new ABP(c,depth - 1, false);
          //get the aplha beta pruning result of the current state
          Board res = a.run(alpha, beta, endTime);

          //get the resulting state's utility value
          Double value = res.getUtilityValue();
          //if the resulting state is the better than the saved one ; update best vars
          if(value > bestVal){
            bestVal = value;
            bestState = res;
            bestMove = res.getMove();
          }
          // pruning, do not continue
          else if (value >= beta) {
            break;
          }
          // set alpha to whatever variable is larger
          // between alpha max
          alpha = Math.max(alpha, value);
        }
        // set the moves for the best board found
        bestState.setMove(bestMove);
        return bestState;
      }
      //min level
      else{

        Board bestState = initial;
        double bestVal = Double.MAX_VALUE;
        int[] bestMove = initial.getMove();

        ArrayList<Board>children = initial.getChildren(false);
        for(Board c : children){
          ABP a = new ABP(c,depth - 1, true);
          Board res = a.run(alpha, beta, endTime);
          Double value = res.getUtilityValue();
          if(value < bestVal){
            bestVal = value;
            bestState = res;
            bestMove = res.getMove();
          }
          // Prune branches that do not lead to better choices
          if (value <= alpha) {
            break;
          }
          // set alpha to whatever variable is larger
          // between alpha max
          beta = Math.min(beta, value);
        }
        // set the moves for the best board found
        bestState.setMove(bestMove);
        return bestState;
      }
    }
  }
}
