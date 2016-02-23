package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
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
public class HandleGameParameter implements HandlerInterface {
    private static final String GRID_SIZE_SLOT = "gridSize";
    private static final String NUMBER_OF_SHIPS_SLOT = "numberOfShips";

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (!isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.parameterizationAdvancedGameUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();

        String speechOutput = "";
        try {
            if (intent.getSlot(GRID_SIZE_SLOT) != null) {
                stateManager.setGridSize(Integer.parseInt(intent.getSlot(GRID_SIZE_SLOT).getValue()));
                speechOutput = Speeches.GRID_SIZE_GIVEN + stateManager.getGridSize() + ". ";
            } else if (intent.getSlot(NUMBER_OF_SHIPS_SLOT) != null) {
                stateManager.setNumberOfShips(Integer.parseInt(intent.getSlot(NUMBER_OF_SHIPS_SLOT).getValue()));
                speechOutput = Speeches.NUMBER_OF_SHIPS_GIVEN + stateManager.getNumberOfShips() + ". ";
            } else {
                return SpeechesGenerator.newTellResponse(Speeches.ERROR);
            }
        } catch (NumberFormatException e) {
            speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (stateManager.isGameReadyToBeStarted()) {
            if (stateManager.isGameReadyToBeStarted()) {
                return startAdvancedGame(gameDataInstance);
            } else {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }

        } else {
            if (stateManager.isGridSizeCorrect()) {
                speechOutput += Speeches.PROMPT_NUMBER_OF_SHIPS_ONLY;
            } else {
                speechOutput += Speeches.PROMPT_GRID_SIZE_ONLY;
            }
        }

        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isAdvancedGameBeingInitizlized();
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