import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class AStar{

  Node goal;
  Node initial;
  PriorityQueue<Node> frontier ;
  HashSet<String> visited;

  public AStar(Node go, Node i){
    goal  =go;
    initial = i;
  }


  public String run(){
    if(!initial.solvable()){
      return "Initial State is not solvable";
    }
    if(!goal.solvable()){
      return "Goal State is not achievable";
    }
    //initialize vars and adds the initial puzzle to the queue
    frontier = new PriorityQueue<Node>(new Com());
    visited = new HashSet<>();
    frontier.add(initial);
    String seq = "";
    long startTime = System.currentTimeMillis() ;
    while(!frontier.isEmpty()){

      Node top = frontier.poll();
      //System.out.println(top.getHash());
      //case : puzzle is not solvable
      if(!top.solvable()){}
      else if(top.isComplete()){
        //get all parents and the actions needed to get there
        for(Node i = top; i.getParent() != null; i = i.getParent()){
          seq=i.getAction()+" -> \n"+seq;
        }
        seq+="DONE";
        return (seq);

      }
      //case: the top puzzle is not the goal
      //need to add the child states
      else{
        //mark puzzle as visited
        visited.add(top.getHash());

        //add add possible child states
        ArrayList<Node> pm = top.neighbors();
        for(int i = 0; i < pm.size(); i++)
        {
          Node pos = pm.get(i);
          if(!visited.contains(pos.getHash())){
            frontier.add(pos);
          }
        }
      }
    }
    return "No Solution";
  }


}
