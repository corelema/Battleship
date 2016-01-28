package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Battleship {
    private Tile[] occupiedTiles;

    public Battleship(Tile[] occupiedTiles) {
        this.occupiedTiles = occupiedTiles;
    }

    public boolean isSunk() {
        boolean isSunk = true;
        for (Tile tile : occupiedTiles) {
            if (!tile.hasBeenHit()) {
                isSunk = false;
            }
        }

        return isSunk;
    }

    public Tile[] getOccupiedTiles() {
        return occupiedTiles;
    }

    static public int battleShipLength() {
        return 2;
    }
}
