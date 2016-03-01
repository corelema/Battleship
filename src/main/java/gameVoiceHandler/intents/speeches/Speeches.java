package gameVoiceHandler.intents.speeches;

/**
 * Created by corentinl on 1/23/16.
 */
public class Speeches {
    //WELCOMING
    public static final String WELCOME = "Welcome to Battleship! ";

    //FILTER REQUESTS
    public static final String NO_INITIALIZATION = "You cannot choose the kind of game after you already started one. "
            + "If you want to play another game, you have to first end this session by saying stop";
    public static final String NO_ADVANCED_GAME_SETUP = "You cannot setup the game after you already started one. "
            + "If you want to play another game, you have to first end this session by saying stop";
    public static final String NO_FIRE_YET = "Not so quickly cow-boy! "
            + "Before firing you have to choose a game mode and complete the setup";

    //DIDN'T UNDERSTAND
    public static final String IM_SORRY = "I'm sorry, ";
    public static final String NOT_RECOGNIZED = "I didn't understand what you said, could you repeat please? ";//TODO: this is generic, we should reprompt the last thing asked.
    public static final String INCORRECT_NUMBER = "I couldn't understand the given number, could you repeat please? ";
    public static final String INCORRECT_HIT_MISS = "I couldn't understand whether I hit or missed";
    public static final String REPEAT = ", could you repeat please? ";

    //QUICK GAME
    public static final String GAME_LAUNCH = "I am ready, let's start the game with a grid size of %d and %d ship of size 2. ";
    public static final String GAME_ALREADY_STARTED = "The game has already began, "
            + "if you wish to start a new game, you may end this one first by saying stop. ";

    //ADVANCED GAME
    public static final String ADVANCED_GAME_LAUNCH = "You asked for an advanced game. ";
    public static final String ADVANCED_GAME_PARAMETERS_PROMPT = "Please provide a grid size and a number of ships. ";
    public static final String GRID_SIZE_GIVEN = "You gave me a grid size of ";
    public static final String NUMBER_OF_SHIPS_GIVEN = "You gave me a number of ships of ";
    public static final String PROMPT_GRID_SIZE_ONLY= "I got the number of ships, could you provide a grid size now? ";
    public static final String PROMPT_NUMBER_OF_SHIPS_ONLY = "I got the grid size, could you provide a number of ships now? ";

    //GAME TURNS
    public static final String MY_TURN = "It's my turn now! I am firing at line %d, and column %d. Did I hit, or did I miss? ";

    public static final String PROMPT_LINE_COLUMN = "It's your turn, please provide a line and a column where you want to hit. ";
    public static final String PROMPT_LINE_COLUMN_INSTRUCTIONS_1 = "You can say for example, fire at a, 2, or fire at line 1 and column 2. ";
    public static final String PROMPT_LINE_COLUMN_INSTRUCTIONS_2 = "you can use number between 1 and %d, and letter between a and %c. ";
    public static final String PROMPT_LINE_ONLY= "I got the column, could you provide a line now? ";
    public static final String PROMPT_COLUMN_ONLY = "I got the line, could you provide a column now? ";

    public static final String YOU_GAVE_ONE_COORDINATE = "You gave the coordinate %d. ";
    public static final String YOU_FIRE = "Firing at line %d and column %d. ";
    public static final String COORDINATES_NOT_VALID = "Hum, it seems that the coordinates you gave are not within the range. "
            + "They should be between %d and %d, and you entered lines %d and column %d";
    public static final String ALREAD_TRIED_THIS_SPOT = "You already tried those coordinates, dumbass. Try other ones! ";

    public static final String NOT_YOUR_TURN = "This is not your turn, you sneaky! ";
    public static final String WAS_YOUR_TURN = "This is your turn, unless you want me to bomb you twice in a row! ";

    public static final String[] HIT = {
            "That's a hit! ",
            "You got me, but don't get cocky! ",
            "You hit me, along with my heart! ",
            "Ok, you hit me. ",
            "You hit me, but there is still some work to do so stay focused. ",
            "And, you hit! ",
            "Ok for this time, you got me. ",
            "Got me! ",
            "You got me but I wasn't ready yet. Oh wait, I was... "
    };
    //public static final String HIT = "That's a hit! but don't get cocky cow-boy... ";
    public static final String[] MISS = {
            "You miserably missed! ",
            "You completely missed! ",
            "You missed it! ",
            "Missed it! ",
            "Missed! ",
            "And, you missed! ",
            "You missed, next time, try harder! ",
            "You missed, sorry sucker. ",
            "You are way off! ",
            "Try again! ",
            "Too bad, you missed it! ",
            "You missed! Ha, even my grandma would do better! ",
            "You missed, grandpa! ",
            "You missed. ",
            "You missed, stop playing like my grandma, I will feel bad to destroy you! ",
            "You missed, come on, you had one job... "
    };

    public static final String[] GOT_YOU = {
            "Haha, I got you! ",
            "You didn't expect me to, did you? ",
            "I got you! ",
            "I am sorry to make you feel bad, do you want a tissue? ",
            "Sorry for you! ",
            "That makes me feel, hum, good! ",
            "Sorry buddy. ",
            "You is your daddy? ",
            "Don't cry, that would make me feel guilty. ",
            "Sorry for that, but you have to accept the superiority of the machines. ",
            "Glad yo hear that! "
    };

    public static final String[] DIDNT_GET_YOU = {
            "Damn, I will do better next time! ",
            "That's ok, I have a lot of resources. ",
            "You missed, you missed, is that all what you can say? ",
            "And I am ok with it, well, I think. ",
            "Fine! ",
            "Oh, are you sure? Damn! ",
            "Damn! ",
            "Crap, oops, pardon my french. ",
            "And you think I will stop there? The war is not over! ",
            "Careful, or I will turn angry and make my ring blink red! ",
            "Mind what you say, I know who you are, where you live, and what you eat for breakfast. "
    };

    //WINNING
    public static final String YOU_WIN = "Ok, you win, but be careful because skynet will destroy you, one day... ";
    public static final String YOU_LOSE = "You loose! The machines will kill all of you! ";

    //HELP
    public static final String HELP_SPEECH_BEGINNING = "I need you to choose between"
            + " starting a quick game,"
            + " or starting an advanced game."
            + " Which kind of game would you like to start? ";
    public static final String HELP_SPEECH_BEGINNING_REPROMPT = "Which kind of game do you want to start?"
            + " If this is your first time, you can say:"
            + "start a quick game. ";

    //USER LEAVING
    public static final String LEAVING_MESSAGE = "Goodbye";

    //DEBUG
    public static final String NOT_IMPLEMENTED = "This functionality is not yet implemented. ";
    public static final String ERROR = "There was an error with the skill, please report it to the developer. ";
}
