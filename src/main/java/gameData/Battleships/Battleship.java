package gameData.Battleships;

import gameData.Boards.Coordinates;
import gameData.enums.Orientation;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Battleship {
    private Coordinates origin;
    private int size;
    Orientation orientation;

    public Battleship (Coordinates origin, int size, Orientation orientation) {
        this.origin = origin;
        this.size = size;
        this.orientation = orientation;
    }

    public Coordinates getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinates origin) {
        this.origin = origin;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Battleship: origin = (" + origin.x + ", " + origin.y + "), size = " + size + ", orientation = " + orientation;
    }
}
