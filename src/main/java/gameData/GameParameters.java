package gameData;

import gameData.enums.Difficulty;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameParameters {
    private int nbRows;
    private int nbColumns;
    private int numberOfBattleShips;
    private Difficulty difficulty;

    public GameParameters() {
        this.nbRows = 3;
        this.nbColumns = 3;
        this.numberOfBattleShips = 2;
        this.difficulty = Difficulty.EASY;
    }

    public GameParameters(int nbRows, int nbColumns, int numberOfBattleShips, Difficulty difficulty) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.numberOfBattleShips = numberOfBattleShips;
        this.difficulty = difficulty;
    }

    /*GETTERS AND SETTERS*/

    public int getNbRows() {
        return nbRows;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public int getNumberOfBattleShips() {
        return numberOfBattleShips;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
