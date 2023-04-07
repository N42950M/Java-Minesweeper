package minesweeper.model;

public class Location {

    private int row; // The row of the location
    private int col; // The column of the location

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the location
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Returns the column of the location
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Returns the hash code of the location
     */
    @Override
    public int hashCode() {
        return (row * 31) + col;
    }

    /**
     * Returns a string representation of the location
     */
    @Override
    public String toString() {
        return this.row + "," + this.col;
    }

    /**
     * Compares two locations to see if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode(); // if the hash codes are equal, the locations are equal
    }
}
