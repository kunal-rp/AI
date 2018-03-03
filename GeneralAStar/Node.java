import java.util.ArrayList;

public abstract class Node<I extends Node>{

  //a unique way to identify the current hash
  protected String hash;

  //the current cost total to get to this node
  protected double pathCost;

  //this estimated/remaining cost to get to the goal state/condition
  protected double heuristic;

  //the parent node that generated the node
  protected Node parent;

  //the specific action that occured to the get to this node
  protected String action;

  Node(Node parent, double pathCost,String action){
    this.parent = parent;
    this.pathCost = pathCost;
    this.action = action;

  }

  abstract protected ArrayList<I> neighbors();

  abstract protected boolean isComplete();

  //calculates the unique hash of the node
  abstract protected void calculateHash();

  //calculates whether the node is solvable or not if needed
  abstract public boolean solvable();

  //calculates the heuristic
  abstract public void calculateHeuristicCost();

  //returns the heuristic cost
  public double getHeuristicCost(){
    return heuristic;
  }

  //returns the heuristic cost
  public double getPathCost(){
    return pathCost;
  }

  public String getAction(){
    return action;
  }

  public String getHash(){
    return hash;
  }

  public Node getParent(){
    return parent;
  }




}
