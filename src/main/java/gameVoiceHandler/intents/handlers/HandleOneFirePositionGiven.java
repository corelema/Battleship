package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.AttackResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.StateManager;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.GameFireUtil;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.*;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleOneFirePositionGiven implements HandlerInterface {
    private static final String LINE_LETTER_SLOT = "lineLetter";
    private static final String LINE_OR_COLUMN_SLOT = "lineOrColumn";

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.fireUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();

        if (stateManager.getTurnState().equals(StateManager.PLAYER)) {
            String speechOutput = "";

            int fireCoordinateGiven = parseOneFireCoordinate(intent);

            if (fireCoordinateGiven != -1) {
                speechOutput = String.format(Speeches.YOU_GAVE_ONE_COORDINATE, fireCoordinateGiven);
            } else {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }

            GameManager gameManager = gameDataInstance.getGameManager();

            if (gameManager.getLastPlayerAttackXCoordinate() == -1) {
                gameManager.setLastPlayerAttackXCoordinate(fireCoordinateGiven);
            } else {
                gameManager.setLastPlayerAttackYCoordinate(fireCoordinateGiven);

                speechOutput += GameFireUtil.fire(gameDataInstance);
            }
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        } else {
            String speechOutput = Speeches.NOT_YOUR_TURN + gameDataInstance.getGameManager().getLastQuestionAsked();
            return SpeechesGenerator.newAskResponse(speechOutput, false, gameDataInstance.getGameManager().getLastQuestionAsked(), false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesStarted();
    }

    private static int parseOneFireCoordinate(Intent intent) {
        Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
        Slot lineOrColumnSlot = intent.getSlot(LINE_OR_COLUMN_SLOT);

        int fireCoordinateGiven = -1;

        if (lineOrColumnSlot != null) {
            String lineLetterSlotString = lineLetterSlot.getValue();
            if (NumberUtils.isNumber(lineLetterSlotString))
                fireCoordinateGiven = Integer.parseInt(lineLetterSlotString);
        } else if (lineLetterSlot != null) {
            String givenChar = lineLetterSlot.getValue();
            fireCoordinateGiven = givenChar.toCharArray()[0] - 'a' + 1;
            if (fireCoordinateGiven < 0) {
                fireCoordinateGiven = givenChar.toCharArray()[0] - 'A' + 1;
            }
        }
        //TODO: Improve the tests above, Add unit tests
        return fireCoordinateGiven;
    }
}
