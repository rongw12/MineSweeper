/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
  user can see about the minefield), Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms
  the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
  outside this class via the getMineField accessor.  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
    /**
    * @param mineField: The minefield to use for for this VisibleField.
    * @param numRows: number of rows this minefield will have, must be positive.
    * @param numCols: number of columns this minefield will have, must be positive.
    * @param status: The array of stats for each square.
    * @param numGuess: The number of guesses the user made.
    * @param mineExploded: whether the user clicked on the mine.
    * @param numUncovered: The number of squares that are unucovered.
    * @param rowChange: The change of rows when recursively finding the open area.
    * @param colChange: The change of columns when recursively finding the open area.
    */
   private MineField mineField;
   private int numRows;
   private int numCols;
   private int[][] status;
   private int numGuess;
   private int numUncovered;
   private boolean mineExploded;
   // private int[] uncoverRecursion;
   private static final int LEFT = -1; // go to the left column.
   private static final int RIGHT = 1; // go to the right column
   private static final int UP = -1; // go to the up row.
   private static final int DOWN = 1; // go to the down row.
   private int[] rowChange;
   private int[] colChange;
  
   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for this VisibleField.
    */
   public VisibleField(MineField mineField) {
      numGuess = 0;
      numUncovered = 0;
      mineExploded = false;
      this.mineField = mineField;
      numRows = this.mineField.numRows();
      numCols = this.mineField.numCols();
      status = new int[numRows][numCols];
      for (int i = 0; i < numRows; i++){
         for (int j = 0; j < numCols; j++){
            status[i][j] = COVERED;
         }
      }
      // uncoverRecursion = new int[]{-1, 0, 1};
      rowChange = new int[]{UP, 0, DOWN}; // for recursion when explorering the open region.
      colChange = new int[]{LEFT, 0, RIGHT};
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */     
   public void resetGameDisplay() {
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            status[i][j] = COVERED;
         }
      }
      numGuess = 0;
      numUncovered = 0;
      mineExploded = false;
   }
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {
      return mineField;
   }
   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the beginning of the class
      for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      assert getMineField().inRange(row, col);
      return status[row][col];
   }

   
   /**
      Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  This value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      return mineField.numMines() - numGuess; // can be negative.
   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
      
      @param statusUpdate: The cycle of the guess update.
      @param updatedIndex: The index of the statusUpdate the square will be updated to.
    */
   public void cycleGuess(int row, int col) {
      /**
      public static final int COVERED = -1;   // initial value of all squares
      public static final int MINE_GUESS = -2;
      public static final int QUESTION = -3;
      */
      assert getMineField().inRange(row, col);
      if (status[row][col] < 0){ //  this square is covered.
         int[] statusUpdate = new int[] {COVERED, MINE_GUESS, QUESTION};
         int updatedIndex = (-1 * status[row][col])%3;
         status[row][col] = statusUpdate[updatedIndex];
         if (status[row][col] == MINE_GUESS){
            numGuess++;
         } 
         else if (status[row][col] == QUESTION){
            numGuess--;
         }
      }
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      assert getMineField().inRange(row, col);
      if (mineField.hasMine(row, col)){ // uncover a mine here
         mineExploded = true; // the mine will explod, and end the game.
         losing(row, col); // update all the sqaures.
         return false;
      }
      else{ // The square is not a mine.
         uncoverSquare(row, col);// Uncover the spuare no matter if it is adjacent to mines.
         if (isGameOver()){ // If the user find all the non-mine squares.
            winning();
         }
         return true;
      }  
   }
 
   
   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game over
    */
   public boolean isGameOver() {
      return (mineExploded || (numUncovered + mineField.numMines() == (numRows * numCols))); // The game will over whether the mind got exploded, or found uncover all non-mine squares.
   }
 
   
   /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      assert getMineField().inRange(row, col);
      return status[row][col] >= 0;
   }
   
 
   // <private methods here>
   
    /**
     * When the user clicked on the mine at [row, col], that mine explodes, the the user lose the game.
     * The exploded mine is shown by a red square.
     * Any previously made incorrect guesses are shown with an X though them.
     * The correctly guessed mine locations are still shown as guesses (yellow).
     * The other unopened mines are shown as "mines" as a black square. 
     * The top middle of the window will show the sad face
     * A message about losing will appear on the upper right of the window.
     *
     * Update the squares to be one of the following:
     * MINE = 9;               // this loc is a mine that hasn't been guessed already (end of losing game)
     * INCORRECT_GUESS = 10;   // is displayed a specific way at the end of losing game
     * EXPLODED_MINE = 11;     // the one you uncovered by mistake (that caused you to lose)
     * @param row: The row of the square the user clicked.
     * @param col: The column of the square the user clicked.
     * PRE: getMineField().inRange(row, col)
     */
    private void losing(int row, int col){
       assert getMineField().inRange(row, col);
       for (int i = 0; i < numRows; i++){
          for (int j = 0; j < numCols; j++){
             if ((i == row) && (j == col)){ // the square the user clicked and exploded.
                status[i][j] = EXPLODED_MINE;
             }
             else{ // All the other squares.
                if ((status[i][j] == MINE_GUESS) &&(!mineField.hasMine(i, j))){ // incorrect guesses.
                   status[i][j] = INCORRECT_GUESS;
                }
                else if ((mineField.hasMine(i, j)) && ((status[i][j] == COVERED) || (status[i][j] == QUESTION))){ 
                   // Unopened squares. Can not use (status[row][col] != MINE_GUESS), cuz there will also be numbers from 0-8.
                   status[i][j] = MINE;
                }
             }
          }
       }  
    }
   
   
    /**
     * When all the non-mine squares got uncovered, the field display changes to show where the other mines are.
     * it shows them as guesses in yellow, so update all coverde squares to MINE_GUESS.
     * I.e., these would be any unopened squares that weren't already yellow.
     * The top middle of the window will show the happy face.
     * A message about winning will appear on the upper right of the window.
     */
   private void winning(){
      for (int i = 0; i < numRows; i++){
         for (int j = 0; j < numCols; j++){
            if (!this.isUncovered(i, j)){ // turn all coved squares into MINE_GUESS.
               status[i][j] = MINE_GUESS;
            }
         }
      }
      
   }
   
   
   /**
     * If the just opened square is not a mine, it will goes to this method.
     * If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in the neighboring area 
     * that are also not next to any mines, possibly uncovering a large region.
     * Any mine-adjacent squares you reach will also be uncovered,
     * and form (possibly along with parts of the edge of the whole field) the boundary of this region.
     * Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
     *
     * @param row: The row of the square the user clicked.
     * @param col: The column of the square the user clicked.
     * PRE: getMineField().inRange(row, col)
     */
   private void uncoverSquare(int row, int col){
      if (!mineField.inRange(row, col) || (isUncovered(row, col)) || (status[row][col] == MINE_GUESS) || mineField.hasMine(row, col)){ //  if it is out of range, or it has been guessed, is a mine, or it has been uncovered, do nothing.
            return;
        }
      status[row][col] = mineField.numAdjacentMines(row, col);
      numUncovered++;
      if (status[row][col] == 0) { // if it has not adjacent mines.
         for (int i: rowChange){
            for (int j: colChange){ 
               uncoverSquare(row + i, col + j); // it will go to (row, col) again, but will return; since it is uncovered now.
            }
         }
      }
   }
   
}
