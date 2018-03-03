import java.util.Random;

public class RR{

  private Board best;
  private int dim;
  private int iter;
  private int lim;


  /*
  @param d : the number of queens on Board
  @param i : the number of restarts we conduct per each run
  @param ti : the max time allowed per each run
  */
  public RR(int d,int i, int li){
    dim = d;
    iter = i;
    lim = li;
  }

  public String generateRandomBoard(){
    String res = "";
    for(int i = 0; i < dim; i++){
      Random r = new Random();
      res += (r.nextInt(dim)+1) + " ";
    }
    return res;
  }

  public Board run(){
    best = null;

    for(int i = 0; i < iter; i++ ){
      Board b = new Board(generateRandomBoard());
      for(int j = 0; j < lim; j++){
        Board bestChild = b.getBestChild();
        if(b.getFitness() == 0 || b.getFitness() < bestChild.getFitness()){
          //System.out.println("break");
          break;
        }
        //System.out.println("B : "+ b.getFitness() + " | BestChild : "+bestChild.getFitness());
        b = bestChild;
      }
      if(best == null || b.getFitness() < best.getFitness()){
        best = b;
      }
      else if(best.getFitness() < b.getFitness()){
        break;
      }
    }
    //System.out.println("Best :"+ best.toString()+ " | Cost : "+best.getFitness());

    return best;
  }

}
