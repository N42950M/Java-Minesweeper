package minesweeper.model;

public enum GameState {
    NOT_STARTED,
    IN_PROGRESS,
    WON,
    LOST;

    /**
     * @return the string representation of the game state
     */
    @Override
    public String toString() {
        String s = " ";
        if(this == NOT_STARTED)
            s = "Game not started ";
        else if(this == IN_PROGRESS)
            s = "Game in Progress";
        else if(this == WON)
            s = "You Win!";
        else if( this == LOST)
            s = "You Lost!";
        return s;

    }
}
