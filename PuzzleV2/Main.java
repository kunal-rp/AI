import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main{

  public static void main(String []args) throws IOException
  {

    //Columns on the output file
    String[] columns = {"Goal","Initial","Mis. Path Cost","Mis. # Nodes","Mis.Time(mili)","Mis. Seq.","Man. Path Cost","Man. # Nodes","Man.Time(mili)","Man. Seq."};

    int userChoice = 0;
    while(userChoice != 3){
      //get user command
      Scanner scanner = new Scanner(System.in);
      System.out.println("1 = Enter a puzzle");
      System.out.println("2 = 100 Random puzzles");
      System.out.println("3 = Exit");
      userChoice = scanner.nextInt();
      //case: user input puzzle
      if(userChoice == 1){
        scanner.nextLine();
        System.out.println("Please Enter Each Row Left to Right as One Line NO SPACES.");
        String line = scanner.nextLine();
        //the goal state is automatically set to '123456780'
        PuzzleBoard goal = new PuzzleBoard("123456780",null, null, 0);
        //the starting puzzle board is set to what the user inputted
        PuzzleBoard initial = new PuzzleBoard(line,null, goal, 0);
        Handle h = new Handle(goal, initial);
        String[] r = h.execute();
        if(r == null){
          System.out.println("Unsolvable Puzzle Inputted");
        }
        else{
          //input the resuts into the output file
          for(int i = 0 ; i < columns.length; i++){
            System.out.println(columns[i] + ":"+r[i] + "\n");
          }
        }
      }
      //case randomly generated puzzles
      else if(userChoice ==2){

        String file = "output.csv";
        File fi = new File(file);

        //delete existing output file
        if (fi.exists())
        {
          fi.delete();
        }
        //append the columns onto the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        for(String s :columns){
          writer.append(s+",");
        }
        writer.append("\n");

        //will use these puzzle boards and the handle object to generate the random puzzles
        PuzzleBoard goal = new PuzzleBoard("123456780",null, null, 0);
        PuzzleBoard initial = new PuzzleBoard("123456780",null, goal, 0);
        Handle h1 = new Handle(goal, initial);

        for(PuzzleBoard p : h1.generateRandomPuzzles()){
          Handle r = new Handle(goal, p);
          String[] res = r.execute();
          if(res != null){
            for(String s : res){
              writer.append(s+",");
            }
            writer.append("\n");
          }
        }
        writer.close();
        System.out.println("OUTPUT in output.csv");
      }
    }









  }

}
