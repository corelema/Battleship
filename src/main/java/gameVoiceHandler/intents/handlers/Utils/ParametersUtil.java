package gameVoiceHandler.intents.handlers.Utils;

import gameData.StateManager;
import gameVoiceHandler.intents.speeches.Speeches;

/**
 * Created by corentinl on 3/8/16.
 */
public class ParametersUtil {
    public static String issueWithParametersSpeech(StateManager stateManager) {
        if (stateManager.areParametersEntered()) {
            return ParametersUtil.parametersIncorrectSpeech(stateManager);
        } else {
            return ParametersUtil.missingParameterSpeech(stateManager);
        }
    }

    private static String missingParameterSpeech(StateManager stateManager) {
        return stateManager.isGridSizeCorrect() ? Speeches.PROMPT_NUMBER_OF_SHIPS_ONLY : Speeches.PROMPT_GRID_SIZE_ONLY;
    }

    private static String parametersIncorrectSpeech(StateManager stateManager) {
        int gridSizeGiven = stateManager.getGridSize();
        int numberOfShipsGiven = stateManager.getNumberOfShips();
        stateManager.resetParameters();
        return String.format(Speeches.INCORRECT_PARAMETERS, gridSizeGiven, numberOfShipsGiven);
    }
}
