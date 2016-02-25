package gameVoiceHandler.intents.handlers.Utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class BadIntentUtil {
    public static SpeechletResponse initializationUnexpected() {
        String speechOutput = Speeches.NO_INITIALIZATION;
        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }

    public static SpeechletResponse parameterizationAdvancedGameUnexpected() {
        String speechOutput = Speeches.NO_ADVANCED_GAME_SETUP;
        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }

    public static SpeechletResponse fireUnexpected() {
        String speechOutput = Speeches.NO_FIRE_YET;
        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }
}
