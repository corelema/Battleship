package gameVoiceHandler.intents.handlers.Utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.StateManager;
import gameData.enums.VoiceState;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 3/2/16.
 */
public class YesNoUtil {
    public static SpeechletResponse handleYesNo(GameDataInstance gameDataInstance, boolean answer) {
        StateManager stateManager = gameDataInstance.getStateManager();
        GameManager gameManager = gameDataInstance.getGameManager();
        VoiceState voiceState = stateManager.getVoiceState();

        if (voiceState == VoiceState.PROMPT_FOR_INSTRUCTIONS) {
            return answerToPromptForInstructions(stateManager, answer);
        } else {
            return yesNoNotExpected(stateManager.getLastQuestionAsked());
        }

    }

    private static SpeechletResponse yesNoNotExpected(String lastQuestionAsked) {
        String speechOutput = Speeches.YES_NO_NOT_EXPECTED;
        if (lastQuestionAsked.length() > 0) {
            speechOutput += Speeches.LAST_QUESTION_WAS + lastQuestionAsked;
        }

        return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestionAsked, false);
    }

    private static SpeechletResponse answerToPromptForInstructions(StateManager stateManager, boolean answer) {
        stateManager.setInstructionsRequested(answer);
        stateManager.setVoiceState(VoiceState.INITIALIZATION);

        return InstructionsUtil.startInstructions(stateManager);
    }
}
