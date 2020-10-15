// Name: Rong Wang
// USC NetID: rwang424
// CSCI455 PA2
// Fall 2020

import java.util.Arrays;

public class VisibleFieldTester{
   public static void main(String[] args){
      boolean[][] t1 = 
      {{false, false, false, false}, 
       {true, false, false, false}, 
       {false, true, true, false},
       {false, true, false, true}};
   
      System.out.println("Testing VisibleField.");
      System.out.println("The 2D array passed in is:");
      print2D(t1);
      System.out.println();
      
      MineField test1 = new MineField(t1);
      VisibleField test1VF = new VisibleField(test1);
      System.out.println("Get status: [exp: all covered -1]: ");
      printStatus(test1VF, t1.length, t1[0].length);
      
      /*
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   */
      System.out.println("Uncover (1, 1), not mine");
      test1VF.uncover(1, 1);
      System.out.println("exp: ");
      int[][] exp1 = 
      {{-1, -1, -1, -1}, 
       {-1, 3, -1, -1}, 
       {-1, -1, -1, -1},
       {-1, -1, -1, -1}};
      print2DInt(exp1);
      System.out.println();
      System.out.println("After uncover (1, 1)");
      printStatus(test1VF, t1.length, t1[0].length);
      System.out.println();
      
      
      System.out.println("Uncover (0, 2), not mine, has no mine adjacent");
      test1VF.uncover(0, 2);
      System.out.println("exp: ");
      int[][] exp2 = 
      {{-1, 1, 0, 0}, 
       {-1, 3, 2, 1}, 
       {-1, -1, -1, -1},
       {-1, -1, -1, -1}};
      print2DInt(exp2);
      System.out.println();
      System.out.println("After uncover (0, 2)");
      printStatus(test1VF, t1.length, t1[0].length);
      System.out.println();
      
      
      System.out.println("make (2, 2) MINE_GUESS correctly, which is a mine.");
      test1VF.cycleGuess(2, 2);
      System.out.println("exp: ");
      int[][] exp3 = 
      {{-1, 1, 0, 0}, 
       {-1, 3, 2, 1}, 
       {-1, -1, -2, -1},
       {-1, -1, -1, -1}};
      print2DInt(exp3);
      System.out.println();
      System.out.println("After guess mine (2, 2)");
      printStatus(test1VF, t1.length, t1[0].length);
      System.out.println();
      
      
      System.out.println("make (3, 2) MINE_GUESS incorrectly, which is not a mine.");
      test1VF.cycleGuess(3, 2);
      System.out.println("exp: ");
      int[][] exp4 = 
      {{-1, 1, 0, 0}, 
       {-1, 3, 2, 1}, 
       {-1, -1, -2, -1},
       {-1, -1, -2, -1}};
      print2DInt(exp4);
      System.out.println();
      System.out.println("After guess mine (2, 2)");
      printStatus(test1VF, t1.length, t1[0].length);
      System.out.println();
      
      
      System.out.println("Uncover (1, 0), which is a mine, end the game with mine emploded.");
      test1VF.uncover(1, 0);
      System.out.println("exp: ");
      int[][] exp5 = 
      {{-1, 1, 0, 0}, 
       {11, 3, 2, 1}, 
       {-1, 9, -2, -1},
       {-1, 9, 10, 9}};
      print2DInt(exp5);
      System.out.println();
      System.out.println("After uncover (1, 0)");
      printStatus(test1VF, t1.length, t1[0].length);
      System.out.println();
      
      
      
      // Second test
      System.out.println("Testing VisibleField.");
      boolean[][] t2 = 
      {{false, false, false, true}, 
       {false, false, false, false}, 
       {false, false, false, false},
       {false, true, false, false}};
   
      System.out.println("The 2D array passed in is:");
      print2D(t2);
      System.out.println();
      
      MineField test2 = new MineField(t2);
      VisibleField test2VF = new VisibleField(test2);
      System.out.println("Get status: [exp: all covered -1]: ");
      printStatus(test2VF, t2.length, t2[0].length);
      
      /*
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   */
      System.out.println("Uncover (1, 1), not mine");
      test2VF.uncover(1, 1);
      System.out.println("exp: ");
      int[][] exp21 = 
      {{0, 0, 1, -1}, 
       {0, 0, 1, -1}, 
       {1, 1, 1, -1},
       {-1, -1, -1, -1}};
      print2DInt(exp21);
      System.out.println();
      System.out.println("After uncover (1, 1)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 2]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      System.out.println("make (3, 1) MINE_GUESS correctly, which is a mine.");
      test2VF.cycleGuess(3, 1);
      System.out.println("exp: ");
      int[][] exp22 = 
      {{0, 0, 1, -1}, 
       {0, 0, 1, -1}, 
       {1, 1, 1, -1},
       {-1, -2, -1, -1}};
      print2DInt(exp22);
      System.out.println();
      System.out.println("After guess mine (3, 1)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 1]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      System.out.println("Uncover (3, 3), not mine, not adjacent to a mine ");
      test2VF.uncover(3, 3);
      System.out.println("exp: ");
      int[][] exp23 = 
      {{0, 0, 1, -1}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {-1, -2, 1,0}};
      print2DInt(exp23);
      System.out.println();
      System.out.println("After uncover (3, 3)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 1]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();

      
      System.out.println("make (3, 0) MINE_GUESS incorrectly, which is not a mine.");
      test2VF.cycleGuess(3, 0);
      System.out.println("exp: ");
      int[][] exp24 = 
      {{0, 0, 1, -1}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {-2, -2, 1,0}};
      print2DInt(exp24);
      System.out.println();
      System.out.println("After guess mine (3, 0)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 0]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      
      System.out.println("make (0, 3) MINE_GUESS incorrectly, which is not a mine.");
      test2VF.cycleGuess(0, 3);
      System.out.println("exp: ");
      int[][] exp25 = 
      {{0, 0, 1, -2}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {-2, -2, 1,0}};
      print2DInt(exp25);
      System.out.println();
      System.out.println("After guess mine (0, 3)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: -1]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      System.out.println("make (0, 3) QUESTION, which is not a mine.");
      test2VF.cycleGuess(0, 3);
      System.out.println("exp: ");
      int[][] exp26 = 
      {{0, 0, 1, -3}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {-2, -2, 1,0}};
      print2DInt(exp26);
      System.out.println();
      System.out.println("After guess mine (0, 3)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 0]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      System.out.println("make (0, 3) COVERED again, which is not a mine.");
      test2VF.cycleGuess(0, 3);
      System.out.println("exp: ");
      int[][] exp27 = 
      {{0, 0, 1, -1}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {-2, -2, 1,0}};
      print2DInt(exp27);
      System.out.println();
      System.out.println("After guess mine (0, 3)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 0]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: no]:"  + test2VF.isGameOver());
      System.out.println();
      
      
      System.out.println("Uncover (0, 3), which is a mine, end the game with mine emploded.");
      test2VF.uncover(0, 3);
      System.out.println("exp: ");
      int[][] exp28 = 
      {{0, 0, 1, 11}, 
       {0, 0, 1, 1}, 
       {1, 1, 1, 0},
       {10, -2, 1,0}};
      print2DInt(exp28);
      System.out.println();
      System.out.println("After uncover (0, 3)");
      printStatus(test2VF, t2.length, t2[0].length);
      System.out.println("Mines left: [exp: 0]: " + test2VF.numMinesLeft());
      System.out.println("Game over? [exp: yes]:"  + test2VF.isGameOver());
      System.out.println();

   }
   
   
   private static void print2D(boolean[][] t){
      for (int i = 0; i < t.length; i++){
         for (int j = 0; j < t[0].length; j++){
            System.out.print(t[i][j] + "  ");
         }
         System.out.println();
      }
   }
   
   private static void printStatus(VisibleField t1, int numRows, int numCols){
      for (int i = 0; i < numRows; i++){
         for (int j = 0; j < numCols; j++){
            System.out.print(t1.getStatus(i, j) + "  ");
         }
         System.out.println();
      }
   }
   
   private static void print2DInt(int[][] t){
      for (int i = 0; i < t.length; i++){
         for (int j = 0; j < t[0].length; j++){
            System.out.print(t[i][j] + "  ");
         }
         System.out.println();
      }
   }
}