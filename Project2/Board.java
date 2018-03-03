import java.lang.Math;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collections;

public class Board{

  private int[] values;
  private int pairsOfAttackingQueens;
  private double fitness;

  public Board(String s){

    String[] a = s.split(" ");
    values = new int[a.length];
    for(int i = 0; i < a.length; i++){
      values[i] = Integer.parseInt(a[i]);
    }
    calculate();

  }

  private void change(int index, int value){
    values[index] = value;
    calculate();
  }

  public double getFitness(){
    return getPairs();
  }

  public double getPairs(){
    return pairsOfAttackingQueens;
  }



  private void calculate(){
    int sum = 0;
    for(int i = 0; i < values.length; i++){
      for(int j = i; j < values.length; j++){
        if(i != j){
          if(values[i] == values[j]|| Math.abs(i-j) == Math.abs(values[i] - values[j])){
            sum ++;
          }
        }
      }
    }
    pairsOfAttackingQueens = sum;
    fitness = 1/(double)pairsOfAttackingQueens;
  }

  public ArrayList<Board> generateChildren(){
    ArrayList<Board> r = new ArrayList<>();
    for(int i = 0; i < values.length; i++){
      for(int j = 0; j < values.length; j++){
        Board b = new Board(toString());
        Board n = new Board(toString());
        b.change(i,j+1);
        if(!(b.toString()).equals(toString())){
          r.add(b);
        }
      }
    }
    Com com = new Com();
    Collections.sort(r, com);
    return r;
  }

  public Board getBestChild(){
    ArrayList<Board> r = generateChildren();
    return r.get(0);
  }

  public String toString(){
    String r = "";
    for(int a : values){
      r += a + " ";
    }
    return r;
  }

  public void printBoard(){
    for(int i = 0; i < values.length; i++){
      for(int j = 0; j <values[i]-1; j++){
        System.out.print("-");
      }
      System.out.print("Q");
      for(int j = values[i]; j <values.length; j++){
        System.out.print("-");
      }
      System.out.println();
    }
    System.out.println();
  }


}
