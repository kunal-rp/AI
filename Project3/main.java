public class main{
  public static void main(String args[]){


    //testing
    int [][] v = {{0,0,1,0,0,0,0,0},
                  {0,0,1,0,0,0,0,0},
                  {0,0,1,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0},
                  {0,0,0,0,0,0,0,0}};
    Board b = new Board(v);

    System.out.println(b.getEstimateValue());

    ABP a = new ABP(b, 5, true);
    Board res = a.initialRun(5);
    int[] move = res.getMove();
    res.printBoard();



  }
}
