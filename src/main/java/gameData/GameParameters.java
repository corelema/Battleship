package gameData;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameParameters {
    private int rows;
    private int columns;
    private int numberOfPlayers;
    private int numberOfBattleShips;

    public GameParameters(int rows, int columns, int numberOfPlayers, int numberOfBattleShips) {
        rows                = rows;
        columns             = columns;
        numberOfPlayers     = numberOfPlayers;
        numberOfBattleShips = numberOfBattleShips;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfBattleShips() {
        return numberOfBattleShips;
    }
}
