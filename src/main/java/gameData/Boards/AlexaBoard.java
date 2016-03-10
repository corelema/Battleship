package gameData.Boards;

import gameData.AttackResponse;
import gameData.Battleships.Battleship;
import gameData.Utils.CoordinatesUtil;
import gameData.enums.Orientation;
import gameData.enums.TileState;

/**
 * Created by corentinl on 2/2/16.
 */
public class AlexaBoard extends BoardAbstract {
    public AlexaBoard() {};

    public AlexaBoard(int nbRows, int nbColumns) {
        super(nbRows, nbColumns);
    }

    @Override
    public void resetBoard() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                this.tiles[x][y] = TileState.WATER_U;
            }
        }

        numberOfHitsNecessary = 0;
        numberOfHitsReceived = 0;
    }

    /**
     * Add the battleships to the board if possible.
     * If not possible, the board will be reset.
     *
     * @param	battleShips the list of the battleships to add
     * @return				true if the boats are not overlapping, false otherwise
     */
    public boolean addBattleShips(java.util.List<Battleship> battleShips) {
        for (Battleship battleship : battleShips) {
            if (!addBattleship(battleship)) {
                return false;
            }
        }
        return true;
    }

    public boolean addBattleship(Battleship battleship) {
        numberOfHitsNecessary += battleship.getSize();
        if (battleship.getOrientation() == Orientation.HORIZONTAL) {
            if (!addBattleShipHorizontally(battleship)) {
                resetBoard();
                return false;
            }
        } else {
            if (!addBattleShipVertically(battleship)) {
                resetBoard();
                return false;
            }
        }
        return true;
    }

    private boolean addBattleShipHorizontally(Battleship battleship) {
        Coordinates origin = battleship.getOrigin();

        for (int y = 0 ; y < battleship.getSize() ; y++) {
            if (tiles[origin.x][origin.y + y] == TileState.WATER_U) {
                tiles[origin.x][origin.y + y] = TileState.BOAT_U;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean addBattleShipVertically(Battleship battleship) {
        Coordinates origin = battleship.getOrigin();

        for (int x = 0 ; x < battleship.getSize() ; x++) {
            if (tiles[origin.x + x][origin.y] == TileState.WATER_U) {
                tiles[origin.x + x][origin.y] = TileState.BOAT_U;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Attack the specified tile.
     * This method is to use only on Alexa's board.
     *
     * @param	coordinates     The coordinates where to port the attack
     * @return	                An AttackResponse object containing whether the attack was possible and successful
     */
    public AttackResponse fireAtCoordinates(Coordinates coordinates) {
        TileState tile = this.tiles[coordinates.x][coordinates.y];
        boolean areCoordinatesInBounds = CoordinatesUtil.coordinatesAreInBoud(tiles.length, tiles[0].length, coordinates.x, coordinates.y);
        boolean canAttack = (tile == TileState.WATER_U || tile == TileState.BOAT_U);
        boolean attackSuccessful = (tile == TileState.BOAT_U);

        if (canAttack) {
            if (attackSuccessful) {
                tiles[coordinates.x][coordinates.y] = TileState.BOAT_D;
                numberOfHitsReceived++;
            } else {
                tiles[coordinates.x][coordinates.y] = TileState.WATER_D;
            }
        }

        return new AttackResponse(areCoordinatesInBounds, canAttack, attackSuccessful);
    }
}
