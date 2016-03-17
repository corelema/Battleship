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
import gameVoiceHandler.intents.handlers.Utils.GameEndUtil;
import gameVoiceHandler.intents.handlers.Utils.InstructionsUtil;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleAnswerHitOrMiss implements HandlerInterface {
    private static final String HIT_OR_MISS_SLOT = "hitOrMiss";

    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        if (!isIntentExpected(gameDataInstance)) {
            return BadIntentUtil.fireUnexpected();
        }

        StateManager stateManager = gameDataInstance.getStateManager();
        GameManager gameManager = gameDataInstance.getGameManager();

        if (stateManager.getTurnState().equals(TurnState.ALEXA)) {
            Slot hitOrMissSlotSlot = intent.getSlot(HIT_OR_MISS_SLOT);

            String speechOutput = "";
            if (hitOrMissSlotSlot != null && hitOrMissSlotSlot.getValue() != null) {
                stateManager.setTurnState(TurnState.PLAYER);

                boolean isHit = hitOrMissSlotSlot.getValue().equals("hit");
                speechOutput = isHit ? SpeechesGenerator.pickOne(Speeches.GOT_YOU) : SpeechesGenerator.pickOne(Speeches.DIDNT_GET_YOU);

                gameManager.didAlexaHit(isHit);

                if (gameManager.gameIsOver()) {
                    speechOutput += GameEndUtil.endingString(gameManager);
                    return SpeechesGenerator.newTellResponse(speechOutput);
                } else {
                    speechOutput += Speeches.YOUR_TURN;
                    String repromptText = Speeches.YOUR_TURN + InstructionsUtil.fireInstructions(stateManager);
                    stateManager.setLastQuestionAsked(repromptText);
                    stateManager.setLastReprompt(repromptText);

                    stateManager.setTurnState(TurnState.PLAYER);

                    return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
                }
            } else {
                speechOutput = Speeches.INCORRECT_HIT_MISS + stateManager.getLastReprompt();
                return SpeechesGenerator.newAskResponse(speechOutput, false, stateManager.getLastReprompt(), false);
            }
        } else {
            String speechOutput = Speeches.WAS_YOUR_TURN + stateManager.getLastReprompt();
            return SpeechesGenerator.newAskResponse(speechOutput, false, stateManager.getLastReprompt(), false);
        }
    }

    private boolean isIntentExpected(GameDataInstance gameDataInstance) {
        return gameDataInstance.getStateManager().isGamesStarted();
    }
}
