package gameVoiceHandler.intents.handlers.Utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameData.GameManager;
import gameData.GameParameters;
import gameData.StateManager;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/23/16.
 */
public class GameStarterUtil {
    public static String startQuickGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startQuickGame();

        startGame(gameDataInstance);

        return SharedSpeeches.startGameSpeech(stateManager);
    }

    public static String startAdvancedGame(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        stateManager.startAdvancedGame();

        startGame(gameDataInstance);

        return SharedSpeeches.startGameSpeech(stateManager);
    }

    private static void startGame(GameDataInstance gameDataInstance) {
        GameParameters gameParameters = gameDataInstance.getStateManager().generateGameParameters();
        GameManager gameManager = new GameManager(gameParameters);
        gameDataInstance.setGameManager(gameManager);
    }

    public static String incorrectParametersSpeech() {
        return Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
    }
}
