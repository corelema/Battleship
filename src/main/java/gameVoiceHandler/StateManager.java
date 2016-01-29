package gameVoiceHandler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by corentinl on 1/23/16.
 */
public class StateManager {
    public static final String INITIALIZATION = "INITIALIZATION";
    public static final String QUICK_GAME_STARTED = "QUICK_GAME_STARTED";
    public static final String ADVANCED_GAME_ASKED = "ADVANCED_GAME_ASKED";
    public static final String ADVANCED_GAME_STARTED = "ADVANCED_GAME_STARTED";

    public static final String PLAYER = "PLAYER";
    public static final String ALEXA = "ALEXA";

    @JsonProperty
    private int gridSize;
    @JsonProperty
    private int numberOfShips;
    @JsonProperty
    private String voiceState;
    @JsonProperty
    private String turnState;

    public StateManager(@JsonProperty("gridSize") int gridSize, @JsonProperty("numberOfShips") int numberOfShips, @JsonProperty("voiceState") String voiceState, @JsonProperty("turnState") String turnState) {
        this.gridSize = gridSize;
        this.numberOfShips = numberOfShips;
        this.voiceState = voiceState;
        this.turnState = turnState;
    }

    public StateManager() {
        gridSize = -1;
        numberOfShips = -1;
        voiceState = INITIALIZATION;
        turnState = PLAYER;

    }


    @JsonIgnore
    public boolean isGameReadyToBeStarted() {
        return (gridSize > 0 && numberOfShips > 0);
    }

    public void startQuickGame() {
        this.gridSize = 3;
        this.numberOfShips = 1;
        voiceState = QUICK_GAME_STARTED;
        turnState = PLAYER;
    }

    public void advancedGameAsked() {
        voiceState = ADVANCED_GAME_ASKED;
    }

    public void startAdvancedGame() {
        voiceState = ADVANCED_GAME_STARTED;
        turnState = PLAYER;
    }

    @JsonIgnore
    public boolean isGamesStarted(){
        return (voiceState.equals(QUICK_GAME_STARTED) || voiceState.equals(VoiceState.ADVANCED_GAME_STARTED));
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

    @JsonIgnore
    public boolean isGridSizeCorrect() {
        return (gridSize > 0);
    }

    @JsonIgnore
    public boolean isNumberOfShipsCorrect() {
        return (numberOfShips > 0);
    }

    public int getGridSize() {
        return gridSize;
    }

    @JsonProperty("gridSize")
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    @JsonProperty("numberOfShips")
    public void setNumberOfShips(int numberOfShips) {
        this.numberOfShips = numberOfShips;
    }

    public String getVoiceState() {
        return voiceState;
    }

    @JsonProperty("voiceState")
    public void setVoiceState(String voiceState) {
        this.voiceState = voiceState;
    }

    public String getTurnState() {
        return turnState;
    }

    @JsonProperty("turnState")
    public void setTurnState(String turnState) {
        this.turnState = turnState;
    }
}
