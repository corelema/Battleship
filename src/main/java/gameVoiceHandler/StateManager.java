package gameVoiceHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corentinl on 1/23/16.
 */
public class StateManager {
    private static StateManager instance = null;

    private boolean isKindOfGameChosen;
    private boolean isGameStarted;
    private int gridSize;
    private int numberOfShips;

    private StateManager() {
        isKindOfGameChosen = false;
        isGameStarted = false;
        gridSize = -1;
        numberOfShips = -1;
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public boolean isGameReadyToBeStarted() {
        return (gridSize > 0 && numberOfShips > 0);
    }

    public void startGame() {
        isGameStarted = true;
    }

    public boolean isGamesStarted(){
        return isGameStarted;
    }

    public String missingParametersSentence() {
        if (isGameReadyToBeStarted()) {
            return null;
        } else {
            String missingParametersSentence = "";
            if (gridSize <= 0) {
                missingParametersSentence = "I am missing the grid size";
            }
            if (numberOfShips <= 0) {
                if (missingParametersSentence.isEmpty()) {
                    missingParametersSentence = "I am missing the number of ships.";
                } else {
                    missingParametersSentence += " and the number of ships.";
                }
            } else {
                missingParametersSentence += ".";
            }

            return missingParametersSentence;
        }
    }

    public boolean isGridSizeCorrect() {
        return (gridSize > 0);
    }

    public boolean isNumberOfShipsCorrect() {
        return (numberOfShips > 0);
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public void setNumberOfShips(int numberOfShips) {
        this.numberOfShips = numberOfShips;
    }
}
