package minesweeper.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Minesweeper
{   
    public char MINE = 'M'; // The character used to represent a mine
    public char COVERED  = '-'; // The character used to represent a covered square
    private char[][] board; // The board on which the game is played
    private int rows; // The number of rows on the board
    private int cols; // The number of columns on the board
    private int moveCount; // The number of moves made so far
    private GameState state; // The current state of the game
    private Set<Location> set; // The set of locations of the mines
    private MinesweeperObserver observer; // The observer of the game
    private int mines; // The number of mines on the board


    public Minesweeper(Minesweeper minesweeper){
        this.rows = minesweeper.rows;
        this.cols = minesweeper.cols;
        this.board = minesweeper.board.clone();
        this.mines = minesweeper.mines;
        this.moveCount = minesweeper.moveCount;
        this.state = minesweeper.state;
        this.set = minesweeper.set;
        this.observer = null;

    }
    /**
     *  constructs a
        Minesweeper object with rows x columns cells and mineCount randomly
        placed mines
     * @param rows
     * @param cols
     * @param mineCount
     */
    public Minesweeper(int rows, int cols, int mineCount){   
        set = new HashSet<>(); // The set of locations of the mines
        this.rows = rows;
        this.cols = cols;
        this.mines = mineCount;
        board = new char[rows][cols];
        Random rand = new Random();
        
        while(set.size() < mineCount){ //sets locations for mines
            int rand_int = rand.nextInt(rows);
            int rand_col = rand.nextInt(cols);
            set.add(new Location(rand_int, rand_col));
        }
        for(int i = 0 ;i< rows;i++){ //covers the board
            for(int j = 0; j< cols;j++){
                board[i][j] = COVERED;
            }
        }
        moveCount = 0; //sets move count to 0
        state = GameState.NOT_STARTED; //sets state to not started
    }

    /** 
     * a minesweeper with a set random value
     */
    public Minesweeper(int rows, int cols, int mineCount,int randomseed){   
        set = new HashSet<>(); //set of locations for mines
        this.rows = rows;
        this.cols = cols;
        board = new char[rows][cols];
        Random rand = new Random(randomseed);
        int size = 0; 
        while(size < mineCount){ //sets locations for mines
            int rand_int = rand.nextInt(rows);
            int rand_col = rand.nextInt(cols);
            set.add(new Location(rand_int, rand_col));
            size++;
        }
        for(int i = 0 ;i< rows;i++){ //covers the board
            for(int j = 0; j< cols;j++){
                board[i][j] = COVERED; 
            }
        }
        moveCount = 0; //sets move count to 0
        state = GameState.NOT_STARTED; //sets state to not started
    }
    
    /**
     * attempts to select a location on the
        board. If the selection is invalid, a MinesweeperException is thrown.
     * @param location
     * @throws MinesweeperException
     */
    public void makeSelction(Location location) throws MinesweeperException{
        int current_row = location.getRow(); //gets the row of the location
        int current_col = location.getCol(); //gets the column of the location
        
        state = GameState.IN_PROGRESS; //sets state to in progress
        if(set.contains(location)){ //if the location is a mine
            moveCount++; //increments move count
            board[current_row][current_col] = MINE; //sets the location to a mine
            state = GameState.LOST; //sets state to lost
        }
        else if(board[current_row][current_col] == COVERED){ //if the location is covered and not a mine
            board[current_row][current_col] = (getneigbhors(location)+"").charAt(0); //sets the location to the number of mines around it
            moveCount++;  //increments move count
        }
        
        if(this.getPossibleSelections().size() == 0){ //if there are no more possible selections
            this.state = GameState.WON; //sets state to won
        }
        if(this.observer != null)
            notifyObserver(location); //notifies the observer
    }
    
    /**
     * returns the number of mines adjacent to the location
     * @param location
     * @return
     */
    private int getneigbhors(Location location){
        int current_row = location.getRow(); //gets the row of the location
        int current_col = location.getCol(); //gets the column of the location
        int mines = 0; //sets the number of adjacent mines to 0
        //checks all 8 locations around the location to see if they are a mine, if yes then increments mines
        if(set.contains(new Location(current_row + 1, current_col)))
            mines++;
        if(set.contains(new Location(current_row - 1, current_col)))
            mines++;
        if(set.contains(new Location(current_row, current_col + 1)))
            mines++;
        if(set.contains(new Location(current_row , current_col -1)))
            mines++;
        if(set.contains(new Location(current_row + 1, current_col + 1)))
            mines++;
        if(set.contains(new Location(current_row - 1, current_col - 1)))
            mines++;
        if(set.contains(new Location(current_row + 1, current_col - 1)))
            mines++;
        if(set.contains(new Location(current_row - 1, current_col + 1)))
            mines++;
        return mines; //returns the total number of adjacent mines
    }

    /**
     *  returns the number of moves for this game thus far
     * @return
     */
    public int getMoveCount(){
        return moveCount;
    }

    /**
     * return the current state of the game
     * @return
     */
    public GameState getGameState(){
        return state;
    }

    /**
     * returns a Collection of all valid cell locations,
        i.e. covered and save, that a player may choose from on the board with its
        current configuration
     * @return
     */
    public Collection<Location> getPossibleSelections(){   
        Set<Location> possible_selections = new HashSet<>(); //creates the set of possible selections
        for(int i = 0 ;i < rows;i++){ //for all the rows
            for(int j = 0;j < cols;j++){ //for all the columns
                if(board[i][j] == COVERED && !set.contains(new Location(i,j))){ //if the location is covered and not a mine
                    possible_selections.add(new Location(i, j)); //adds the location to the set of possible selections
                }
            }
        }
        return possible_selections; //returns the set of possible selections
    }

    @Override
    /**
     * returns a string that can be used to display the board. Each
        character in the board should be Minesweeper.COVERED (constant),
        Minesweeper.MINE (constant), or ‘0’ - ‘8’ (number of adjacent mines)
     */
    public String toString(){
        String i = ""; //creates the string to be returned
        for(int k = 0; k < rows; k++){ //for all the rows
            for(int j = 0; j < cols;j++){ //for all the columns
                i += "[" + board[k][j] + "]"; //adds the character to the string
            }
            i+= "\n"; //adds a new line to the string
        }
        return i; //returns the string
    }

    /**
     * returns the finished game board
     */
    public String printSolution() throws MinesweeperException{
        for(int i = 0; i< rows;i++){ //for all the rows
            for(int j = 0; j< cols;j++){ //for all the columns
                this.makeSelction(new Location(i, j)); //makes a selection at the location which uncovers it
            }
        }
        return this.toString(); //returns the string of the board to be displayed
    }

    /**
     * registers the observer with the minesweeper class
     */
    public void register(MinesweeperObserver observer){
        this.observer = observer; //sets the observer to the observer passed in
    }

    /**
     * notifies the observer of the location that was selected
     */
    private void notifyObserver(Location location){
        observer.cellUpdated(location); //calls cellupdated on the location
    }

    /**
     * returns the character at the specified location
     */
    public char getSymbol(Location location) throws MinesweeperException{
        return board[location.getRow()][location.getCol()];
    }

    /**
     * checks if the location is covered or not
     * @return true if the location is covered, false otherwise
     */
    public boolean isCovered (Location location) throws MinesweeperException{
        return board[location.getRow()][location.getCol()] == COVERED;
    }

    /**
     * sets the game state
     */
    public void setState(GameState state) {
        this.state = state; //sets the state to the state passed in
    }

    /**
     * returns the board
     */
    public char[][] getBoard(){
        return board;
    }

    /**
     * returns the number of rows
     */
    public int getRows(){
        return rows;
    }

    /**
     * returns the number of columns
     */
    public int getCols(){
        return cols;
    }

    /**
     * returns the number of mines
     */
    public int getMines() {
        return mines;
    }

    public static void main(String[] args) throws MinesweeperException {
        Minesweeper sweep = new Minesweeper(3,3,3,1);
        System.out.println(sweep.printSolution());

    }
}
