package gameVoiceHandler;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by corentinl on 1/20/16.
 */
public class BattleshipSPeechletRequestsStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.echo-sdk-ams.app.4bca183b-9938-4203-a19f-ce41c60b40f2");
    }

    public BattleshipSPeechletRequestsStreamHandler() {
        super(new BattleshipSpeechlet(), supportedApplicationIds);
    }
}
