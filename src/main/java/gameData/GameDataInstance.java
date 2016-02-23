package gameData;

import com.amazon.speech.speechlet.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import gameData.Utils.AttributesUtil;

/**
 * Created by corentinl on 2/18/16.
 */
public class GameDataInstance {
    @JsonIgnore
    private static GameDataInstance instance;
    @JsonIgnore
    private static Session session;

    private static GameManager gameManager;
    private static StateManager stateManager;


    private GameDataInstance() {
    }

    private static void initialization() {
        gameManager = (GameManager) AttributesUtil.readAttribute(AttributesUtil.GAME_MANAGER, session);
        stateManager = (StateManager) AttributesUtil.readAttribute(AttributesUtil.GAME_MANAGER, session);
        if (stateManager == null) {
            stateManager = new StateManager();
        }
    }

    public static GameDataInstance initialize(Session session) {
        instance = new GameDataInstance();

        GameDataInstance.session = session;
        initialization();

        return instance;
    }

    public void serializeAll() {
        serializeGameManager();
        serializeStateManager();
    }

    public void serializeGameManager() {
        AttributesUtil.writeAttribute(AttributesUtil.GAME_MANAGER, gameManager, session);
    }

    public void serializeStateManager() {
        AttributesUtil.writeAttribute(AttributesUtil.STATE_MANAGER, stateManager, session);
    }

    public boolean isGameManagerInstantiated() {
        return (gameManager != null);
    }

    public boolean isStateManagerInstantiated() {
        return (stateManager != null);
    }

    /*GETTERS AND SETTERS*/

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }
}
