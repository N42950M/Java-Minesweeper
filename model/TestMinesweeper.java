package minesweeper.model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

//tests the minesweeper class
@Testable
public class TestMinesweeper{
    
    //tests if getting the move count works correctly
    @Test
    public void Testgetmovecount() throws MinesweeperException{
        Minesweeper game = new Minesweeper(3,3,3);
        assert(game.getMoveCount() == 0);
        game.makeSelction(new Location(0,0));
        assert(game.getMoveCount() == 1);
    }

    //tests the game state to see if it works
    @Test
    public void TestgetGameState() throws MinesweeperException{
        Minesweeper game = new Minesweeper(3,3,3,1);
        assert(game.getGameState().toString().equals("Game not started "));
        game.makeSelction(new Location(0,1));
        assert(game.getGameState().toString().equals("You Lost!"));
    }
}
