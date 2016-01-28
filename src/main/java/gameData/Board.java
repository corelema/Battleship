package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Board {
    private Tile[][] tiles;
    private Battleship[] battleships;

    public Board(int rows, int columns) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                tiles[x][y] = new Tile(x,y);
            }
        }
    }

    public void setBattleShips(Battleship[] battleShips) {
        for (Battleship battleship : battleShips) {
            for (Tile selectedTile : battleship.getOccupiedTiles()) {
                selectedTile.setTileState(Tile.SHIP_COVERED_TILE);
            }
        }
    }

    public AttackResponse fireAtPoint(Point tile) {
        Tile space = tiles[tile.x][tile.y];
        boolean canAttack = false;
        boolean attackSuccessful = false;

        if (!space.hasBeenHit() || !space.hasBeenFiredUpon()) {
            canAttack = true;

            if (space.containsShip()) {
                attackSuccessful = true;
            }
        }

        return new AttackResponse(canAttack, attackSuccessful);
    }

    public void updateTileStatus(String status, Point attackPoint) {
        Tile space = tiles[attackPoint.x][attackPoint.y];

        space.setTileState(status);
    }
}
