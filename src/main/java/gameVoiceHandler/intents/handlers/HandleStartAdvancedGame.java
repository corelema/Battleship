package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.StateManager;
import gameVoiceHandler.intents.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
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

        if (!stateManager.isGamesStarted()) { //TODO: check that it is not needed and remove
            stateManager.advancedGameAsked();
            String speechOutput = Speeches.ADVANCED_GAME_LAUNCH + Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;

            String repromptText = Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;
            //lastQuestion = repromptText;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            String speechOutput = Speeches.GAME_ALREADY_STARTED;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);//lastQuestion, false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesBeingInitialized();
    }
}
