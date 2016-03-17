package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.StateManager;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleHelp implements HandlerInterface {
    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();

        String speechOutput = stateManager.getLastQuestionAsked();
        String repromptText = stateManager.getLastReprompt();

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }
}
