package gameVoiceHandler;

import com.amazon.speech.speechlet.SpeechletResponse;

/**
 * Created by corentinl on 1/23/16.
 */
public class OrdersTranslator {
    public static SpeechletResponse handleFirstEventRequest() {
        return null;
    }

    public static SpeechletResponse handleQuickGameAsked() {
        return null;
    }

    public static SpeechletResponse handleParameteredGameAsked(int size, int numberOfShips) {
        return null;
    }

    public static SpeechletResponse handleFireCoordinatesGiven(int x, int y) {
        return null;
    }

    public static SpeechletResponse handleFireResultGivent(boolean isHit) {
        return null;
    }

    public static SpeechletResponse handleHelpAsked() {
        if (!StateManager.getInstance().isGamesStarted()) {
            String speechOutput = Speeches.HELP_SPEECH_BEGINNING;

            String repromptText = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            return null;
        }
    }

    public static SpeechletResponse handleCancel() {
        String speechOutput = Speeches.LEAVING_MESSAGE;

        return SpeechesGenerator.newTellResponse(speechOutput);
    }

    public static SpeechletResponse handleStop() {

        return null;
    }
}
