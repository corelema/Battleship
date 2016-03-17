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
    public static void startQuickGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startQuickGame();
        startGame(gameDataInstance);
    }

    public static void startAdvancedGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startAdvancedGame();
        startGame(gameDataInstance);
    }

    private static void startGame(GameDataInstance gameDataInstance) {
        GameParameters gameParameters = gameDataInstance.getStateManager().generateGameParameters();
        GameManager gameManager = new GameManager(gameParameters);
        gameDataInstance.setGameManager(gameManager);
    }

    public static String incorrectParametersSpeech() {
        return Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
    }

    public static String startGameSpeech(StateManager stateManager) {
        String gameLaunchSpeech = String.format(Speeches.GAME_LAUNCH, stateManager.getGridSize(), stateManager.getGridSize(), stateManager.getNumberOfShips());
        String speechOutput = gameLaunchSpeech + Speeches.YOUR_TURN;
        String instructions = InstructionsUtil.fireInstructions(stateManager);
        String instructionsIfRequired = InstructionsUtil.fireInstructionsIfRequired(stateManager);
        stateManager.setLastQuestionAsked(speechOutput + instructions);
        speechOutput = instructionsIfRequired == null ? speechOutput : speechOutput + instructionsIfRequired;

        return speechOutput;
    }
}
