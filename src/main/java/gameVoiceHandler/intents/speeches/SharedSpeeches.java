package gameVoiceHandler.intents.speeches;

import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameManager;
import gameData.StateManager;

/**
 * Created by corentinl on 2/22/16.
 */
public class SharedSpeeches {
    public static String startGameSpeech(StateManager stateManager) {
        String speechOutput = String.format(Speeches.GAME_LAUNCH, stateManager.getGridSize(), stateManager.getNumberOfShips());

        return speechOutput;
    }

    public static String endingString(GameManager gameManager){
        if (gameManager.didAlexaWin()) {
            return Speeches.YOU_LOSE;
        } else {
            return Speeches.YOU_WIN;
        }
    }
}
