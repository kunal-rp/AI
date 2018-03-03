import java.lang.Integer;

public class Main2{

  public static void main(String []args)
  {
    Puzzle8 g = new Puzzle8(null, 0,null, "START",args[0],Integer.parseInt(args[2]));
    Puzzle8 i = new Puzzle8(null, 0,   g, "START",args[1],Integer.parseInt(args[2]));
    AStar as2 = new AStar(g, i);

    System.out.println(as2.run());
  }
}
