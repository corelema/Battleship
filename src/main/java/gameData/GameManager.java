package gameData;

import java.awt.*;
import java.util.Random;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameManager {
    private GameParameters parameters;
    private Board playerOneBoard;
    private Board playerTwoBoard;

    public GameManager(GameParameters parameters) {
        this.parameters = parameters;

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
        this.playerOneBoard = new Board(parameters.getRows(), parameters.getColumns());
        this.playerTwoBoard = new Board(parameters.getRows(), parameters.getColumns());
    }

    private void initBattleShips() {
        Battleship[] ships = new Battleship[parameters.getNumberOfBattleShips()];
        for (int i = 0; i < parameters.getNumberOfBattleShips(); i++) {
           ships[i] = this.generateBattleship();
        }
    }

    private Battleship generateBattleship() {
        Tile[] occupiedTiles = new Tile[Battleship.battleShipLength()];
        Point startPoint     = this.randomlyGeneratedStartingPointForShip(occupiedTiles.length);

        // Will work on something fancy if time permits;
        for (int i = 0; i < occupiedTiles.length; i++) {
            occupiedTiles[i] = new Tile(startPoint.x, startPoint.y++);
        }

        Battleship ship = new Battleship(occupiedTiles);

        return ship;
    }

    private Point randomlyGeneratedStartingPointForShip(int length) {
        Random randomNumber = new Random();

        int startX = randomNumber.nextInt(this.parameters.getRows());
        int startY = randomNumber.nextInt(this.parameters.getColumns());

        return new Point(startX, startY);
    }
}
