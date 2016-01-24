package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
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
        return null;
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(),
                session.getSessionId());

        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("startQuickGameIntent".equals(intentName)) {
            return OrdersTranslator.handleQuickGameAsked();
        } else if ("startAdvancedGameIntent".equals(intentName)) {
            return null;
        } else if ("parameterSizeOfGridIntent".equals(intentName)) {
            return null;
        } else if ("parameterNumberOfShipsIntent".equals(intentName)) {
            return null;
        } else if ("startAdvancedGameWithParametersIntent".equals(intentName)) {
            return null;
        } else if ("answerHitOrMissIntent".equals(intentName)) {
            return null;
        } else if ("fireAtXAndYIntent".equals(intentName)) {
            return null;
        } else if ("oneFirePosition".equals(intentName)) {
            return null;
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return OrdersTranslator.handleHelpAsked();
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            return OrdersTranslator.handleStop();
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            return OrdersTranslator.handleCancel();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {

    }
}
