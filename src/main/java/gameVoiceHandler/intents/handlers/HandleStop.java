package gameVoiceHandler.intents.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.GameDataInstance;
import gameVoiceHandler.intents.HandlerInterface;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandleStop implements HandlerInterface {
    @Override
    public SpeechletResponse handleIntent(Intent intent, GameDataInstance gameDataInstance) {
        String speechOutput = Speeches.LEAVING_MESSAGE;

        return SpeechesGenerator.newTellResponse(speechOutput);
    }
}
