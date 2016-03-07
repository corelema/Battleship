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

        if (!stateManager.isGamesStarted()) {
            String speechOutput = Speeches.IM_SORRY + Speeches.NOT_IMPLEMENTED;//Speeches.HELP_SPEECH_BEGINNING + Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

            //String repromptText = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        } else {
            //TODO: determine the current state and give appropriate help.
            //TODO: and help wasn't asked but result in a call from another handler, give an error message. Maybe the whole error process should be handled in a different method or class
            return null;
        }
    }
}
