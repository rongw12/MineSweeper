import java.util.Arrays;

public class MineFieldTester{
   public static void main(String[] args){
      boolean[][] t1 = new boolean[3][3]; // the 2D array will be passed into test1
      // boolean[] fillt1 = new boolean[3];
      // Arrays.fill(fillt1, false); // by default is all false.
      // Arrays.fill(t1, fillt1); // fill t1 with 1d array fillt1
      t1[0][0] = true;
      t1[1][1] = true;
      t1[2][2] = true;
      System.out.println("Testing the 1-arg constructor MineField(boolean[][] mineData).");
      System.out.println("The 2D array passed in is:");
      print2D(t1);
      System.out.println();
      
      MineField test1 = new MineField(t1);
      // System.out.println(test1);
      
      System.out.println("Testing the numRows()");
      System.out.println("The number of rows [exp:3]: " + test1.numRows());
      System.out.println();
      
      System.out.println("Testing the numCols()");
      System.out.println("The number of columns [exp:3]: " + test1.numCols());
      System.out.println();
      
      System.out.println("Testing the numMines()");
      System.out.println("The number of mines [exp:3]: " + test1.numMines());
      System.out.println();
      
      System.out.println("Testing the inRange(int row, int col)");
      System.out.println("[0, 0] is in range [exp: true]: " + test1.inRange(0, 0));
      System.out.println("[1, 2] is in range [exp: true]: " + test1.inRange(1, 2));
      System.out.println("[3, 0] is in range [exp: false]: " + test1.inRange(3, 0));
      System.out.println();
      
      System.out.println("Testing the numAdjacentMines(int row, int col)");
      System.out.println("The number of adjacent mines of [0, 0] is [exp: 1]: " + test1.numAdjacentMines(0, 0));
      System.out.println("The number of adjacent mines of [1, 2] is [exp: 2]: " + test1.numAdjacentMines(1, 2));
      System.out.println("The number of adjacent mines of [2, 2] is [exp: 1]: " + test1.numAdjacentMines(2, 2));
      System.out.println();
 
      System.out.println("Testing the hasMine(int row, int col)");
      System.out.println("[0, 0] has mine [exp:true]: " + test1.hasMine(0, 0));
      System.out.println("[2, 0] has mine [exp:false]: " + test1.hasMine(2, 0));
      System.out.println("[1, 1] has mine [exp:true]: " + test1.hasMine(1, 1));
      System.out.println();
  
      System.out.println("Testing the resetEmpty()");
      test1.resetEmpty();
      System.out.println("After resetEmpty()");
      System.out.println("The number of rows [exp:3]: " + test1.numRows());
      System.out.println("The number of columns [exp:3]: " + test1.numCols());
      System.out.println("The number of mines [exp:0]: " + test1.numMines());
      System.out.println("The number of adjacent mines of [0, 0] is [exp: 0]: " + test1.numAdjacentMines(0, 0));
      System.out.println("The number of adjacent mines of [1, 2] is [exp: 0]: " + test1.numAdjacentMines(1, 2));
      System.out.println("The number of adjacent mines of [2, 2] is [exp: 0]: " + test1.numAdjacentMines(2, 2));
      System.out.println("[0, 0] has mine [exp:false]: " + test1.hasMine(0, 0));
      System.out.println("[2, 0] has mine [exp:false]: " + test1.hasMine(2, 0));
      System.out.println("[1, 1] has mine [exp:false]: " + test1.hasMine(1, 1));
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
}
