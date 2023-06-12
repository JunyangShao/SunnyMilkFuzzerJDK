import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.google.json.JsonSanitizer;

public class IdempotenceFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        String output;
        try {
            output = JsonSanitizer.sanitize(input, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        assert JsonSanitizer.sanitize(output).equals(output) : "Not idempotent";
    }
}
