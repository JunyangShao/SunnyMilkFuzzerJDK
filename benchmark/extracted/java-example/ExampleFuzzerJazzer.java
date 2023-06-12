import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.security.SecureRandom;

public class ExampleFuzzerJazzer {

    public static void fuzzerInitialize() {
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        long random = 123123132;
        if (input.startsWith("magicstring" + random) && input.length() > 30 && input.charAt(25) == 'C') {
            throw new IllegalStateException("Not reached");
        }
    }
}
