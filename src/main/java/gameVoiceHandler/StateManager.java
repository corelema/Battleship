package gameVoiceHandler;

/**
 * Created by corentinl on 1/23/16.
 */
public class StateManager {
    private static StateManager instance = null;

    private StateManager() {

    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public boolean isGamesStarted(){
        return false;
    }

}
