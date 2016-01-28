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
        log.info("onLaunch requestId={}, sessionId={}", launchRequest.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(),
                session.getSessionId());

        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if (isInitializationRequest(intentName) && StateManager.getInstance().getVoiceState() != VoiceState.INITIALIZATION) {
            String speechOutput = Speeches.NO_INITIALIZATION;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (isAdvancedGameSetupRequest(intentName) && StateManager.getInstance().getVoiceState() != VoiceState.ADVANCED_GAME_STARTED) {
            String speechOutput = Speeches.NO_ADVANCED_GAME_SETUP;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (isFiringRelatedRequest(intentName)
                && !(StateManager.getInstance().getVoiceState() == VoiceState.ADVANCED_GAME_STARTED
                    || StateManager.getInstance().getVoiceState() == VoiceState.QUICK_GAME_STARTED)) {
            String speechOutput = Speeches.NO_FIRE_YET;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if ("startQuickGameIntent".equals(intentName)) {
            return OrdersTranslator.handleQuickGameAsked();
        } else if ("startAdvancedGameIntent".equals(intentName)) {
            return OrdersTranslator.handleAdvancedGameAsked();
        } else if ("startAdvancedGameWithParametersIntent".equals(intentName)) {
            return OrdersTranslator.handleAdvancedGameAsked(intent);
        } else if ("parameterSizeOfGridIntent".equals(intentName) || "parameterNumberOfShipsIntent".equals(intentName)) {
            return OrdersTranslator.handleParameterGiven(intent);
        } else if ("answerHitOrMissIntent".equals(intentName)) {
            return OrdersTranslator.handleFireResultGiven(intent);
        } else if ("fireAtXAndYIntent".equals(intentName) || "fireAtLetterAndNumberIntent".equals(intentName)) {
            return OrdersTranslator.handleTwoFireCoordinatesGiven(intent);
        } else if ("oneFirePosition".equals(intentName) || "oneFirePositionLetter".equals(intentName)) {
            return OrdersTranslator.handleOneFireCoordinatesGiven(intent);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return OrdersTranslator.handleHelpAsked();
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            return OrdersTranslator.handleStop();
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            return OrdersTranslator.handleCancel();
        } else {
            return OrdersTranslator.handleUnrecognizedIntent();
        }
    }

    private boolean isInitializationRequest(String intentName) {
        if ("startQuickGameIntent".equals(intentName)
                || "startAdvancedGameIntent".equals(intentName)
                || "startAdvancedGameWithParametersIntent".equals(intentName)) {
            return true;
        }
        return false;
    }

    private boolean isAdvancedGameSetupRequest(String intentName) {
        if ("parameterSizeOfGridIntent".equals(intentName)
                || "parameterNumberOfShipsIntent".equals(intentName)) {
            return true;
        }
        return false;
    }

    private boolean isFiringRelatedRequest(String intentName) {
        if ("answerHitOrMissIntent".equals(intentName)
                || "fireAtXAndYIntent".equals(intentName)
                || "oneFirePosition".equals(intentName)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {

    }

    private SpeechletResponse getWelcomeResponse() {
        String welcomeMessage = Speeches.WELCOME + Speeches.HELP_SPEECH_BEGINNING;
        String reprompt = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

        return SpeechesGenerator.newAskResponse(welcomeMessage, false, reprompt, false);
    }

}
