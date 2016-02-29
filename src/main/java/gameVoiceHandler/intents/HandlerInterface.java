package gameVoiceHandler.intents;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;

/**
 * Created by corentinl on 2/22/16.
 */
public interface HandlerInterface {
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance);
}
