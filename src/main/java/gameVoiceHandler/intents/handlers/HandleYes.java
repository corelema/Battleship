package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.handlers.Utils.YesNoUtil;

/**
 * Created by corentinl on 3/2/16.
 */
public class HandleYes implements HandlerInterface {
    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        return YesNoUtil.handleYesNo(gameDataInstance, true);
    }
}
