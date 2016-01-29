package gameVoiceHandler;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletResponse;
import gameData.AttackResponse;
import gameData.GameManager;
import gameData.GameParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.peer.ScrollbarPeer;

/**
 * Created by corentinl on 1/23/16.
 */
public class OrdersTranslator {
    private static final Logger log = LoggerFactory.getLogger(OrdersTranslator.class);

    private static GameManager gameManager;

    private static final String GRID_SIZE_SLOT = "gridSize";
    private static final String NUMBER_OF_SHIPS_SLOT = "numberOfShips";
    private static final String HIT_OR_MISS_SLOT = "hitOrMiss";
    private static final String LINE_SLOT = "line";
    private static final String COLUMN_SLOT = "column";
    private static final String LINE_LETTER_SLOT = "lineLetter";
    private static final String COLUMN_NUMBER_SLOT = "columnNumber";
    private static final String LINE_OR_COLUMN_SLOT = "lineOrColumn";

    private static String lastQuestion = "Oops. It seems that there is a bug";

    private static int x = -1, y = -1;

    public static SpeechletResponse handleQuickGameAsked(StateManager stateManager) {
        stateManager.startQuickGame();
        initializeGameManager(stateManager);

        String speechOutput = startGameSpeech(stateManager) + Speeches.PROMPT_LINE_COLUMN;
        String repromptText = Speeches.PROMPT_LINE_COLUMN;
        lastQuestion = repromptText;

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }

    public static SpeechletResponse handleAdvancedGameAsked(StateManager stateManager) {
        if (!stateManager.isGamesStarted()) {
            stateManager.advancedGameAsked();
            String speechOutput = Speeches.ADVANCED_GAME_LAUNCH + Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;

            String repromptText = Speeches.ADVANCED_GAME_PARAMETERS_PROMPT;
            lastQuestion = repromptText;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            String speechOutput = Speeches.GAME_ALREADY_STARTED;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    public static SpeechletResponse handleAdvancedGameAsked(Intent intent, StateManager stateManager) {
        Slot gridSizeSlot = intent.getSlot(GRID_SIZE_SLOT);
        Slot numberOfShipsSlot = intent.getSlot(NUMBER_OF_SHIPS_SLOT);

        try {
            if (gridSizeSlot != null && numberOfShipsSlot != null) {
                stateManager.setGridSize(Integer.parseInt(gridSizeSlot.getValue()));
                stateManager.setNumberOfShips(Integer.parseInt(numberOfShipsSlot.getValue()));
            }
        } catch (NumberFormatException e) {
            String speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (stateManager.isGameReadyToBeStarted()) {
            return startAdvancedGame(stateManager);
        } else {
            String speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }
    }
    
    private static SpeechletResponse startAdvancedGame(StateManager stateManager) {
        if (!stateManager.isGamesStarted()) {
            stateManager.startAdvancedGame();
            initializeGameManager(stateManager);

            String speechOutput = startGameSpeech(stateManager) + Speeches.PROMPT_LINE_COLUMN;
            String repromptText = Speeches.PROMPT_LINE_COLUMN;
            lastQuestion = repromptText;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        }

        String speechOutput = Speeches.GAME_ALREADY_STARTED;
        return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
    }

    /**
     * handles one parameter given at a time
     * the parameter can be in either of the slots:
     * GRID_SIZE_SLOT
     * NUMBER_OF_SHIPS_SLOT
     * */
    public static SpeechletResponse handleParameterGiven(Intent intent, StateManager stateManager) {
        String speechOutput = "";
        try {
            if (intent.getSlot(GRID_SIZE_SLOT) != null) {
                stateManager.setGridSize(Integer.parseInt(intent.getSlot(GRID_SIZE_SLOT).getValue()));
                speechOutput = Speeches.GRID_SIZE_GIVEN + stateManager.getGridSize() + ". ";
            } else if (intent.getSlot(NUMBER_OF_SHIPS_SLOT) != null) {
                stateManager.setNumberOfShips(Integer.parseInt(intent.getSlot(NUMBER_OF_SHIPS_SLOT).getValue()));
                speechOutput = Speeches.NUMBER_OF_SHIPS_GIVEN + stateManager.getNumberOfShips() + ". ";
            } else {
                return SpeechesGenerator.newTellResponse(Speeches.ERROR);
            }
        } catch (NumberFormatException e) {
            speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        }

        if (stateManager.isGameReadyToBeStarted()) {
            if (stateManager.isGameReadyToBeStarted()) {
                return startAdvancedGame(stateManager);
            } else {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER + Speeches.REPEAT;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }
            
        } else {
            if (stateManager.isGridSizeCorrect()) {
                speechOutput += Speeches.PROMPT_NUMBER_OF_SHIPS_ONLY;
            } else {
                speechOutput += Speeches.PROMPT_GRID_SIZE_ONLY;
            }
        }

        return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
    }

    /**
     * handles two fire coordinates given at a time
     * the parameters can be either the slots:
     * line -> AMAZON.NUMBER
     * column -> AMAZON.NUMBER
     * OR
     * lineLetter -> LETTERS
     * columnNumber -> AMAZON.NUMBER
     * */
    public static SpeechletResponse handleTwoFireCoordinatesGiven(Intent intent, StateManager stateManager) {
        if (stateManager.getTurnState().equals(StateManager.PLAYER)) {
            Slot lineSlot = intent.getSlot(LINE_SLOT);
            Slot columnSlot = intent.getSlot(COLUMN_SLOT);
            Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
            Slot columnNumberSlot = intent.getSlot(COLUMN_NUMBER_SLOT);

            String speechOutput = "";

            try {
                if (lineSlot != null && columnSlot != null) {
                    x = Integer.parseInt(lineSlot.getValue());
                    y = Integer.parseInt(columnSlot.getValue());
                } else if (lineLetterSlot != null && columnNumberSlot != null) {
                    String givenChar = lineLetterSlot.getValue();
                    x = givenChar.toCharArray()[0] - 'a' + 1;
                    if (x < 0) {
                        x = givenChar.toCharArray()[0] - 'A' + 1;
                    }
                    y = Integer.parseInt(columnNumberSlot.getValue());
                } else {
                    return SpeechesGenerator.newTellResponse(Speeches.ERROR);
                }
            } catch (NumberFormatException e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            } catch (Exception e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }

            speechOutput += fire(stateManager);

            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        } else {
            String speechOutput = Speeches.NOT_YOUR_TURN + lastQuestion;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    /**
     * handles one fire coordinates given at a time
     * the parameter can be either the slot:
     * lineOrColumn -> AMAZON.NUMBER
     * OR
     * lineLetter -> LETTERS
     * */
    public static SpeechletResponse handleOneFireCoordinatesGiven(Intent intent, StateManager stateManager) {
        if (stateManager.getTurnState().equals(StateManager.PLAYER)) {
            Slot lineLetterSlot = intent.getSlot(LINE_LETTER_SLOT);
            Slot lineOrColumnSlot = intent.getSlot(LINE_OR_COLUMN_SLOT);

            int z = -1;

            String speechOutput = "";

            try {
                if (lineOrColumnSlot != null) {
                    z = Integer.parseInt(lineLetterSlot.getValue());
                    speechOutput = String.format(Speeches.YOU_GAVE_ONE_COORDINATE, z);
                } else if (lineLetterSlot != null) {
                    String givenChar = lineLetterSlot.getValue();
                    z = givenChar.toCharArray()[0] - 'a' + 1;
                    if (z < 0) {
                        z = givenChar.toCharArray()[0] - 'A' + 1;
                    }
                    speechOutput = String.format(Speeches.YOU_GAVE_ONE_COORDINATE, z);
                } else {
                    return SpeechesGenerator.newTellResponse(Speeches.ERROR);
                }
            } catch (NumberFormatException e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            } catch (Exception e) {
                speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
            }

            if (x == -1) {
                x = z;
            } else {
                if (y == -1) {
                    y = z;
                } else {
                    speechOutput = Speeches.IM_SORRY + Speeches.INCORRECT_NUMBER;
                    return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
                }

                speechOutput += fire(stateManager);
            }
            return SpeechesGenerator.newAskResponse(speechOutput, false, speechOutput, false);
        } else {
            String speechOutput = Speeches.NOT_YOUR_TURN + lastQuestion;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    private static String fire(StateManager stateManager) {
        if (canFire(stateManager)) {
            String speechOutput = String.format(Speeches.YOU_FIRE, x, y);
            String repromptText = "";

            AttackResponse attackResponse = gameManager.fireAtPoint(new Point(x - 1, y - 1));

            if (attackResponse.isCanAttack()) {
                if (attackResponse.isAttackSuccessful()) {
                    speechOutput += Speeches.HIT;
                    if (gameManager.isGameOver()) {
                        speechOutput += endGame();
                    }
                } else {
                    speechOutput += Speeches.MISS;
                }

                if (!gameManager.isGameOver()) {

                    Point alexaFire = gameManager.getNextAlexaHit();

                    repromptText = String.format(Speeches.MY_TURN, alexaFire.x + 1, alexaFire.y + 1);
                    stateManager.setTurnState(StateManager.ALEXA);

                    lastQuestion = repromptText;
                    speechOutput += repromptText;
                }

                //stateManager.setTurnState(TurnState.ALEXA);
            } else {
                return Speeches.ALREAD_TRIED_THIS_SPOT;
            }

            x = -1;
            y = -1;

            return speechOutput;
        } else {
            return String.format(Speeches.COORDINATES_NOT_VALID, 1, stateManager.getGridSize(), x, y) + lastQuestion;
        }
    }

    private static String endGame(){
        if (gameManager.didAlexWin()) {
            return Speeches.YOU_LOSE;
        } else {
            return Speeches.YOU_WIN;
        }
    }

    private static boolean canFire(StateManager stateManager) {
        int indexX = x-1;
        int indexY = y-1;

        return (indexX < stateManager.getGridSize()
                && indexX >= 0
                && indexY < stateManager.getGridSize()
                && indexY >= 0);
    }

    /**
     * handles two fire coordinates given at a time
     * */
    public static SpeechletResponse handleFireResultGiven(Intent intent, StateManager stateManager) {
        if (stateManager.getTurnState().equals(StateManager.PLAYER)) {
            Slot hitOrMissSlotSlot = intent.getSlot(HIT_OR_MISS_SLOT);

            String speechOutput = "";
            if (hitOrMissSlotSlot != null && hitOrMissSlotSlot.getValue() != null) {
                boolean isHit = hitOrMissSlotSlot.getValue().equals("hit");

                //TODO: call the game: give true or false, to say that Alexa hit or missed.

                if (isHit) {
                    speechOutput = "Haha, I hit you! ";
                } else {
                    speechOutput = "Damn, I will do better next time! ";
                }

                stateManager.setTurnState(StateManager.PLAYER);

                if (gameManager.isGameOver()) {
                    speechOutput += endGame();
                    return SpeechesGenerator.newTellResponse(speechOutput);
                }
                speechOutput += Speeches.PROMPT_LINE_COLUMN;
                String repromptText = Speeches.PROMPT_LINE_COLUMN;
                lastQuestion = repromptText;

                stateManager.setTurnState(StateManager.PLAYER);

                return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
            } else {
                return handleHelpAsked(stateManager);
            }
        } else {
            String speechOutput = Speeches.WAS_YOUR_TURN + lastQuestion;
            return SpeechesGenerator.newAskResponse(speechOutput, false, lastQuestion, false);
        }
    }

    public static SpeechletResponse handleHelpAsked(StateManager stateManager) {
        if (!stateManager.isGamesStarted()) {
            String speechOutput = Speeches.HELP_SPEECH_BEGINNING;

            String repromptText = Speeches.HELP_SPEECH_BEGINNING_REPROMPT;

            return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
        } else {
            //TODO: determine the current state and give appropriate help.
            //TODO: and help wasn't asked but result in a call from another handler, give an error message. Maybe the whole error process should be handled in a different method or class
            return null;
        }
    }

    public static SpeechletResponse handleCancel(StateManager stateManager) {
        //stateManager.reset();

        String speechOutput = Speeches.LEAVING_MESSAGE;

        return SpeechesGenerator.newTellResponse(speechOutput);
    }

    public static SpeechletResponse handleStop(StateManager stateManager) {
        //stateManager.reset();

        String speechOutput = Speeches.LEAVING_MESSAGE;

        return SpeechesGenerator.newTellResponse(speechOutput);
    }

    public static SpeechletResponse handleUnrecognizedIntent() {
        String speechOutput = Speeches.IM_SORRY + Speeches.NOT_RECOGNIZED + Speeches.REPEAT;
        String repromptText = Speeches.NOT_RECOGNIZED;

        return SpeechesGenerator.newAskResponse(speechOutput, false, repromptText, false);
    }

    private static void initializeGameManager(StateManager stateManager) {
        int gridSize = stateManager.getGridSize();
        int numberOfShips = stateManager.getNumberOfShips();
        GameParameters gameParameters = new GameParameters(gridSize, gridSize, 1, numberOfShips);
        gameManager = new GameManager(gameParameters);
    }

    private static String startGameSpeech(StateManager stateManager) {
        String speechOutput = String.format(Speeches.GAME_LAUNCH, stateManager.getGridSize(), stateManager.getNumberOfShips());

        return speechOutput;
    }
}
