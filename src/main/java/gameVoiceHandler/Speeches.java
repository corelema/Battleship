package gameVoiceHandler;

/**
 * Created by corentinl on 1/23/16.
 */
public class Speeches {
    //QUICK GAME
    public static final String QUICK_GAME_LAUNCH = "A %d by %d game has been created for you";

    //GAME TURNS
    public static final String YOUR_TURN = "It's your turn, please provide a line and a column where you want to hit";
    public static final String YOUR_TURN_LINE_ONLY = "I got the column, could you provide a line now?";
    public static final String YOUR_TURN_COLUMN_ONLY = "I got the line, could you provide a column now?";

    public static final String ALREADY_HIT = "It seems that you already hit this position, could you give me another one?";
    public static final String HIT = "That's a hit! but don't get cocky cow-boy...";
    public static final String MISS = "You lamentably missed!";

    //HELP
    public static final String HELP_SPEECH_BEGINNING = "With Battleship, you can"
            + " start a quick game or"
            + " start an advanced game."
            + " Which kind of game would you like to start?";
    public static final String HELP_SPEECH_BEGINNING_REPROMPT = "Which kind of game do you want to start?";

    //USER LEAVING
    public static final String LEAVING_MESSAGE = "Goodbye";
}
