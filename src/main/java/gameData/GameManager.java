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
    @JsonProperty
    private GameParameters parameters;
    @JsonProperty
    private PlayerBoard playerOneBoard;
    @JsonProperty
    private AlexaBoard alexaBoard;
    private FireAlgorithmAbstract fireAlgorithm;
    @JsonProperty
    private Point lastAlexaAttackCoordinates;

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
        //return (playerOneBoard.areAllBattleShipsSunk() || alexaBoard.areAllBattleShipsSunk());
        return false;
    }

    public boolean didPlayerOneWin() {
        //return alexaBoard.areAllBattleShipsSunk();
        return false;
    }

    public boolean didAlexaWin() {
        return playerOneBoard.areAllBattleShipsSunk();
    }
}
