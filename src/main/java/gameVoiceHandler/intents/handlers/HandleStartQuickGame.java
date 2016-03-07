package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.StateManager;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.GameStarterUtil;
import gameVoiceHandler.intents.handlers.Utils.InstructionsUtil;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleStartQuickGame implements HandlerInterface {
    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (!isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.initializationUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();
        InstructionsUtil.defaultInstructionsRequiredToNoIfQuestionNotAnswered(stateManager);

        String speechOutput = GameStarterUtil.startQuickGame(gameDataInstance);
        String repromptText = Speeches.YOUR_TURN + InstructionsUtil.fireInstructions(stateManager);

        gameDataInstance.getGameManager().setLastQuestionAsked(repromptText);

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }
}
