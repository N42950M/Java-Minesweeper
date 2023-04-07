package minesweeper.model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

//tests the location class
@Testable
public class TestLocation {

    //tests get row
    @Test
    public void testgetRow(){
        Location loc = new Location(5, 3);
        assert loc.getRow() == 5;
    }

    //tests get column
    @Test
    public void testgetCol(){
        Location loc = new Location(5, 3);
        assert loc.getCol() == 3;
    }

    //tests if the hash code works
    @Test
    public void testhashcode(){
        Location loc = new Location(5, 3);
        assert loc.hashCode() == ((5*31)+3);
    }

    //tests if the to string works
    @Test
    public void testToString()
    {
        Location loc = new Location(5, 3);
        assert loc.toString().equals("5,3");
    }

    //tests if equals works
    @Test
    public void testequals()
    {
        Location loc = new Location(5, 3);
        Location loc2 = new Location(5, 3);
        assert loc.equals(loc2);
    }
}
