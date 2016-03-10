package gameData.Boards;


import com.fasterxml.jackson.annotation.JsonProperty;
import gameData.Battleships.Battleship;
import gameData.Utils.CoordinatesUtil;
import gameData.enums.Orientation;
import gameData.GameCommon;
import gameData.enums.TileState;

/**
 * Created by paul.moon on 1/28/16.
 */
public abstract class BoardAbstract {
    @JsonProperty
    protected TileState[][] tiles;
    @JsonProperty
    protected int numberOfHitsNecessary;
    @JsonProperty
    protected int numberOfHitsReceived;

    /**
     * Default constructor
     * Instantiates the board with 3 rows/columns
     * Set all the tiles to TileState.WATER_U
     *
     * @return  the instantiated object
     */
    public BoardAbstract() {
        this.tiles = new TileState[GameCommon.minNumberRows][GameCommon.minNumberColumns];
        resetBoard();
    }

    public BoardAbstract(int nbRows, int nbColumns) {
        nbRows = nbRows >= GameCommon.minNumberRows ? nbRows : GameCommon.minNumberRows;
        nbColumns = nbColumns >= GameCommon.minNumberColumns ? nbColumns : GameCommon.minNumberColumns;
        this.tiles = new TileState[nbRows][nbColumns];
        resetBoard();
    }

    protected abstract void resetBoard();

    public boolean checkSpaceIsFree(Battleship battleship) {
        if (battleship.getOrientation() == Orientation.HORIZONTAL) {
            return checkHorizontalSpaceIsFree(battleship.getOrigin(), battleship.getSize());
        } else {
            return checkVerticalSpaceIsFree(battleship.getOrigin(), battleship.getSize());
        }
    }

    public TileState checkState(int x, int y) {
        TileState state = null;

        if (CoordinatesUtil.coordinatesAreInBoud(tiles.length, tiles[0].length, x, y)) {
            state = tiles[x][y];
        }

        return state;
    }

    private boolean checkVerticalSpaceIsFree(Coordinates origin, int shipSize) {
        for (int i = 0 ; i < shipSize ; i++) {
            if (tiles[origin.x + i][origin.y] == TileState.BOAT_U) {
                return false;
            }
        }
        return true;
    }

    private boolean checkHorizontalSpaceIsFree(Coordinates origin, int shipSize) {
        for (int i = 0 ; i < shipSize ; i++) {
            if (tiles[origin.x][origin.y + i] == TileState.BOAT_U) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int x = 0; x < tiles.length; x++) {
            s.append("[ ");
            for (int y = 0; y < tiles[0].length; y++) {
                s.append(tiles[x][y]).append(" , ");
            }
            s.replace(s.length() - 3, s.length(), " ]");
            s.append("\n");
        }

        return s.toString();
    }

    public boolean areAllBattleShipsSunk() {
        return numberOfHitsReceived == numberOfHitsNecessary;
    }

    /**GETTERS AND SETTERS**/

    public int getNumberOfHitsNecessary() {
        return numberOfHitsNecessary;
    }

    public void setNumberOfHitsNecessary(int numberOfHitsNecessary) {
        this.numberOfHitsNecessary = numberOfHitsNecessary;
    }
}
