// Name: Rong Wang
// USC NetID: rwang424
// CS 455 PA3
// Fall 2020

import java.util.Random;
import java.util.Arrays;

/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   
   // <put instance variables here>
   /**
    * @param mineData: the data for the mines; must have at least one row and one col, and must be rectangular.
    * @param numRows: number of rows this minefield will have, must be positive.
    * @param numCols: number of columns this minefield will have, must be positive.
    * @param numMines: number of mines this minefield will have,  once we populate it.
    */
   private boolean[][] mineData;
   private int numRows;
   private int numCols;
   private int numMines;
   private Random rand;
   private static final int LEFT = -1; // go to the left column.
   private static final int RIGHT = 1; // go to the right column
   private static final int UP = -1; // go to the up row.
   private static final int DOWN = 1; // go to the down row.
   private static final boolean HAS_MINE = true; // has mine is true 
   private static final boolean NO_MINE = false; // has mine is false
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {
      numRows = mineData.length;
      numCols = mineData[0].length;
      this.mineData = new boolean[numRows][numCols];
      numMines = 0;
      rand = new Random();
      for(int i = 0; i < numRows; i++){ // deep copy
         this.mineData[i] = Arrays.copyOf(mineData[i], numCols);
      }  
      for (int i = 0; i < numRows; i++){ // count how may mines the mineData has.
         for (int j = 0; j < numCols; j++){
            if (mineData[i][j]){ //  if HAS_MINE
               numMines++; 
            }
         }
      }
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      assert (numRows > 0) && (numCols > 0) && (0 <= numMines) && (numMines< (1/3)*(numRows*numCols));
      this.numRows = numRows;
      this.numCols = numCols;
      this.mineData = new boolean[numRows][numCols]; // with all 0s.
      this.numMines = numMines;
      rand = new Random();
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   public void populateMineField(int row, int col) {
      resetEmpty(); //make it empty first, remove any current mines on the minefield.
      int count = 0;
      // rand = new Random();
      while (count < numMines){
         int randRow = rand.nextInt(numRows); // the random row we want to put a mine on it.
         int randCol = rand.nextInt(numCols); // the random column we want to put a mine on it.
         if (((randRow != row) || (randCol != col)) && (mineData[randRow][randCol] != HAS_MINE)){
            mineData[randRow][randCol] = HAS_MINE;
            count++;
         }        
      }
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in 
         at the beginning of a game.
    */
   public void resetEmpty() {
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            this.mineData[i][j] = NO_MINE;
         }
      }
   }

   
  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      assert inRange(row, col);
      int count = 0;
      for (int i = row+UP; i <= row+DOWN; i++) {
         for (int j = col+LEFT; j <= col+RIGHT; j++) {
            // System.out.print("" + i + j);
            if (inRange(i, j) && mineData[i][j] && ((i != row) || (j != col))){ 
               count++;
            }
            // System.out.println("   " + count);
         }
      }  
      return count;  
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
      return (0 <= row && row < numRows) && (0 <= col && col < numCols);
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return this.numRows;      
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return this.numCols;   
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {
      assert inRange(row, col);
      return this.mineData[row][col];
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return
    */
   public int numMines() {
      return this.numMines;     
   }

   
   // <put private methods here>
   
         
}

