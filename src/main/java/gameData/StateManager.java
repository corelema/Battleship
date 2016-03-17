package gameData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameData.enums.Difficulty;
import gameData.enums.TurnState;
import gameData.enums.VoiceState;
import gameVoiceHandler.intents.speeches.Speeches;

/**
 * Created by corentinl on 1/23/16.
 */
public class StateManager {
    //TODO: Extract the GameParameters in its own instance, to avoid duplication between here and GameManager

    private int gridSize;
    private int numberOfShips;
    private VoiceState voiceState;
    private TurnState turnState;
    private Difficulty difficulty;

    private boolean instructionsRequested = true;
    private boolean answerInstructionsRequested = true;
    private boolean fireInstructionsRequested = true;
    private boolean advancedGameParametersInstructionsRequested = true;

    private boolean hitOrMissedQuestionAsked = false;

    private static String lastQuestionAsked = Speeches.WELCOME + Speeches.PROMPT_FOR_INSTRUCTIONS;
    private static String lastReprompt = Speeches.PROMPT_FOR_INSTRUCTIONS;

    public StateManager(int gridSize,
                        int numberOfShips,
                        VoiceState voiceState,
                        TurnState turnState,
                        Difficulty difficulty,
                        boolean instructionsRequested,
                        boolean answerInstructionsRequested,
                        boolean fireInstructionsRequested) {
        this.gridSize = gridSize;
        this.numberOfShips = numberOfShips;
        this.voiceState = voiceState;
        this.turnState = turnState;
        this.difficulty = difficulty;
        this.instructionsRequested = instructionsRequested;
        this.answerInstructionsRequested = answerInstructionsRequested;
        this.fireInstructionsRequested = fireInstructionsRequested;
    }

    public StateManager() {
        gridSize = -1;
        numberOfShips = -1;
        voiceState = VoiceState.PROMPT_FOR_INSTRUCTIONS;
        turnState = TurnState.PLAYER;
        difficulty = Difficulty.EASY;
    }

    @JsonIgnore
    public boolean isGameReadyToBeStarted() {
        return (gridSize >= 3
                && gridSize <= 10
                && numberOfShips > 0
                && numberOfShips <= gridSize);
    }

    public boolean areParametersEntered() {
        return gridSize >= 0 && numberOfShips >= 0;
    }

    public void startQuickGame() {
        this.gridSize = 3;
        this.numberOfShips = 1;
        voiceState = VoiceState.QUICK_GAME_STARTED;
        turnState = TurnState.PLAYER;
    }

    public void advancedGameAsked() {
        voiceState = VoiceState.INITIALIZATION;
    }

    public void startAdvancedGame() {
        voiceState = VoiceState.ADVANCED_GAME_STARTED;
        turnState = TurnState.PLAYER;
    }

    public GameParameters generateGameParameters() {
        return new GameParameters(gridSize, gridSize, numberOfShips, difficulty);
    }

    /**STATE**/

    @JsonIgnore
    public boolean isGamesStarted(){
        return (voiceState.equals(VoiceState.QUICK_GAME_STARTED) || voiceState.equals(VoiceState.ADVANCED_GAME_STARTED));
    }

    @JsonIgnore
    public boolean isGamesBeingInitialized(){
        return (voiceState.equals(VoiceState.INITIALIZATION) || voiceState.equals(VoiceState.PROMPT_FOR_INSTRUCTIONS));
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

    public void resetParameters() {
        this.gridSize = -1;
        this.numberOfShips = -1;
    }

    /**GETTERS AND SETTERS**/

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

    public VoiceState getVoiceState() {
        return voiceState;
    }

    @JsonProperty("voiceState")
    public void setVoiceState(VoiceState voiceState) {
        this.voiceState = voiceState;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    @JsonProperty("turnState")
    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public boolean isInstructionsRequested() {
        return instructionsRequested;
    }

    public void setInstructionsRequested(boolean instructionsRequested) {
        this.instructionsRequested = instructionsRequested;
    }

    public boolean isAnswerInstructionsRequested() {
        return answerInstructionsRequested;
    }

    public void setAnswerInstructionsRequested(boolean answerInstructionsRequested) {
        this.answerInstructionsRequested = answerInstructionsRequested;
    }

    public boolean isFireInstructionsRequested() {
        return fireInstructionsRequested;
    }

    public void setFireInstructionsRequested(boolean fireInstructionsRequested) {
        this.fireInstructionsRequested = fireInstructionsRequested;
    }

    public boolean isAdvancedGameParametersInstructionsRequested() {
        return advancedGameParametersInstructionsRequested;
    }

    public void setAdvancedGameParametersInstructionsRequested(boolean advancedGameParametersInstructionsRequested) {
        this.advancedGameParametersInstructionsRequested = advancedGameParametersInstructionsRequested;
    }

    public String getLastQuestionAsked() {
        return lastQuestionAsked;
    }

    public void setLastQuestionAsked(String lastQuestionAsked) {
        this.lastQuestionAsked = lastQuestionAsked;
    }

    public String getLastReprompt() {
        return lastReprompt;
    }

    public void setLastReprompt(String lastReprompt) {
        StateManager.lastReprompt = lastReprompt;
    }

    public boolean isHitOrMissedQuestionAsked() {
        return hitOrMissedQuestionAsked;
    }

    public void setHitOrMissedQuestionAsked(boolean hitOrMissedQuestionAsked) {
        this.hitOrMissedQuestionAsked = hitOrMissedQuestionAsked;
    }
}
