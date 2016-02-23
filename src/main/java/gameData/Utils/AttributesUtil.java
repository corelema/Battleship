package gameData.Utils;

import com.amazon.speech.speechlet.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import gameData.GameManager;
import gameData.StateManager;

/**
 * Created by corentinl on 1/29/16.
 */
public class AttributesUtil {
    public static final String GAME_MANAGER = "GAMEMANAGER";
    public static final String STATE_MANAGER = "STATEMANAGER";

    private AttributesUtil(){

    }

    public static void writeAttribute(String attributeName, Object value, Session session) {
        if (session != null) {
            session.setAttribute(attributeName, value);
        }
    }

    public static Object readAttribute(String attributeName, Session session) {
        if (session == null) {
            return null;
        }
        Object deserializedAttribute = null;

        Object attribute = session.getAttribute(attributeName);

        if (attribute != null) {
            java.util.LinkedHashMap attributeMap =  (java.util.LinkedHashMap)attribute;
            final ObjectMapper mapper = new ObjectMapper();

            Class classType = classMapper(attributeName);

            deserializedAttribute = mapper.convertValue(attributeMap, classType);
        }

        return deserializedAttribute;
    }

    private static Class classMapper(String attributeName) {
        Class classType = null;
        switch(attributeName) {
            case GAME_MANAGER:
                classType = GameManager.class;
                break;
            case STATE_MANAGER:
                classType = StateManager.class;
                break;
        }

        return classType;
    }
}
