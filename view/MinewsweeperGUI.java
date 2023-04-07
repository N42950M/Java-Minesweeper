package minesweeper.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.model.GameState;
import minesweeper.model.Location;
import minesweeper.model.Minesweeper;
import minesweeper.model.MinesweeperException;
import minesweeper.model.MinesweeperObserver;

public class MinewsweeperGUI extends Application implements MinesweeperObserver {
    private Minesweeper minesweeper; // The minesweeper
    private boolean really_dead = false; // variable for checking if the player has really lost
    protected int lost_moves = 0; // variable for counting the number of moves the player has made on loss
    private Button[][] buttons; // The buttons in the grid pane
    private Label moves = new Label(); // Label for the number of moves
    private Label win_or_nah = new Label(); // Label for the win or loss message

    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = makepane(10,10,30); //makes the grid pane
        StackPane sta = new StackPane(); //makes the stack pane
        sta.getChildren().add(root); //adds the grid pane to the stack pane
        Button help = new Button("Help"); //makes the help button
        help.setOnAction(new Hintchanger(this)); //sets the help button to show the hint
        win_or_nah.setText("");//sets the win or loss message to space for the color
        win_or_nah.setMinWidth(810); // sets the width of the label 
        win_or_nah.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5.0), new Insets(0.0))));//hehehaha
        Button reset = new Button("Reset"); //makes the reset button
        reset.setBackground(new Background(new BackgroundFill(Color.BISQUE, new CornerRadii(5.0), new Insets(0.0))));
        help.setBackground(new Background(new BackgroundFill(Color.BISQUE, new CornerRadii(5.0), new Insets(0.0))));
        reset.setMinWidth(60); // sets the width of the reset button
        help.setMinWidth(60); // sets the width of the help button
        reset.setMinHeight(40); // sets the height of the reset button
        help.setMinHeight(40); // sets the height of the help button
        reset.setOnAction(new Resetchanger(this, 10, 10, 30,sta,moves,win_or_nah)); //sets the reset button to reset the game
        Label minecount = new Label("Mines: " + minesweeper.getMines()); //makes the label for the number of mines
        minecount.setMinHeight(40);
        minecount.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5.0), new Insets(0.0))));
        moves.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5.0), new Insets(0.0))));
        moves.setMinHeight(40);
        moves.setText("Moves: " + minesweeper.getMoveCount()); //sets the move count for the move text
        Button solver = new Button("Solve"); //makes the solver button
        solver.setOnAction(solveMine_Sweep()); //sets the solver button to solve the game
        VBox vbox = new VBox(); //makes the vbox
        vbox.getChildren().addAll(minecount, moves,help, reset,solver); //adds the labels and buttons to the vbox
        HBox hbox = new HBox(); //makes the hbox
        hbox.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5.0), new Insets(0.0))));
        vbox.setMinWidth(60); // sets the width of the vbox
        hbox.getChildren().addAll(vbox, sta); //adds the vbox and the stack pane to the hbox 
        VBox wholething = new VBox(); //makes the vbox for the whole thing
        wholething.getChildren().addAll(hbox, win_or_nah); //adds the hbox and the win or loss message to the vbox
        stage.setScene(new Scene(wholething)); //sets the scene to the vbox
        stage.show(); //shows the stage
    }

    private EventHandler<ActionEvent> solveMine_Sweep() 
    {
        //TODO: Implement this method
        return null;
    }

    /**
     * Makes the grid pane
     * @param rows rows of the grid pane
     * @param cols columns of the grid pane
     * @param mines number of mines to use
     * @return
     */
    public GridPane makepane(int row,int col, int mine){
        GridPane root = new GridPane(); //makes the grid pane
        minesweeper = new Minesweeper(row, col, mine); //makes the minesweeper
        minesweeper.register(this); //registers the minesweeper
        buttons = new Button[minesweeper.getRows()][minesweeper.getCols()]; //makes the buttons
        for(int i = 0; i < minesweeper.getRows(); i++){ //for loop for the rows
            for(int j = 0; j < minesweeper.getCols(); j++){ //for loop for the columns
                Button button = makeButton(i, j); //makes the button
                buttons[i][j] = button; //adds the button to the buttons
                button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT))); //sets the border for the button
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY))); //sets the background for the button
                root.add(button, i, j); //adds the button to the grid pane
            }
        }
        return root; //returns the grid pane
    }
    
    /**
     * Makes the button
     * @param row row for the button
     * @param col column for the button
     * @return the button
     */
    public Button makeButton(int row, int col) {
        Button button = new Button(); //make a button
        button.setPrefSize(75, 75); //set the size
        button.setOnAction(new Buttonchanger(new Location(row, col), this));  //set the action
        return button; //return the button
    }

    /**
     * Updates the minesweeper
     * @param location the location of the button(cell) getting updated
     */
    @Override
    public void cellUpdated(Location location) {
        char[][] board = minesweeper.getBoard(); //gets the board
        Button button = buttons[location.getRow()][location.getCol()]; //gets the button
        button.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))); //sets the background for the button
        button.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        moves.setText("Moves: " + minesweeper.getMoveCount()); //sets the move count for the move text
        if(board[location.getRow()][location.getCol()] == 'M'){ //if the cell is a mine
            button.setGraphic(new ImageView(new Image("media/images/mine24.png"))); //sets the graphic for the button
        }
        else{
            //changes the color of the numbers based on how many mines are around them
            button.setText(Character.toString(board[location.getRow()][location.getCol()]));
            if(board[location.getRow()][location.getCol()] == '0'){ //if the cell is a 0 sets color to white and removes the text
                button.setText("");
                button.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                button.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            else if(board[location.getRow()][location.getCol()] == '1'){ //if the cell is a 1 sets color to blue
                button.setTextFill(Color.BLUE);
            }
            else if(board[location.getRow()][location.getCol()] == '2'){ //if the cell is a 2 sets color to green
                button.setTextFill(Color.GREEN);
            }
            else if(board[location.getRow()][location.getCol()] == '3'){ //if the cell is a 3 sets color to red
                button.setTextFill(Color.RED);
            }
            else if(board[location.getRow()][location.getCol()] == '4'){ //if the cell is a 4 sets color to darker blue
                button.setTextFill(Color.DARKBLUE);
            }
            else if(board[location.getRow()][location.getCol()] == '5'){ //if the cell is a 5 sets color to darker red
                button.setTextFill(Color.DARKRED);
            }
            else if(board[location.getRow()][location.getCol()] == '6'){ //if the cell is a 6 sets color to darker green
                button.setTextFill(Color.DARKGREEN);
            }
            else if(board[location.getRow()][location.getCol()] == '7'){ //if the cell is a 7 sets color to dark gray
                button.setTextFill(Color.DARKGRAY);
            }
            else if(board[location.getRow()][location.getCol()] == '8'){ //if the cell is a 8 sets color to dark orange
                button.setTextFill(Color.DARKORANGE);
            }
        }  
        if(minesweeper.getGameState() == GameState.LOST) //if the game is lost
        {
            minesweeper.setState(GameState.IN_PROGRESS); //sets the game state to in progress so it doesnt go through this section again
            this.really_dead = true; //sets the really dead to true
            if(lost_moves == 0){ //if the lost moves is 0 sets the lost moves to the move count
                lost_moves = minesweeper.getMoveCount();
            }
            for(int i = 0; i < minesweeper.getRows(); i++){ //for loop for the rows
                for(int j = 0; j < minesweeper.getCols(); j++){ //for loop for the columns
                    Location L = new Location(i, j); //makes a location at the row and column
                    if(minesweeper.getBoard()[i][j] == '-'){ //if the cell is blank it makes a move and then updates that cell
                        try{
                            minesweeper.makeSelction(L);
                        } 
                        catch (MinesweeperException e){
                            System.out.println(e);
                        }
                        cellUpdated(L);
                    }
                }
            }
        }
        else if(minesweeper.getGameState() == GameState.WON){ //if the game is won
            if(really_dead){ //checks if really dead is true, then displays lost message if really dead
                moves.setText("Moves: " + lost_moves);
                win_or_nah.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5.0), new Insets(0.0))));
                win_or_nah.setText("You Lose! Omae wae shindeiru!          ");
            }
            else{ //displays the win message
                win_or_nah.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5.0), new Insets(0.0))));
                win_or_nah.setText("You Win!            ");
            }
        }
    }

    //gets the minesweeper
    public Minesweeper getMinesweeper() {
        return minesweeper;
    }

    //gets the buttons
    public Button[][] getButtons() {
        return buttons;
    }

    /**
     * is used when buttons are pressed in the buttonchanger observer
     */
    public void buttonpressed(Location location) throws MinesweeperException {
        minesweeper.makeSelction(location);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}