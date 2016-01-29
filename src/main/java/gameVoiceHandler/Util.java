package gameVoiceHandler;

import com.amazon.speech.speechlet.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import gameData.GameManager;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by corentinl on 1/29/16.
 */
public class Util {
    private static final String GAME_MANAGER = "GAMEMANAGER";
    public static Session session;


    /*
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
    */

    public static void updateGameManager(GameManager gameManager) {
        List<String> test = new ArrayList<String>();

        test.add("coucou");
        test.add("je suis la");
            session.setAttribute(GAME_MANAGER, test);
    }

}
