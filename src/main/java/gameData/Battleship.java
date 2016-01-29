package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Battleship {
    private Tile[] occupiedTiles;
    private int timesHit;

    public static Battleship generateBattleship(Point startPoint, int numberOfRows, int numberOfColumns) {
        Tile[] occupiedTiles = new Tile[Battleship.battleShipLength()];

        // Will work on something fancy if time permits;
        for (int i = 0; i < occupiedTiles.length; i++) {
            int column = startPoint.y + i > numberOfColumns - 1 ? startPoint.y - i : startPoint.y + i;
            occupiedTiles[i] = new Tile(startPoint.x, column, Tile.SHIP_COVERED_TILE);
        }

        Battleship ship = new Battleship(occupiedTiles);

        return ship;
    }

    public Battleship(Tile[] occupiedTiles) {
        this.occupiedTiles = occupiedTiles;
    }

    public boolean isSunk() {
        return this.timesHit == this.length();
    }

    public Tile[] getOccupiedTiles() {
        return occupiedTiles;
    }

    public int length() {
        return this.occupiedTiles.length;
    }

    public void battleshipHit() {
        this.timesHit++;
    }

    static public int battleShipLength() {
        return 2;
    }
}
