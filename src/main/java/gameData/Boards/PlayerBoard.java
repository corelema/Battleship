package gameData.Boards;

import gameData.enums.TileState;

import java.awt.*;

/**
 * Created by corentinl on 2/2/16.
 */
public class PlayerBoard extends BoardAbstract {
    public PlayerBoard() {
    }

    public PlayerBoard(int nbRows, int nbColumns) {
        super(nbRows, nbColumns);
    }

    @Override
    public void resetBoard() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                this.tiles[x][y] = TileState.UNKNOWN;
            }
        }

        numberOfHitsNecessary = 0;
        numberOfHitsReceived = 0;
    }

    /**
     * Update the tile after the player's answer
     * This method is to use only on Alexa's board.
     *
     * @param	coordinates     The coordinates where to port the attack
     */
    public void updateTileWithAttackResult(Coordinates coordinates, boolean wasHit) {
        if (coordinates.x >= 0
                && coordinates.x < tiles.length
                && coordinates.y >= 0
                && coordinates.y < tiles[0].length) {
            if (wasHit) {
                tiles[coordinates.x][coordinates.y] = TileState.BOAT_D;
                numberOfHitsReceived++;
            } else {
                tiles[coordinates.x][coordinates.y] = TileState.WATER_D;
            }
        }
    }
}
