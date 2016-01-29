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
    private int numberOfHits;

    public GameManager(GameParameters parameters) {
        this.parameters   = parameters;
        this.numberOfHits = 0;

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

        if (this.parameters.getNumberOfPlayers() > 1) {
            playerTwoBoard.setBattleShips(this.generateBattleShipsForPlayer());
        } else {
            playerTwoBoard.setBattleShips(new Battleship[0]);
        }
    }

    private Battleship[] generateBattleShipsForPlayer() {
        Battleship[] ships = new Battleship[parameters.getNumberOfBattleShips()];
        for (int i = 0; i < parameters.getNumberOfBattleShips(); i++) {
            ships[i] = Battleship.generateBattleship(this.randomlyGeneratedPoint());
        }

        return ships;
    }

    private Point randomlyGeneratedPoint() {
        Random randomNumber = new Random();

        int startX = randomNumber.nextInt(this.parameters.getRows());
        int startY = randomNumber.nextInt(this.parameters.getColumns());

        return new Point(startX, startY);
    }

    // TODO: bounds checking
    public AttackResponse fireAtPoint(Point point) {
        return this.fireAtPoint(point, this.playerOneBoard);
    }

    public Point getNextAlexaHit() {
        this.alexasLastAttackPoint = this.randomlyGeneratedPoint();
        this.fireAtPoint(this.alexasLastAttackPoint, this.playerOneBoard);

        return this.alexasLastAttackPoint;
    }

    public void didAlexaHit(boolean wasHit) {
        String status = wasHit ? Tile.BATTLESHIP_HIT_TILE : Tile.FIRED_UPON_TILE;
        this.numberOfHits = wasHit ? this.numberOfHits + 1 : numberOfHits;

        this.playerTwoBoard.updateTileStatus(status, this.alexasLastAttackPoint);
    }

    public boolean isGameOver() {
        return (playerOneBoard.areAllBattleShipsSunk() || playerTwoBoard.areAllBattleShipsSunk() || this.numberOfHits == 2);
    }

    private AttackResponse fireAtPoint(Point point, Board board) {
        return board.fireAtPoint(point);
    }
}
