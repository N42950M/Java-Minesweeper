package minesweeper.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Resetchanger implements EventHandler<ActionEvent> {
    private MinewsweeperGUI gui; // The GUI that this event handler is associated with
    private int rows; // The number of rows in the board
    private int mines; // The number of mines on the board
    private int cols; // The number of columns in the board
    private StackPane pane; // The pane that contains the board
    private Label moves; // The label that displays the number of moves 
    private Label win_or_nah; // The label that displays the win or lose message
    
    public Resetchanger(MinewsweeperGUI gui,int rows,int cols,int mines,StackPane pane,Label moves,Label win_or_nah){
        this.gui = gui; 
        this.cols = cols;
        this.rows = rows;
        this.mines = mines;
        this.pane = pane;
        this.moves = moves;
        this.win_or_nah = win_or_nah;
    }

    @Override
    public void handle(ActionEvent arg0) {
        moves.setText("Moves: 0"); // Reset the number of moves shown
        gui.lost_moves = 0; // Reset the number of moves to be shown on loss
        GridPane root = gui.makepane(rows, cols, mines); // creates a new pane to be stacked on top of the old one <insert skull emoji>
        pane.getChildren().add(root); // adds the new pane to the stack
        win_or_nah.setText(""); // Reset the win or lose message
        win_or_nah.setBackground(new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(5.0), new Insets(0.0)))); // Reset the win or lose message background
    }
}
