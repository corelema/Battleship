package gameVoiceHandler;

/**
 * Created by corentinl on 1/23/16.
 */
public class Speeches {
    //WELCOMING
    public static final String WELCOME = "Welcome to Battleship!";

    //DIDN'T UNDERSTAND
    public static final String IM_SORRY = "I'm sorry, ";
    public static final String NOT_RECOGNIZED = "I didn't understand what you said, could you repeat please?";//TODO: this is generic, we should reprompt the last thing asked.
    public static final String INCORRECT_NUMBER = "I couldn't understand the given number, could you repeat please?";

    //QUICK GAME
    public static final String QUICK_GAME_LAUNCH = "A %d by %d game has been created for you";
    public static final String GAME_ALREADY_STARTED = "The game has already began,"
            + "if you wish to start a new game, you may end this one first by saying stop.";

    //ADVANCED GAME
    public static final String ADVANCED_GAME_LAUNCH = "You asked for an advanced game.";
    public static final String ADVANCED_GAME_PARAMETERS_PROMPT = "Please provide a grid size and a number of ships";

    //GAME TURNS
    public static final String PROMPT_LINE_COLUMN = "It's your turn, please provide a line and a column where you want to hit";
    public static final String PROMPT_LINE_ONLY= "I got the column, could you provide a line now?";
    public static final String PROMPT_COLUMN_ONLY = "I got the line, could you provide a column now?";

    public static final String ALREADY_HIT = "It seems that you already hit this position, could you give me another one?";
    public static final String HIT = "That's a hit! but don't get cocky cow-boy...";
    public static final String MISS = "You lamentably missed!";

    //HELP
    public static final String HELP_SPEECH_BEGINNING = "I need you to choose between"
            + " starting a quick game,"
            + " or starting an advanced game."
            + " Which kind of game would you like to start?";
    public static final String HELP_SPEECH_BEGINNING_REPROMPT = "Which kind of game do you want to start?"
            + " If this is your first time, you can say:"
            + "start a quick game.";

    //USER LEAVING
    public static final String LEAVING_MESSAGE = "Goodbye";
}
