package gameVoiceHandler.intents.handlers.Utils;

import gameData.*;
import gameVoiceHandler.intents.speeches.SharedSpeeches;
import gameVoiceHandler.intents.speeches.Speeches;

import java.awt.*;

/**
 * Created by corentinl on 2/23/16.
 */
public class GameFireUtil {
    public static String fire(GameDataInstance gameDataInstance) {
        StateManager stateManager = gameDataInstance.getStateManager();
        GameManager gameManager = gameDataInstance.getGameManager();

        int indexX = gameManager.getLastPlayerAttackXCoordinate()-1;
        int indexY = gameManager.getLastPlayerAttackYCoordinate()-1;

        if (canFire(indexX, indexY, gameManager)) {

            String speechOutput = String.format(Speeches.YOU_FIRE, indexX + 1, indexY + 1);
            String repromptText = "";

            AttackResponse attackResponse = gameManager.fireAtPoint(new Point(indexX, indexY));

            if (attackResponse.isCanAttack()) {
                stateManager.setTurnState(StateManager.ALEXA);

                if (attackResponse.isAttackSuccessful()) {
                    speechOutput += Speeches.HIT;
                    if (gameManager.gameIsOver()) {
                        speechOutput += SharedSpeeches.endingString(gameManager);
                    }
                } else {
                    speechOutput += Speeches.MISS;
                }

                if (!gameManager.gameIsOver()) {

                    Point alexaFire = gameManager.nextAlexaHit();

                    repromptText = String.format(Speeches.MY_TURN, alexaFire.x + 1, alexaFire.y + 1);

                    gameManager.setLastQuestionAsked(repromptText);
                    speechOutput += repromptText;
                }

                //stateManager.setTurnState(TurnState.ALEXA);
            } else {
                return Speeches.ALREAD_TRIED_THIS_SPOT;
            }

            gameManager.setLastPlayerAttackXCoordinate(-1);
            gameManager.setLastPlayerAttackYCoordinate(-1);

            return speechOutput;
        } else {
            return String.format(Speeches.COORDINATES_NOT_VALID, 1, stateManager.getGridSize(), indexX + 1, indexY + 1);// + lastQuestion;
        }
    }

    public static boolean canFire(int indexX, int indexY, GameManager gameManager) {
        GameParameters gameParameters = gameManager.getParameters();

        return (indexX < gameParameters.getNbRows()
                && indexX >= 0
                && indexY < gameParameters.getNbColumns()
                && indexY >= 0);
    }
}