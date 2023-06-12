import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.*;

public class TokenFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String content = data.consumeRemainingAsString();
        try {
            Token t = new Token(content);
        } catch (CloudFoundryAuthorizationException e) {
        }
    }
}
