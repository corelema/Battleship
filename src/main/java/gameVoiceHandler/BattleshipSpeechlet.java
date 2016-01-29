package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gameData.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by corentinl on 1/20/16.
 */
public class BattleshipSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(BattleshipSpeechlet.class);

    private static final String STATE_MANAGER = "STATEMANAGER";

    public static GameManager gameManager; //Trying to keep it in this class to see if this object doesn't get killed


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

        StateManager stateManager = getStateManager(session);
        Util.session = session;
        
        if (isInitializationRequest(intentName) && !stateManager.getVoiceState().equals(StateManager.INITIALIZATION)) {
            String speechOutput = Speeches.NO_INITIALIZATION;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (isAdvancedGameSetupRequest(intentName) && !stateManager.getVoiceState().equals(StateManager.ADVANCED_GAME_STARTED)) {
            String speechOutput = Speeches.NO_ADVANCED_GAME_SETUP;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (isFiringRelatedRequest(intentName)
                && !(stateManager.getVoiceState().equals(StateManager.ADVANCED_GAME_STARTED)
                    || stateManager.getVoiceState().equals(StateManager.QUICK_GAME_STARTED))) {
            String speechOutput = Speeches.NO_FIRE_YET;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        SpeechletResponse speechletResponse;

        if ("startQuickGameIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleQuickGameAsked(stateManager);
        } else if ("startAdvancedGameIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleAdvancedGameAsked(stateManager);
        } else if ("startAdvancedGameWithParametersIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleAdvancedGameAsked(intent, stateManager);
        } else if ("parameterSizeOfGridIntent".equals(intentName) || "parameterNumberOfShipsIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleParameterGiven(intent, stateManager);
        } else if ("answerHitOrMissIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleFireResultGiven(intent, stateManager);
        } else if ("fireAtXAndYIntent".equals(intentName) || "fireAtLetterAndNumberIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleTwoFireCoordinatesGiven(intent, stateManager);
        } else if ("oneFirePosition".equals(intentName) || "oneFirePositionLetter".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleOneFireCoordinatesGiven(intent, stateManager);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleHelpAsked(stateManager);
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleStop(stateManager);
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            speechletResponse =  OrdersTranslator.handleCancel(stateManager);
        } else {
            speechletResponse =  OrdersTranslator.handleUnrecognizedIntent();
        }

        session.setAttribute(STATE_MANAGER, stateManager);

        return speechletResponse;
    }

    private StateManager getStateManager(Session session) {

        StateManager stateManager = null;

        if (session.getAttribute(STATE_MANAGER) != null) {
            java.util.LinkedHashMap stateManagerMap =  (java.util.LinkedHashMap)session.getAttribute(STATE_MANAGER);
            final ObjectMapper mapper = new ObjectMapper();
            stateManager = mapper.convertValue(stateManagerMap, StateManager.class);
        }

        if (stateManager == null) {
            stateManager = new StateManager();
            session.setAttribute(STATE_MANAGER, stateManager);
        }
        return stateManager;
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
                || "fireAtLetterAndNumberIntent".equals(intentName)
                || "oneFirePosition".equals(intentName)
                || "oneFirePositionLetter".equals(intentName)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {
        gameManager = null;
    }

    private SpeechletResponse getWelcomeResponse() {
        String welcomeMessage = Speeches.WELCOME + Speeches.HELP_SPEECH_BEGINNING;
        String reprompt = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

        return SpeechesGenerator.newAskResponse(welcomeMessage, false, reprompt, false);
    }

}
