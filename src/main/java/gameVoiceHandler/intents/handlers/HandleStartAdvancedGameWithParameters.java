package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
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

        String speechOutput = GameStarterUtil.incorrectParametersSpeech();
        String repromptText = speechOutput;

        Slot gridSizeSlot = intent.getSlot(GRID_SIZE_SLOT);
        Slot numberOfShipsSlot = intent.getSlot(NUMBER_OF_SHIPS_SLOT);

        if (gridSizeSlot != null && numberOfShipsSlot != null) {
            String gridSizeParameter = gridSizeSlot.getValue();
            String numberOfShipsParameter = numberOfShipsSlot.getValue();

            if (NumberUtils.isNumber(gridSizeParameter)
                    && NumberUtils.isNumber(numberOfShipsParameter)) {
                stateManager.setGridSize(Integer.parseInt(gridSizeSlot.getValue()));
                stateManager.setNumberOfShips(Integer.parseInt(numberOfShipsSlot.getValue()));

                if (stateManager.isGameReadyToBeStarted()) {
                    InstructionsUtil.defaultInstructionsRequiredToNoIfQuestionNotAnswered(stateManager);
                    GameStarterUtil.startAdvancedGame(gameDataInstance);
                    speechOutput = GameStarterUtil.startGameSpeech(stateManager);
                    repromptText = Speeches.YOUR_TURN + InstructionsUtil.fireInstructions(stateManager);
                } else {
                    ParametersUtil.issueWithParametersSpeech(stateManager);
                }
            }
        }

        stateManager.setLastQuestionAsked(repromptText);

        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }
}
