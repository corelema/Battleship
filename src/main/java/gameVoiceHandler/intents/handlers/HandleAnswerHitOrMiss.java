package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.StateManager;
import gameVoiceHandler.intents.handlers.Utils.BadIntentUtil;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleAnswerHitOrMiss implements HandlerInterface {
    private static final String HIT_OR_MISS_SLOT = "hitOrMiss";

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.fireUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();
        GameManager gameManager = gameDataInstance.getGameManager();

        if (stateManager.getTurnState().equals(StateManager.ALEXA)) {
            Slot hitOrMissSlotSlot = intent.getSlot(HIT_OR_MISS_SLOT);

            String speechOutput = "";
            if (hitOrMissSlotSlot != null && hitOrMissSlotSlot.getValue() != null) {
                boolean isHit = hitOrMissSlotSlot.getValue().equals("hit");

                if (isHit) {
                    speechOutput = Speeches.GOT_YOU;
                } else {
                    speechOutput = Speeches.YOU_GOT_ME;
                }

                stateManager.setTurnState(StateManager.PLAYER);

                gameManager.didAlexaHit(isHit);

                if (gameManager.gameIsOver()) {
                    speechOutput += SharedSpeeches.endingString(gameManager);
                    return SpeechesGenerator.newTellResponse(speechOutput);
                }
                speechOutput += Speeches.PROMPT_LINE_COLUMN;
                String repromptText = Speeches.PROMPT_LINE_COLUMN;
                gameManager.setLastQuestionAsked(repromptText);

                stateManager.setTurnState(StateManager.PLAYER);

                return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
            } else {
                return handleHelpAsked();
            }
        } else {
            String speechOutput = Speeches.WAS_YOUR_TURN + gameManager.getLastQuestionAsked();
            return SpeechesGenerator.newAskResponse(speechOutput, false, gameManager.getLastQuestionAsked(), false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesStarted();
    }

    private static SpeechletResponse handleHelpAsked() {
        return null;
    }
}
