package minesweeper.model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

//tests the game state more than in TestMinesweeper
@Testable
public class TestGameState {
    
    //tests game state with all possible enums
    @Test
    public void testGameState(){
        GameState state = GameState.NOT_STARTED;
        assert(state.toString().equals("Game not started "));
        state = GameState.IN_PROGRESS;
        assert(state.toString().equals("Game in Progress"));
        state = GameState.WON;
        assert(state.toString().equals("You Win!"));
        state = GameState.LOST;
        assert(state.toString().equals("You Lost!"));
    }
}
