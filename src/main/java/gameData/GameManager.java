package gameData;

import java.awt.*;
import java.util.Random;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameManager {
    private GameParameters parameters;
    private Board playerOneBoard;  // User
    private Board playerTwoBoard;  // Alexa for now

    private Point alexasLastAttackPoint;

    public GameManager(GameParameters parameters) {
        this.parameters = parameters;

        initGame();
    }

    private void initGame() {
        initGameBoards();
        initBattleShips();
    }

    private void initGameBoards() {
        this.playerOneBoard = new Board(parameters.getRows(), parameters.getColumns());
        this.playerTwoBoard = new Board(parameters.getRows(), parameters.getColumns());
    }

    private void initBattleShips() {
        playerOneBoard.setBattleShips(this.generateBattleShipsForPlayer());
        playerTwoBoard.setBattleShips(this.generateBattleShipsForPlayer());
    }

    private Battleship[] generateBattleShipsForPlayer() {
        Battleship[] ships = new Battleship[parameters.getNumberOfBattleShips()];
        for (int i = 0; i < parameters.getNumberOfBattleShips(); i++) {
            ships[i] = this.generateBattleShip();
        }

        return ships;
    }

    private Battleship generateBattleShip() {
        Tile[] occupiedTiles = new Tile[Battleship.battleShipLength()];
        Point startPoint     = this.randomlyGeneratedPoint();

        // Will work on something fancy if time permits;
        for (int i = 0; i < occupiedTiles.length; i++) {
            occupiedTiles[i] = new Tile(startPoint.x, startPoint.y++);
        }

        Battleship ship = new Battleship(occupiedTiles);

        return ship;
    }

    private Point randomlyGeneratedPoint() {
        Random randomNumber = new Random();

        int startX = randomNumber.nextInt(this.parameters.getRows());
        int startY = randomNumber.nextInt(this.parameters.getColumns());

        return new Point(startX, startY);
    }

    public AttackResponse fireAtPoint(Point point) {
        return this.fireAtPoint(point, this.playerTwoBoard);
    }

    public Point getNextAlexaHit() {
        this.alexasLastAttackPoint = this.randomlyGeneratedPoint();
        this.fireAtPoint(this.alexasLastAttackPoint, this.playerOneBoard);

        return this.alexasLastAttackPoint;
    }

    public void didAlexaHit(boolean wasHit) {
        String status = wasHit ? Tile.BATTLESHIP_HIT_TILE : Tile.FIRED_UPON_TILE;
        this.playerOneBoard.updateTileStatus(status, this.alexasLastAttackPoint);
    }

    public boolean isGameOver() {
        return (playerOneBoard.areAllBattleShipsSunk() || playerTwoBoard.areAllBattleShipsSunk());
    }

    private AttackResponse fireAtPoint(Point point, Board board) {
        return board.fireAtPoint(point);
    }
}
