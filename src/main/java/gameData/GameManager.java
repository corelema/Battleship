package gameData;

import com.fasterxml.jackson.annotation.JsonProperty;
import gameData.Battleships.ShipGenerator;
import gameData.fireAlgorithms.FireAlgorithmRandom;
import gameData.fireAlgorithms.FireAlgorithmAbstract;
import gameData.Battleships.Battleship;
import gameData.Boards.AlexaBoard;
import gameData.Boards.PlayerBoard;

import java.awt.Point;
import java.util.List;


/**
 * Created by paul.moon on 1/28/16.
 */
public class GameManager {
    //TODO: Check if I need the JsonProperty, and if I need the getter and setters
    @JsonProperty
    private GameParameters parameters;
    @JsonProperty
    private PlayerBoard playerOneBoard;
    @JsonProperty
    private AlexaBoard alexaBoard;
    private FireAlgorithmAbstract fireAlgorithm;
    @JsonProperty
    private Point lastAlexaAttackCoordinates;
    @JsonProperty
    private int lastPlayerAttackXCoordinate;
    @JsonProperty
    private int lastPlayerAttackYCoordinate;

    private static String lastQuestionAsked;

    public GameManager() {
    }

    public GameManager(GameParameters parameters) {
        this.parameters = parameters;
        initGame();
    }

    private void initGame() {
        initGameBoards();
        initBattleShips();
        initFireAlgorithm();
    }

    private void initGameBoards() {
        playerOneBoard = new PlayerBoard(parameters.getNbRows(), parameters.getNbColumns());
        alexaBoard = new AlexaBoard(parameters.getNbRows(), parameters.getNbColumns());
    }

    private void initBattleShips() {
        List<Battleship> randomBattleships = new ShipGenerator().generateDefaultShips();
        alexaBoard.addBattleShips(randomBattleships);
    }

    private void initFireAlgorithm() {
        if (alexaBoard != null) { //TODO: Make sure that initFireAlgorithm is called after initGameBoards
            fireAlgorithm = new FireAlgorithmRandom(parameters.getNbRows(), parameters.getNbColumns(), alexaBoard);
        }
    }

    public AttackResponse fireAtPoint(Point coordinates) {
        return alexaBoard.fireAtPoint(coordinates);
    }

    public Point nextAlexaHit() {
        this.lastAlexaAttackCoordinates = this.fireAlgorithm.getNextHit();

        return lastAlexaAttackCoordinates;
    }

    public void giveResultForAlexasTurn(boolean wasHit) {
        this.playerOneBoard.updateTileWithAttackResult(lastAlexaAttackCoordinates, wasHit);
    }

    public void didAlexaHit(boolean wasHit) {
        this.playerOneBoard.updateTileWithAttackResult(lastAlexaAttackCoordinates, wasHit);
    }

    public boolean gameIsOver() {
        return (playerOneBoard.areAllBattleShipsSunk() || alexaBoard.areAllBattleShipsSunk());
    }

    public boolean didAlexaWin() {
        return playerOneBoard.areAllBattleShipsSunk();
    }

    /**GETTERS AND SETTERS**/

    public GameParameters getParameters() {
        return parameters;
    }

    public String getLastQuestionAsked() {
        return lastQuestionAsked;
    }

    public void setLastQuestionAsked(String lastQuestionAsked) {
        this.lastQuestionAsked = lastQuestionAsked;
    }

    public int getLastPlayerAttackYCoordinate() {
        return lastPlayerAttackYCoordinate;
    }

    public void setLastPlayerAttackYCoordinate(int lastPlayerAttackYCoordinate) {
        this.lastPlayerAttackYCoordinate = lastPlayerAttackYCoordinate;
    }

    public int getLastPlayerAttackXCoordinate() {
        return lastPlayerAttackXCoordinate;
    }

    public void setLastPlayerAttackXCoordinate(int lastPlayerAttackXCoordinate) {
        this.lastPlayerAttackXCoordinate = lastPlayerAttackXCoordinate;
    }
}
