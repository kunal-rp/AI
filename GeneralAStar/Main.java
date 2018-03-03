import java.lang.Integer;

public class Main{

  public static void main(String []args)
  {
    Puzzle8Swap g = new Puzzle8Swap(null, 0,null, "START",args[0],Integer.parseInt(args[2]));
    Puzzle8Swap i = new Puzzle8Swap(null, 0,   g, "START",args[1],Integer.parseInt(args[2]));
    AStar as2 = new AStar(g, i);

    System.out.println(as2.run());
  }
}
