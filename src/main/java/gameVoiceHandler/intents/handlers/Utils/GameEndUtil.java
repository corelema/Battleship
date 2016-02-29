package gameVoiceHandler.intents.handlers.Utils;

import gameData.GameManager;
import gameVoiceHandler.intents.speeches.Speeches;

/**
 * Created by corentinl on 2/27/16.
 */
public class GameEndUtil {
    public static String endingString(GameManager gameManager){
        if (gameManager.didAlexaWin()) {
            return Speeches.YOU_LOSE;
        } else {
            return Speeches.YOU_WIN;
        }
    }
}
