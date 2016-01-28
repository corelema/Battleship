package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.oracle.tools.packager.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.nimbus.State;

/**
 * Created by corentinl on 1/23/16.
 */
public class OrdersTranslator {
    private static final Logger log = LoggerFactory.getLogger(OrdersTranslator.class);

    private static final String GRID_SIZE_SLOT = "gridSize";
    private static final String NUMBER_OF_SHIPS_SLOT = "numberOfShips";
    private static final String HIT_OR_MISS_SLOT = "hitOrMiss";
    private static final String LINE_SLOT = "line";
    private static final String COLUMN_SLOT = "column";
    private static final String LINE_LETTER_SLOT = "lineLetter";
    private static final String COLUMN_NUMBER_SLOT = "columnNumber";
    private static final String LINE_OR_COLUMN_SLOT = "lineOrColumn";

    private static String lastQuestion = "Oops. It seems that there is a bug";

    public static SpeechletResponse handleQuickGameAsked() {
        if (!StateManager.getInstance().isGamesStarted()) {

            //TODO: call the game:  to create a 3 by 3 game with one ship of size 2

            String speechOutput = String.format(Speeches.QUICK_GAME_LAUNCH, 3, 3);

            String repromptText = Speeches.PROMPT_LINE_COLUMN;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            String speechOutput = Speeches.GAME_ALREADY_STARTED;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    public static SpeechletResponse handleAdvancedGameAsked() {
        if (!StateManager.getInstance().isGamesStarted()) {

            String speechOutput = Speeches.ADVANCED_GAME_LAUNCH + Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;

            String repromptText = Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            String speechOutput = Speeches.GAME_ALREADY_STARTED;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    public static SpeechletResponse handleAdvancedGameAsked(Intent intent) {
        Slot gridSizeSlot = intent.getSlot(GRID_SIZE_SLOT);
        Slot numberOfShipsSlot = intent.getSlot(NUMBER_OF_SHIPS_SLOT);

        try {
            if (gridSizeSlot != null && numberOfShipsSlot != null) {
                StateManager.getInstance().setGridSize(Integer.parseInt(gridSizeSlot.getValue()));
                StateManager.getInstance().setNumberOfShips(Integer.parseInt(numberOfShipsSlot.getValue()));
            }
        } catch (NumberFormatException e) {
            String speechText = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            return SpeechesGenerator.newAskResponse(speechText, false, speechText, false);
        }

        //TODO: call the game: create a game with the given parameters. In here, we know that we have all the parameters. We might want to be able to create a game, give the parameters one by one, then call something like "isReadyToBeLaunched" then launching it

        return SpeechesGenerator.newTellResponse(Speeches.NOT_IMPLEMENTED);
    }

    /**
     * handles one parameter given at a time
     * the parameter can be in either of the slots:
     * GRID_SIZE_SLOT
     * NUMBER_OF_SHIPS_SLOT
     * */
    public static SpeechletResponse handleParameterGiven(Intent intent) {
        String speechText = "";
        try {
            if (intent.getSlot(GRID_SIZE_SLOT) != null) {
                StateManager.getInstance().setGridSize(Integer.parseInt(intent.getSlot(GRID_SIZE_SLOT).getValue()));
                speechText = Speeches.GRID_SIZE_GIVEN + StateManager.getInstance().getGridSize() + ". ";
            } else if (intent.getSlot(NUMBER_OF_SHIPS_SLOT) != null) {
                StateManager.getInstance().setNumberOfShips(Integer.parseInt(intent.getSlot(NUMBER_OF_SHIPS_SLOT).getValue()));
                speechText = Speeches.NUMBER_OF_SHIPS_GIVEN + StateManager.getInstance().getNumberOfShips() + ". ";
            } else {
                return SpeechesGenerator.newTellResponse(Speeches.ERROR);
            }
        } catch (NumberFormatException e) {
            speechText = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            return SpeechesGenerator.newAskResponse(speechText, false, speechText, false);
        }

        if (StateManager.getInstance().isGameReadyToBeStarted()) {
            //TODO: call the game: pass the parameters to the instance of the game. If the game has everything, launch the game (we can suppose that the user might say only one parameter at a time)
            StateManager.getInstance().startGame();
            speechText += String.format(Speeches.GAME_START, StateManager.getInstance().getGridSize(), StateManager.getInstance().getNumberOfShips()) + Speeches.YOUR_TURN;
        } else {
            if (StateManager.getInstance().isGridSizeCorrect()) {
                speechText += Speeches.PROMPT_NUMBER_OF_SHIPS_ONLY;
            } else {
                speechText += Speeches.PROMPT_GRID_SIZE_ONLY;
            }
        }

        return SpeechesGenerator.newAskResponse(speechText, false, speechText, false);
    }

    /**
     * handles two fire coordinates given at a time
     * the parameter can be either the slots:
     * line -> AMAZON.NUMBER
     * column -> AMAZON.NUMBER
     * OR
     * lineLetter -> LETTERS
     * columnNumber -> AMAZON.NUMBER
     * */
    public static SpeechletResponse handleTwoFireCoordinatesGiven(Intent intent) {
        Slot lineSlot = intent.getSlot(LINE_SLOT);
        Slot columnSlot = intent.getSlot(COLUMN_SLOT);
        Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
        Slot columnNumberSlot = intent.getSlot(COLUMN_NUMBER_SLOT);

        int x = -1, y = -1;
        String speechText = "";

        try {
            if (lineSlot != null && columnSlot != null) {
                x = Integer.parseInt(lineSlot.getValue());
                y = Integer.parseInt(columnSlot.getValue());
                speechText = String.format(Speeches.YOU_FIRE, x, y);
            } else if (lineLetterSlot != null && columnNumberSlot != null) {
                String givenChar = lineLetterSlot.getValue();
                x = givenChar.toCharArray()[0] - 'a' + 1;
                y = Integer.parseInt(columnNumberSlot.getValue());
                speechText = String.format(Speeches.YOU_FIRE, x, y);
            } else {
                return SpeechesGenerator.newTellResponse(Speeches.ERROR);
            }
        } catch (NumberFormatException e) {
            speechText = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            return SpeechesGenerator.newAskResponse(speechText, false, speechText, false);
        }

        //TODO: call to the game: I am firing at x, y (need to create a Coordinates class)

        return SpeechesGenerator.newAskResponse(speechText, false, speechText, false);
    }

    public static SpeechletResponse handleOneFireCoordinatesGiven(Intent intent) {
        //TODO: call to the game: I am firing at x, y (need to create a Coordinates class)

        return null;
    }

    public static SpeechletResponse handleFireResultGivent(Intent intent) {
        //TODO: call the game: give true or false, to say that Alexa hit or missed.

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
        String speechOutput = Speeches.LEAVING_MESSAGE;

        return SpeechesGenerator.newTellResponse(speechOutput);
    }

    public static SpeechletResponse handleUnrecognizedIntent() {
        String speechOutput = Speeches.IM_SORRY + Speeches.NOT_RECOGNIZED;
        String repromptText = Speeches.NOT_RECOGNIZED;

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }
}
