package gameData;

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

    public void initGame() {
        initGameBoards();
        initBattleShips();
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
