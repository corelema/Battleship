package gameData;

import java.awt.*;
import java.util.Random;

/**
 * Created by paul.moon on 1/28/16.
 */
public class GameManager {
    private GameParameters parameters;
    private Board playerOneBoard;  // User
    private Board alexaBoard;  // Alexa for now

    private Point alexasLastAttackPoint;
    private int numberOfHits;
    private int numberOfHitsByPlayer;

    public GameManager(GameParameters parameters) {
        this.parameters   = parameters;
        this.numberOfHits = 0;
        numberOfHitsByPlayer = 0;

        initGame();
    }

    private void initGame() {
        initGameBoards();
        initBattleShips();
    }

    private void initGameBoards() {
        this.playerOneBoard = new Board(parameters.getRows(), parameters.getColumns());
        this.alexaBoard = new Board(parameters.getRows(), parameters.getColumns());
    }

    private void initBattleShips() {
        alexaBoard.setBattleShips(this.generateBattleShipsForPlayer());

        /*
        if (this.parameters.getNumberOfPlayers() > 1) {
            alexaBoard.setBattleShips(this.generateBattleShipsForPlayer());
        } else {
            alexaBoard.setBattleShips(new Battleship[0]);
        }
        */
    }

    private Battleship[] generateBattleShipsForPlayer() {
        Battleship[] ships = new Battleship[parameters.getNumberOfBattleShips()];
        for (int i = 0; i < parameters.getNumberOfBattleShips(); i++) {
            ships[i] = Battleship.generateBattleship(this.randomlyGeneratedPoint(), this.parameters.getRows(), this.parameters.getColumns());
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
        AttackResponse response = this.fireAtPoint(point, this.alexaBoard);

        if (response.isCanAttack() && response.isAttackSuccessful()) {
            numberOfHitsByPlayer++;
        }

        return response;
    }

    public Point getNextAlexaHit() {
        this.alexasLastAttackPoint = this.randomlyGeneratedPoint();
        this.fireAtPoint(this.alexasLastAttackPoint, this.playerOneBoard);

        return this.alexasLastAttackPoint;
    }

    public void didAlexaHit(boolean wasHit) {
        String status = wasHit ? Tile.BATTLESHIP_HIT_TILE : Tile.FIRED_UPON_TILE;

        if (wasHit) {
            numberOfHits++;
        }

        //this.playerOneBoard.updateTileStatus(status, this.alexasLastAttackPoint, new Battleship(new Tile[2]));
    }

    public boolean isGameOver() {
        return (playerOneBoard.areAllBattleShipsSunk() || alexaBoard.areAllBattleShipsSunk() || this.numberOfHits >= 1 || this.numberOfHitsByPlayer >= 1);
    }

    public boolean didPlayerOneWin() {
        return alexaBoard.areAllBattleShipsSunk();
    }

    public boolean didPlayerTwoWin() {
        return playerOneBoard.areAllBattleShipsSunk();
    }

    public boolean didAlexWin() {
        return this.numberOfHits >= 1;
    }


    private AttackResponse fireAtPoint(Point point, Board board) {
        return board.fireAtPoint(point);
    }
}
