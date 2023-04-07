package minesweeper.view;

import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import minesweeper.model.Location;

public class Hintchanger implements EventHandler<ActionEvent> {
    private MinewsweeperGUI minesweeper; // The GUI that this event handler is associated with
    
    public Hintchanger(MinewsweeperGUI minesweeper) {
        this.minesweeper = minesweeper;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Set<Location> list = (Set<Location>) minesweeper.getMinesweeper().getPossibleSelections(); // get the list of possible selections to be used for a hint
        for(Location location: list){
            minesweeper.getButtons()[location.getRow()][location.getCol()].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));; // set the background a single possible selection to green
            break; // only need to set one possible selection so it breaks out of the loop
        }
    }
}
    

