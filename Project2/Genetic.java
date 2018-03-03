import java.util.Random;
import java.lang.Math;
import java.lang.Integer;
import java.util.ArrayList;

public class Genetic{

  private int mutationRate;
  private Board best;
  private int dim;
  private int iter;
  private int popSize;

  private Board initial;

  /*
  @param d : the number of queens on Board
  @param i : the number of restarts we conduct per each run
  @param ti : the max time allowed per each run
  */
  public Genetic(int d,int mr,int it,int pop){
    dim = d;
    mutationRate = mr;
    iter = it;
    popSize = pop;
  }

  public String generateRandomBoard(){
    String res = "";
    for(int i = 0; i < dim; i++){
      Random r = new Random();
      res += (r.nextInt(dim)+1) + " ";
    }
    return res;
  }

  private ArrayList<Board> generatePopulation(Board p){
    ArrayList<Board> pop = p.generateChildren();
    ArrayList<Board> b  = new ArrayList<Board>(popSize);
    for(int i = 0; i < popSize; i++){
      b.add(pop.get(i));
    }
    return b;
  }

  private ArrayList<Board> crossOver(ArrayList<Board> parent){
    int popSize = parent.size();
    ArrayList<Board> crossOver = new ArrayList<>(popSize);
    for(int i = 0; i < popSize; i++){
      Random rand = new Random();

      Board p1 = parent.get(rand.nextInt(popSize));
      Board p2 = parent.get(rand.nextInt(popSize));

      int crossOverPoint = rand.nextInt(dim);
			while(crossOverPoint <= 0)
			{
				crossOverPoint = rand.nextInt(dim);
			}

			String[] p1V = (p1.toString()).split(" ");
      String[] p2V = (p2.toString()).split(" ");

      String[] c1V = new String[dim];
      String[] c2V = new String[dim];

      for(int j = 0; j < crossOverPoint; j++){
        c1V[j] = p1V[j];
        c2V[j] = p2V[j];
      }

      for(int j = crossOverPoint; j < dim; j++){
        c1V[j] = p2V[j];
        c2V[j] = p1V[j];
      }
      mutation(c1V);
      mutation(c2V);
      String c1 = "";
      String c2 = "";
      for(int j = 0; j < dim; j++){
        c1 += c1V[j] + " ";
        c2 += c2V[j] + " ";
      }
      Board child1 = new Board(c1);
      Board child2 = new Board(c2);
			crossOver.add(child1);
			crossOver.add(child2);
    }
    Com com = new Com();
    crossOver.sort(com);
    return crossOver;
  }

  private void mutation(String[] v){
    Random rand = new Random();

    int go = rand.nextInt(mutationRate);
    if(go == 1){
      int index = rand.nextInt(dim);
      v[index] = String.valueOf(rand.nextInt(dim));
    }
  }

  public Board run(){
    initial = new Board(generateRandomBoard());
    for(int i = 0; i < iter; i++){
      ArrayList<Board> pop = generatePopulation(initial);
      ArrayList<Board> children = crossOver(pop);
      initial = children.get(0);
      if(initial.getFitness() == 0){
        break;
      }
    }
    //System.out.println("Best :"+ initial.toString()+ " | Cost : "+initial.getFitness());
    return initial;
  }



}
