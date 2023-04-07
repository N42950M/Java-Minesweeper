package minesweeper.model;

public interface MinesweeperObserver {

    //method to be implemented that updates cells
    public void cellUpdated(Location location);
}