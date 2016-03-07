package gameVoiceHandler.intents.handlers.Utils;

import gameData.*;
import gameData.Boards.Coordinates;
import gameData.enums.TurnState;
import gameVoiceHandler.intents.speeches.Speeches;
import gameVoiceHandler.intents.speeches.SpeechesGenerator;

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

            String speechOutput = String.format(Speeches.YOU_FIRE, (char)(indexX + 'a'), indexY + 1);
            String repromptText = "";

            AttackResponse attackResponse = gameManager.fireAtCoordinates(new Coordinates(indexX, indexY));

            if (attackResponse.isCanAttack()) {
                stateManager.setTurnState(TurnState.ALEXA);

                speechOutput += attackResponse.isAttackSuccessful() ? SpeechesGenerator.pickOne(Speeches.HIT) : SpeechesGenerator.pickOne(Speeches.MISS);

                if (gameManager.gameIsOver()) {
                    speechOutput += GameEndUtil.endingString(gameManager);
                } else {
                    Coordinates alexaFire = gameManager.nextAlexaHit();

                    repromptText = String.format(Speeches.MY_TURN, (char)(alexaFire.x + 'a'), alexaFire.y + 1);
                    String instructions = InstructionsUtil.answerInstructionsIfRequired(stateManager);
                    repromptText = instructions == null ? repromptText : repromptText + instructions;

                    gameManager.setLastQuestionAsked(repromptText);
                    speechOutput += repromptText;
                }

                stateManager.setTurnState(TurnState.ALEXA);
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
