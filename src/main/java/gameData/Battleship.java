package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Battleship {
    private Tile[] occupiedTiles;

    public static Battleship generateBattleship(Point startPoint) {
        Tile[] occupiedTiles = new Tile[Battleship.battleShipLength()];

        // Will work on something fancy if time permits;
        for (int i = 0; i < occupiedTiles.length; i++) {
            occupiedTiles[i] = new Tile(startPoint.x, startPoint.y++, Tile.SHIP_COVERED_TILE);
        }

        Battleship ship = new Battleship(occupiedTiles);

        return ship;
    }

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
