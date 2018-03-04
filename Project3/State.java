import java.util.ArrayList;


public abstract class State<I extends State>{

  double evaluationValue = 0;;
  double utilityValue = 0;
  //move made to get to state
  int[] move;

  //returns the evaluation value of the state
  abstract protected void evaluate();

  //indicates when the state is a finished State
  abstract protected boolean isFinishedState();

  /*
  sets the utilityvalue to the passed value
  */
  protected void setUtilityValue(double v){
     utilityValue = v;
  }

  protected double getUtilityValue(){
    return utilityValue;
  }
  protected double getEstimateValue(){
    return evaluationValue;
  }

  //gets the best move possible for this state
   protected int[] getMove(){
    return move;
  }

  //gets the best move possible for this state
   protected void setMove(int[] m){
    move  = m;
  }


  /*gets all children states
  @param turn : indicates whose turn it is; computer or player
  */
  abstract protected ArrayList<I> getChildren(boolean turn);







}
