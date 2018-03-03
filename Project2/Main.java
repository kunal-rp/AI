public class Main{


  public static void main(String []args)
	{

    double RRcorrect = 0;
    double RRtime = 0;
    double RRcost= 0;
    double Gcorrect = 0;
    double Gtime = 0;
    double Gcost= 0;

    int num = 1;
    for(int i = 1; i <= num; i++){
      System.out.print(" "+i +" out of "+num+"\r" );

      RR r = new RR(21, 40,50);
      long start1 = System.currentTimeMillis();
      Board b = r.run();

      RRtime += (System.currentTimeMillis() - start1);
      RRcost += b.getFitness();
      if(b.getFitness() == 0){
        RRcorrect++;
      }


      long start2 = System.currentTimeMillis();
      Genetic gen = new Genetic(21,2,50,50);
      Gtime += (System.currentTimeMillis() - start2);
      b = gen.run();
      Gcost += b.getFitness();
      if(b.getFitness() == 0){
        Gcorrect++;
      }
    }
    System.out.println("\n \r Done \nAVG Time RR(mili) : "+ RRtime/num);
    System.out.println("Correct RR : "+ (RRcorrect/num)*100+"%");
    System.out.println("Avg Cost RR: "+ RRcost/num);
    System.out.println();
    System.out.println("AVG Time G(mili) : "+ Gtime/num);
    System.out.println("Correct G: "+ (Gcorrect/num)*100+"%");
    System.out.println("Avg Cost G: "+ Gcost/num);

    System.out.println("Random Restart:");
    for(int i = 0; i < 3; i++){
      System.out.println("Ex." + (i+1));
      RR r = new RR(21, 40,50);
      Board b = r.run();
      b.printBoard();
      System.out.println("Fitness : "+ b.getFitness());
    }

    System.out.println("Genetic:");
    for(int i = 0; i < 3; i++){
      System.out.println("Ex." + (i+1));
      Genetic gen = new Genetic(21,2,50,50);
      Board b = gen.run();
      b.printBoard();
      System.out.println("Fitness : "+ b.getFitness());
    }





  }
}
