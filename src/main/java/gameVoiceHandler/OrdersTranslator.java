package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;

/**
 * Created by corentinl on 1/23/16.
 */
public class OrdersTranslator {
    private static final String GRID_SIZE_SLOT = "gridSize";
    private static final String NUMBER_OF_SHIPS_SLOT = "numberOfShips";
    private static final String HIT_OR_MISS_SLOT = "hitOrMiss";
    private static final String LINE_SLOT = "line";
    private static final String COLUMN_SLOT = "column";
    private static final String LINE_OR_COLUMN_SLOT = "lineOrColumn";

    public static SpeechletResponse handleFirstEventRequest() {
        return null;
    }

    public static SpeechletResponse handleQuickGameAsked() {
        if (!StateManager.getInstance().isGamesStarted()) {
            String speechOutput = String.format(Speeches.QUICK_GAME_LAUNCH, 3, 3);

            String repromptText = Speeches.YOUR_TURN;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            return null;
        }
    }

    public static SpeechletResponse handleAdvancedGameAsked() {
        return null;
    }

    public static SpeechletResponse handleAdvancedGameAsked(Intent intent) {
        Slot gridSizeSlot = intent.getSlot(GRID_SIZE_SLOT);
        Slot numberOfShipsSlot = intent.getSlot(NUMBER_OF_SHIPS_SLOT);

        return null;
    }

    public static SpeechletResponse handleParameterGiven(Intent intent) {
        return null;
    }

    public static SpeechletResponse handleFireCoordinatesGiven(Intent intent) {
        return null;
    }

    public static SpeechletResponse handleFireResultGivent(Intent intent) {
        Slot hitOrMissSlotSlot = intent.getSlot(HIT_OR_MISS_SLOT);
        if (hitOrMissSlotSlot != null && hitOrMissSlotSlot.getValue() != null) {
            String speechOutput = "You said that you " + hitOrMissSlotSlot.getValue();

            String repromptText = "Sorry, my abilities are limited for now.";

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            return handleHelpAsked();
        }
    }

    public static SpeechletResponse handleHelpAsked() {
        if (!StateManager.getInstance().isGamesStarted()) {
            String speechOutput = Speeches.HELP_SPEECH_BEGINNING;

            String repromptText = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            //TODO: determine the current state and give appropriate help.
            //TODO: and help wasn't asked but result in a call from another handler, give an error message. Maybe the whole error process should be handled in a different method or class
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
