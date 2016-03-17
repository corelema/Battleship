package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.StateManager;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.GameStarterUtil;
import gameVoiceHandler.intents.handlers.Utils.InstructionsUtil;
import gameVoiceHandler.intents.handlers.Utils.ParametersUtil;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;
import org.apache.commons.lang3.math.NumberUtils;

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

        String speechOutput = GameStarterUtil.incorrectParametersSpeech();
        String repromptText;

        if (intent.getSlot(GRID_SIZE_SLOT) != null) {
            String gridSizeParameter = intent.getSlot(GRID_SIZE_SLOT).getValue();
            if (NumberUtils.isNumber(gridSizeParameter)) {
                stateManager.setGridSize(Integer.parseInt(gridSizeParameter));
                speechOutput = Speeches.GRID_SIZE_GIVEN + stateManager.getGridSize() + ". ";
            }
        } else if (intent.getSlot(NUMBER_OF_SHIPS_SLOT) != null) {
            String numberOfShipsParameters = intent.getSlot(NUMBER_OF_SHIPS_SLOT).getValue();
            if (NumberUtils.isNumber(numberOfShipsParameters)) {
                stateManager.setNumberOfShips(Integer.parseInt(numberOfShipsParameters));
                speechOutput = Speeches.NUMBER_OF_SHIPS_GIVEN + stateManager.getNumberOfShips() + ". ";
            }
        }

        if (stateManager.isGameReadyToBeStarted()) {
            InstructionsUtil.defaultInstructionsRequiredToNoIfQuestionNotAnswered(stateManager);
            GameStarterUtil.startAdvancedGame(gameDataInstance);
            speechOutput += GameStarterUtil.startGameSpeech(stateManager);
            repromptText = Speeches.YOUR_TURN + InstructionsUtil.fireInstructions(stateManager);
        } else {
            String missingOrIncorrectParameterSpeech = ParametersUtil.missingOrIncorrectParameterSpeech(stateManager);
            speechOutput += missingOrIncorrectParameterSpeech;
            repromptText = missingOrIncorrectParameterSpeech;
        }

        stateManager.setLastQuestionAsked(speechOutput);
        stateManager.setLastReprompt(repromptText);

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }
}