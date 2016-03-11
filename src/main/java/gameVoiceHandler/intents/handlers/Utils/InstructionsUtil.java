package gameVoiceHandler.intents.handlers.Utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.StateManager;
import gameData.enums.VoiceState;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

//TODO: In this whole class, see if I can take 2 strings and append the instructions to them. Then return void.

/**
 * Created by corentinl on 3/2/16.
 */
public class InstructionsUtil {
    public static SpeechletResponse startInstructions(StateManager stateManager) {
        boolean instructionsRequested = stateManager.isInstructionsRequested();

        String speechOutput;
        String reprompt;

        if (instructionsRequested) {
            speechOutput = Speeches.GENERAL_INSTRUCTIONS
                    + Speeches.LETS_START
                    + Speeches.HELP_SPEECH_BEGINNING
                    + Speeches.HELP_SPEECH_BEGINNING_REPROMPT
                    + Speeches.HELP_SPEECH_BEGINNING_INSTRUCTIONS;
            reprompt = Speeches.HELP_SPEECH_BEGINNING_REPROMPT
                    + Speeches.HELP_SPEECH_BEGINNING_INSTRUCTIONS;
        } else {
            speechOutput = Speeches.HELP_SPEECH_BEGINNING + Speeches.HELP_SPEECH_BEGINNING_REPROMPT;
            reprompt = Speeches.HELP_SPEECH_BEGINNING_REPROMPT + Speeches.HELP_SPEECH_BEGINNING_INSTRUCTIONS;
        }




        //stateManager.setLastQuestionAsked(reprompt); //TODO: Move the lasQuestionAsked to StateManager

        return SpeechesGenerator.newAskResponse(speechOutput, false, reprompt, false);
    }

    public static String fireInstructionsIfRequired(StateManager stateManager) {
        boolean instructionsRequired = stateManager.isInstructionsRequested() && stateManager.isFireInstructionsRequested();
        if (instructionsRequired) {
            stateManager.setFireInstructionsRequested(false);

            return fireInstructions(stateManager);
        } else {
            return null;
        }
    }

    public static String fireInstructions(StateManager stateManager) {
        int gridSize = stateManager.getGridSize();
        char gridSizeChar = (char)(gridSize + 'a' - 1);
        String instructions2 = String.format(Speeches.PROMPT_LINE_COLUMN_INSTRUCTIONS_2, gridSize, gridSizeChar);

        return Speeches.PROMPT_LINE_COLUMN_INSTRUCTIONS_0
                + Speeches.PROMPT_LINE_COLUMN_INSTRUCTIONS_1
                + instructions2;
    }

    public static String answerInstructionsIfRequired(StateManager stateManager) {
        boolean promptHitMissRequired = stateManager.isHitOrMissedQuestionAsked();
        String instructions = promptHitMissRequired ? Speeches.HIT_OR_MISSED : "";

        boolean instructionsRequired = stateManager.isInstructionsRequested() && stateManager.isAnswerInstructionsRequested();
        if (instructionsRequired) {
            stateManager.setAnswerInstructionsRequested(false);
            return instructions + answerInstructions(stateManager);
        } else {
            return instructions;
        }
    }

    public static String answerInstructions(StateManager stateManager) {
        return Speeches.ANSWER_EXAMPLE;
    }

    public static String advancedGameParametersInstructionsIfRequired(StateManager stateManager) {
        boolean instructionsRequired = stateManager.isInstructionsRequested() && stateManager.isAdvancedGameParametersInstructionsRequested();
        if (instructionsRequired) {
            stateManager.setAdvancedGameParametersInstructionsRequested(false);
            return Speeches.ADVANCED_GAME_PARAMETERS_INSTRUCTIONS;
        } else {
            return null;
        }
    }

    public static void defaultInstructionsRequiredToNoIfQuestionNotAnswered(StateManager stateManager) {
        if (stateManager.getVoiceState().equals(VoiceState.PROMPT_FOR_INSTRUCTIONS)) {
            stateManager.setInstructionsRequested(false);
        }
    }
}
