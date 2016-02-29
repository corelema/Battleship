package gameData;

import com.fasterxml.jackson.annotation.JsonProperty;
import gameData.Battleships.ShipGenerator;
import gameData.Boards.Coordinates;
import gameData.fireAlgorithms.FireAlgorithmRandom;
import gameData.fireAlgorithms.FireAlgorithmAbstract;
import gameData.Battleships.Battleship;
import gameData.Boards.AlexaBoard;
import gameData.Boards.PlayerBoard;

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
    private Coordinates lastAlexaAttackCoordinates = new Coordinates(-1, -1);
    @JsonProperty
    private int lastPlayerAttackXCoordinate;
    @JsonProperty
    private int lastPlayerAttackYCoordinate;
    @JsonProperty
    private static String lastQuestionAsked;
    @JsonProperty
    private static boolean answerInstructionsGiven;

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
        alexaBoard = new AlexaBoard(parameters.getNbRows(), parameters.getNbColumns());
        playerOneBoard = new PlayerBoard(parameters.getNbRows(), parameters.getNbColumns());
    }

    private void initBattleShips() {
        List<Battleship> randomBattleships = new ShipGenerator().generateDefaultShips();
        alexaBoard.addBattleShips(randomBattleships);
        playerOneBoard.setNumberOfHitsNecessary(alexaBoard.getNumberOfHitsNecessary());
    }

    public void initFireAlgorithm() {
        if (playerOneBoard != null) { //TODO: Make sure that initFireAlgorithm is called after initGameBoards
            fireAlgorithm = new FireAlgorithmRandom(parameters.getNbRows(), parameters.getNbColumns(), playerOneBoard);
        }
    }

    public AttackResponse fireAtCoordinates(Coordinates coordinates) {
        return alexaBoard.fireAtCoordinates(coordinates);
    }

    public Coordinates nextAlexaHit() {
        lastAlexaAttackCoordinates = fireAlgorithm.getNextHit();
        return lastAlexaAttackCoordinates;
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
