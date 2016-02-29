package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.StateManager;
import gameData.enums.TurnState;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.GameFireUtil;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by corentinl on 2/22/16.
 */

//TODO: Combine this class with HandleOneFirePositionGiven

public class HandleTwoFirePositionsGiven implements HandlerInterface {
    private static final String LINE_SLOT = "line";
    private static final String COLUMN_SLOT = "column";
    private static final String LINE_LETTER_SLOT = "lineLetter";
    private static final String COLUMN_NUMBER_SLOT = "columnNumber";

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.fireUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();

        if (stateManager.getTurnState().equals(TurnState.PLAYER)) {


            String speechOutput = "";
            GameManager gameManager = gameDataInstance.getGameManager();
            parseFireCoordinates(intent, gameManager);

            if (gameDataInstance.getGameManager().getLastPlayerAttackXCoordinate() == -1
                    || gameDataInstance.getGameManager().getLastPlayerAttackYCoordinate() == -1) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            } else {
                speechOutput += GameFireUtil.fire(gameDataInstance);
            }

            if (gameManager.gameIsOver()) {
                return SpeechesGenerator.newTellResponse(speechOutput);
            } else {
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);//TODO: Check the reprompt
            }
        } else {
            String speechOutput = Speeches.NOT_YOUR_TURN + gameDataInstance.getGameManager().getLastQuestionAsked();
            return SpeechesGenerator.newAskResponse(speechOutput, false, gameDataInstance.getGameManager().getLastQuestionAsked(), false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesStarted();
    }

    private static void parseFireCoordinates(Intent intent, GameManager gameManager) {
        Slot lineSlot = intent.getSlot(LINE_SLOT);
        Slot columnSlot = intent.getSlot(COLUMN_SLOT);
        Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
        Slot columnNumberSlot = intent.getSlot(COLUMN_NUMBER_SLOT);

        int x = -1;
        int y = -1;

        if (lineSlot != null && columnSlot != null) {
            String lineSlotValue = lineSlot.getValue();
            String columnSlotValue = columnSlot.getValue();

            if (NumberUtils.isNumber(lineSlotValue) && NumberUtils.isNumber(columnSlotValue))
            x = Integer.parseInt(lineSlotValue);
            y = Integer.parseInt(columnSlotValue);
        } else if (lineLetterSlot != null && columnNumberSlot != null) {
            String lineLetterSlotValue = lineLetterSlot.getValue();
            String columnNumberSlotValue = columnNumberSlot.getValue();

            x = lineLetterSlotValue.toCharArray()[0] - 'a' + 1;
            if (x < 0) {
                x = lineLetterSlotValue.toCharArray()[0] - 'A' + 1;
            }

            if (NumberUtils.isNumber(columnNumberSlotValue))
            y = Integer.parseInt(columnNumberSlotValue);
        }

        gameManager.setLastPlayerAttackXCoordinate(x);
        gameManager.setLastPlayerAttackYCoordinate(y);
    }
}
