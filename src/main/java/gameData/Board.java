package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class Board {
    private Tile[][] tiles;
    private Battleship[] battleships;

    public Board(int rows, int columns) {
        this.tiles = new Tile[rows][columns];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                this.tiles[x][y] = new Tile(x,y);
            }
        }
    }

    public void setBattleShips(Battleship[] battleShips) {
        this.battleships = battleShips;

        for (Battleship battleship : battleShips) {
            for (Tile occupiedTile : battleship.getOccupiedTiles()) {
                this.updateTileStatus(Tile.SHIP_COVERED_TILE, new Point(occupiedTile.getX(), occupiedTile.getY()), battleship);
            }
        }
    }

    public AttackResponse fireAtPoint(Point point) {
        Tile space = this.tiles[point.x][point.y];
        boolean canAttack = false;
        boolean attackSuccessful = false;

        if (!space.hasBeenHit() && !space.hasBeenFiredUpon()) {
            canAttack = true;

            if (space.containsShip()) {
                attackSuccessful = true;
                space.setTileState(Tile.BATTLESHIP_HIT_TILE);
                space.getShip().battleshipHit();
            } else {
                space.setTileState(Tile.FIRED_UPON_TILE);
            }
        }

        return new AttackResponse(canAttack, attackSuccessful);
    }

    public void updateTileStatus(String status, Point attackPoint, Battleship battleship) {
        Tile space = this.tiles[attackPoint.x][attackPoint.y];

        space.setTileState(status);
        space.setBattleShip(battleship);
    }

    public boolean areAllBattleShipsSunk() {
        boolean allShipsSunk = true;

        if (this.battleships.length > 0) {
            for (Battleship ship : this.battleships) {
                if (!ship.isSunk()) {
                    allShipsSunk = false;
                    break;
                }
            }
        } else {
            allShipsSunk = false;
        }

        return allShipsSunk;
    }
}
