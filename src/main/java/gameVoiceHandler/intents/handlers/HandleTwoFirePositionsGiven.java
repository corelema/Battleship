package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.AttackResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.StateManager;
import gameVoiceHandler.intents.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

import java.awt.*;

/**
 * Created by corentinl on 2/22/16.
 */

//TODO: Combine this class with HandleOneFirePositionGiven

public class HandleTwoFirePositionsGiven implements HandlerInterface {
    private static final String LINE_SLOT = "line";
    private static final String COLUMN_SLOT = "column";
    private static final String LINE_LETTER_SLOT = "lineLetter";
    private static final String COLUMN_NUMBER_SLOT = "columnNumber";
    private static int x = -1, y = -1;


    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.fireUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();

        if (stateManager.getTurnState().equals(StateManager.PLAYER)) {
            Slot lineSlot = intent.getSlot(LINE_SLOT);
            Slot columnSlot = intent.getSlot(COLUMN_SLOT);
            Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
            Slot columnNumberSlot = intent.getSlot(COLUMN_NUMBER_SLOT);

            String speechOutput = "";

            try {
                if (lineSlot != null && columnSlot != null) {
                    x = Integer.parseInt(lineSlot.getValue());
                    y = Integer.parseInt(columnSlot.getValue());
                } else if (lineLetterSlot != null && columnNumberSlot != null) {
                    String givenChar = lineLetterSlot.getValue();
                    x = givenChar.toCharArray()[0] - 'a' + 1;
                    if (x < 0) {
                        x = givenChar.toCharArray()[0] - 'A' + 1;
                    }
                    y = Integer.parseInt(columnNumberSlot.getValue());
                } else {
                    return SpeechesGenerator.newTellResponse(Speeches.ERROR);
                }
            } catch (NumberFormatException e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            } catch (Exception e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }

            speechOutput += fire(gameDataInstance);

            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        } else {
            String speechOutput = Speeches.NOT_YOUR_TURN;// + lastQuestion;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);//lastQuestion, false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesStarted();
    }

    private static String fire(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        GameManager gameManager = gameDataInstance.getGameManager();

        if (canFire(stateManager)) {
            String speechOutput = String.format(Speeches.YOU_FIRE, x, y);
            String repromptText = "";

            AttackResponse attackResponse = gameManager.fireAtPoint(new Point(x - 1, y - 1));

            if (attackResponse.isCanAttack()) {
                stateManager.setTurnState(StateManager.ALEXA);

                if (attackResponse.isAttackSuccessful()) {
                    speechOutput += Speeches.HIT;
                    if (gameManager.gameIsOver()) {
                        speechOutput += SharedSpeeches.endingString(gameManager);
                    }
                } else {
                    speechOutput += Speeches.MISS;
                }

                if (!gameManager.gameIsOver()) {

                    Point alexaFire = gameManager.nextAlexaHit();

                    repromptText = String.format(Speeches.MY_TURN, alexaFire.x + 1, alexaFire.y + 1);

                    //lastQuestion = repromptText;
                    speechOutput += repromptText;
                }

                //stateManager.setTurnState(TurnState.ALEXA);
            } else {
                return Speeches.ALREAD_TRIED_THIS_SPOT;
            }

            x = -1;
            y = -1;

            return speechOutput;
        } else {
            return String.format(Speeches.COORDINATES_NOT_VALID, 1, stateManager.getGridSize(), x, y);// + lastQuestion;
        }
    }

    private static boolean canFire(StateManager stateManager) {
        int indexX = x-1;
        int indexY = y-1;

        return (indexX < stateManager.getGridSize()
                && indexX >= 0
                && indexY < stateManager.getGridSize()
                && indexY >= 0);
    }
}
