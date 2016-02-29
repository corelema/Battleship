package gameVoiceHandler.intents.handlers.Utils;

import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.GameParameters;
import gameData.StateManager;
import gameVoiceHandler.intents.speeches.Speeches;

/**
 * Created by corentinl on 2/23/16.
 */
public class GameStarterUtil {
    public static String startQuickGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startQuickGame();

        startGame(gameDataInstance);

        return startGameSpeech(stateManager);
    }

    public static String startAdvancedGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startAdvancedGame();

        startGame(gameDataInstance);

        return startGameSpeech(stateManager);
    }

    private static void startGame(GameDataInstance gameDataInstance) {
        GameParameters gameParameters = gameDataInstance.getStateManager().generateGameParameters();
        GameManager gameManager = new GameManager(gameParameters);
        gameDataInstance.setGameManager(gameManager);
    }

    public static String incorrectParametersSpeech() {
        return Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
    }

    private static String startGameSpeech(StateManager stateManager) {
        String gameLaunchSpeech = String.format(Speeches.GAME_LAUNCH, stateManager.getGridSize(), stateManager.getNumberOfShips());
        String speechOutput = gameLaunchSpeech + Speeches.PROMPT_LINE_COLUMN;
        speechOutput += Speeches.PROMPT_LINE_COLUMN_INSTRUCTIONS_1;
        int gridSize = stateManager.getGridSize();
        char gridSizeChar = (char)(gridSize + 'a');
        speechOutput += String.format(Speeches.PROMPT_LINE_COLUMN_INSTRUCTIONS_2, gridSize, gridSizeChar);

        return speechOutput;
    }
}
