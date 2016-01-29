package gameVoiceHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corentinl on 1/23/16.
 */
public class StateManager {
    private static StateManager instance = null;

    private int gridSize;
    private int numberOfShips;

    private VoiceState voiceState;
    private TurnState turnState;


    private StateManager() {
        gridSize = -1;
        numberOfShips = -1;
        voiceState = VoiceState.INITIALIZATION;
        turnState = TurnState.PLAYER;

    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public void reset() {
        gridSize = -1;
        numberOfShips = -1;
        voiceState = VoiceState.INITIALIZATION;
        turnState = TurnState.PLAYER;
    }

    public boolean isGameReadyToBeStarted() {
        return (gridSize > 0 && numberOfShips > 0);
    }

    public void startQuickGame() {
        this.gridSize = 3;
        this.numberOfShips = 1;
        voiceState = VoiceState.QUICK_GAME_STARTED;
        turnState = TurnState.PLAYER;
    }

    public void advancedGameAsked() {
        voiceState = VoiceState.ADVANCED_GAME_ASKED;
    }

    public void startAdvancedGame() {
        voiceState = VoiceState.ADVANCED_GAME_STARTED;
        turnState = TurnState.PLAYER;
    }

    public boolean isGamesStarted(){
        return (voiceState == VoiceState.QUICK_GAME_STARTED || voiceState == VoiceState.ADVANCED_GAME_STARTED);
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

    public VoiceState getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(VoiceState voiceState) {
        this.voiceState = voiceState;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }
}
