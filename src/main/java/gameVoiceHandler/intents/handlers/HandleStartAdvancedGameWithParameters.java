package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.GameParameters;
import gameData.StateManager;
import gameVoiceHandler.intents.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleStartAdvancedGameWithParameters implements HandlerInterface {
    private static final String GRID_SIZE_SLOT = "gridSize";
    private static final String NUMBER_OF_SHIPS_SLOT = "numberOfShips";

    //TODO: Merge this class with HandleGameParameter

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (!isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.initializationUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();

        Slot gridSizeSlot = intent.getSlot(GRID_SIZE_SLOT);
        Slot numberOfShipsSlot = intent.getSlot(NUMBER_OF_SHIPS_SLOT);

        try {
            if (gridSizeSlot != null && numberOfShipsSlot != null) {
                stateManager.setGridSize(Integer.parseInt(gridSizeSlot.getValue()));
                stateManager.setNumberOfShips(Integer.parseInt(numberOfShipsSlot.getValue()));
            }
        } catch (NumberFormatException e) {
            String speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (stateManager.isGameReadyToBeStarted()) {
            return startAdvancedGame(gameDataInstance);
        } else {
            String speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }

    private static SpeechletResponse startAdvancedGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        if (!stateManager.isGamesStarted()) { //TODO: check if I can remove
            stateManager.startAdvancedGame();

            GameParameters gameParameters = stateManager.generateGameParameters();
            GameManager gameManager = new GameManager(gameParameters);

            gameDataInstance.setGameManager(gameManager);

            String speechOutput = SharedSpeeches.startGameSpeech(stateManager) + Speeches.PROMPT_LINE_COLUMN;
            String repromptText = Speeches.PROMPT_LINE_COLUMN;
            //lastQuestion = repromptText;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        }

        String speechOutput = Speeches.GAME_ALREADY_STARTED;
        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);//lastQuestion, false);
    }
}
