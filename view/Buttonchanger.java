package minesweeper.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import minesweeper.model.Location;
import minesweeper.model.MinesweeperException;

public class Buttonchanger implements EventHandler<ActionEvent> {
    private Location location; // The location of the button that was clicked
    private MinewsweeperGUI minesweeper; // The GUI that this event handler is associated with

    public Buttonchanger(Location location, MinewsweeperGUI minewsweeper) {
        this.location = location;
        this.minesweeper = minewsweeper;
    }
    @Override
    public void handle(ActionEvent arg0) {
        try{
            minesweeper.buttonpressed(location); // call the buttonpressed method in the minesweeper class
        } 
        catch (MinesweeperException e) {
            System.out.println(e);
        }
    }
}
