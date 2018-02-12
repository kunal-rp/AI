import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Handle{

  PuzzleBoard goal;
  PuzzleBoard initial;
  PriorityQueue<PuzzleBoard> frontier ;
  HashSet<Integer> visited;


  public Handle(PuzzleBoard go, PuzzleBoard i){
    goal  =go;
    initial = i;

  }

  //generates the random puzzles thay will be used
  public ArrayList<PuzzleBoard> generateRandomPuzzles(){
    ArrayList<PuzzleBoard> randomPuzzles = new ArrayList<>();
    for(int i = 0; i < 150; i++){
  		Random r = new Random();
      PuzzleBoard p = initial;

  		for(int j = 0; j < r.nextInt(26) + 2; j++){
  			ArrayList<PuzzleBoard> possibities = p.possible();
  			p = possibities.get(r.nextInt(possibities.size()));
  		}
      randomPuzzles.add(p);
    }
    return randomPuzzles;

	}


  /*
  runs the a* search
  @param b : indicates what type of heuristic function will be used
  */
  public String[] run(boolean b){
    String[] result = null;
    //initializ vars and adds the initial puzzle to the queue
    frontier = new PriorityQueue<PuzzleBoard>(new Com());
    visited = new HashSet<>();
    frontier.add(initial);
    String seq = "";
    long startTime = System.nanoTime() ;


    while(!frontier.isEmpty()){
      PuzzleBoard top = frontier.poll();
      //case : puzzle is not solvable
      if(!top.solvable()){
        System.out.println(top.getHash()+" not solvable");
        break;
      }

      //case: the top state is the goal state
      else if(top.getHash() == goal.getHash()){
        //get all parents and the actions needed to get there
        for(PuzzleBoard i = top; i.getParent() != null; i = i.getParent()){
          seq+=i.getAction()+"->";
        }
        seq+="DONE";
        result =  getReport(top, seq,(System.nanoTime() - startTime));
        break;
      }
      //case: the top puzzle is not the goal
      //need to add the child states
      else{
        //mark puzzle as visited
        visited.add(top.getHash());

        //add add possible child states
        ArrayList<PuzzleBoard> pm = top.possible();
        for(int i = 0; i < pm.size(); i++)
        {
          PuzzleBoard pos = pm.get(i);

          if(!visited.contains(pos.getHash())){
            pos.setHur(b);
            frontier.add(pos);
          }
        }
      }
    }
    return result;
  }

  public String[] getReport(PuzzleBoard top,String seq,long time){
    String[] s =  {String.valueOf(goal.getHash()),String.valueOf(initial.getHash()),String.valueOf(top.getPathCost()),String.valueOf(frontier.size() + visited.size()),String.valueOf(time),seq};
    return s;
  }

  public String[] execute(){
    String[] mis = run(true);
    String[] man = run(false);

    if(mis == null || man == null  ){
      return null;
    }
    String[] s =  {mis[0],mis[1],mis[2],mis[3],mis[4],mis[5],man[2],man[3],man[4],man[5]};
    return s;


  }



}
