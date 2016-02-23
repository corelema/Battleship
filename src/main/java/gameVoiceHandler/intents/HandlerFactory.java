package gameVoiceHandler.intents;

import gameVoiceHandler.intents.handlers.*;

/**
 * Created by corentinl on 2/22/16.
 */
public class HandlerFactory {
    public HandlerInterface getHandler(String intentName) {
        HandlerInterface retval;

        if ("startQuickGameIntent".equals(intentName)) {
            retval = new HandleStartQuickGame();
        } else if ("startAdvancedGameIntent".equals(intentName)) {
            retval = new HandleStartAdvancedGame();
        } else if ("startAdvancedGameWithParametersIntent".equals(intentName)) {
            retval = new HandleStartAdvancedGameWithParameters();
        } else if ("parameterSizeOfGridIntent".equals(intentName) || "parameterNumberOfShipsIntent".equals(intentName)) {
            retval = new HandleGameParameter();
        } else if ("answerHitOrMissIntent".equals(intentName)) {
            retval = new HandleAnswerHitOrMiss();
        } else if ("fireAtXAndYIntent".equals(intentName) || "fireAtLetterAndNumberIntent".equals(intentName)) {
            retval = new HandleTwoFirePositionsGiven();
        } else if ("oneFirePosition".equals(intentName) || "oneFirePositionLetter".equals(intentName)) {
            retval = new HandleOneFirePositionGiven();
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            retval = new HandleHelp();
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            retval = new HandleStop();
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            retval = new HandleCancel();
        } else {
            retval = new HandleWelcome();
        }

        return retval;
    }

}
