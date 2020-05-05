package com.example.thegameoflife;

/**
 * This class is used to manage the location as well as alive/dead state of a cell being
 * referenced in other parts of the program.
 */

public class CellManager {
    private int xcoord;
    private int ycoord;
    private boolean living;

    public CellManager(int set_xcoord, int set_ycoord, boolean set_living) {
        xcoord = set_xcoord;
        ycoord = set_ycoord;
        living = set_living;
    }

    /**
     * getter for one coordinate of cell.
     * @return coordinate integer value.
     */

    public int getXcoord() {
        return xcoord;
    }

    /**
     * getter for other coordinate of cell.
     * @return coordinate integer value.
     */

    public int getYcoord() {
        return ycoord;
    }

    /**
     * getter for alive/dead state
     * @return boolean indicating whether cell is alive or not.
     */

    public boolean getLiving() {
        return living;
    }

    /**
     * sets cell to alive state to represent on screen as white.
     */

    public void newBorn() {
        living = true;
    }

    /**
     * reverses alive/dead state of cell.
     */

    public void reverse() {
        living = !living;
    }

    /**
     * sets cell to dead state to represent on screen as black.
     */

    public void death() {
        living = false;
    }
}
