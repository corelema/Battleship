package gameData;

import java.awt.*;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameManager {
    private GameParameters parameters;
    private Board playerOneBoard;
    private Board playerTwoBoard;

    public GameManager(GameParameters parameters) {
        parameters = parameters;

        initGame();
    }

    private void initGame() {
        initGameBoards();
        initBattleShips();
    }

    public AttackResponse fireAtPoint(Point tile) {
        return null;
    }

    public Point getNextAlexaHit() {
        return null;
    }

    public void didAlexaHit(boolean wasHit) {

    }

    public boolean isGameOver() {
        return false;
    }

    private void initGameBoards() {
        playerOneBoard = new Board(parameters.getRows(), parameters.getColumns());
        playerTwoBoard = new Board(parameters.getRows(), parameters.getColumns());
    }

    private void initBattleShips() {
        for (int i = 0; i < parameters.getNumberOfBattleShips(); i++) {

        }
    }

//    private Battleship generateBattleship() {
//
//        Battleship ship = new Battleship();
//
//        return ship;
//    }

    private int battleShipLength() {
        return 2;
    }
}
