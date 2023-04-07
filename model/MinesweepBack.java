package minesweeper.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backtracker.Configuration;

public class MinesweepBack implements Configuration {
    private Minesweeper minesweeper;
    private List<Location> locations;

    public MinesweepBack(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        locations = new ArrayList<>();
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<Configuration>();
        for (int i = 0 ; i < minesweeper.getRows(); i++) {
            for(int j = 0; j < minesweeper.getCols();j++){
                Minesweeper copy = new Minesweeper(minesweeper);
                MinesweepBack newMinesweeper = new MinesweepBack(copy);
                if(minesweeper.getBoard()[i][j] == '-'){
                    try {
                        newMinesweeper.minesweeper.makeSelction(new Location(i, j));
                        locations.add(new Location(i, j));
                    } catch (MinesweeperException e) {
                        
                    e.printStackTrace();
                    }
                    successors.add(newMinesweeper);
                }
                
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        return !(minesweeper.getGameState() == GameState.LOST);
    }

    @Override
    public boolean isGoal() {
        return minesweeper.getGameState() == GameState.WON;
    }
    @Override
    public String toString() {
        return locations.toString();
    }
}
