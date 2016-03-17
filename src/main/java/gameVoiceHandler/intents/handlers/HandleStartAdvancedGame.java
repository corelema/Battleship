package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.StateManager;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.InstructionsUtil;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleStartAdvancedGame implements HandlerInterface {
    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (!isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.initializationUnexpected();
        }

        StateManager stateManager = new StateManager();

        stateManager.advancedGameAsked();
        String speechOutput = Speeches.ADVANCED_GAME_LAUNCH + Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;
        String instructions = InstructionsUtil.advancedGameParametersInstructionsIfRequired(stateManager);
        speechOutput = instructions == null ? speechOutput : speechOutput + instructions;

        String repromptText = Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;

        stateManager.setLastQuestionAsked(speechOutput); //TODO: Check why this always contains the instructions even if we answered no
        stateManager.setLastReprompt(repromptText);

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }
}
