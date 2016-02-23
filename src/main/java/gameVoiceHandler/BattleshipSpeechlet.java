package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import gameData.GameDataInstance;
import gameVoiceHandler.intents.HandlerFactory;
import gameVoiceHandler.intents.HandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by corentinl on 1/20/16.
 */
public class BattleshipSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(BattleshipSpeechlet.class);

    @Override
    public void onSessionStarted(SessionStartedRequest sessionStartedRequest, Session session) throws SpeechletException {
    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", launchRequest.getRequestId(),
                session.getSessionId());

        HandlerFactory factory = new HandlerFactory();
        HandlerInterface handler = factory.getHandler("");

        return handler.handleIntent(null, null);
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(),
                session.getSessionId());

        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        GameDataInstance gameDataInstance = GameDataInstance.initialize(session);

        HandlerFactory factory = new HandlerFactory();
        HandlerInterface handler = factory.getHandler(intentName);

        return handler.handleIntent(intent, gameDataInstance);
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
    }
}
