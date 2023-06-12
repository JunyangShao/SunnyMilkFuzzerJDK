import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.json.JsonSanitizer;

public class ValidJsonFuzzerJazzer {

    private static Gson gson = new Gson();

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        String output;
        try {
            output = JsonSanitizer.sanitize(input, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        try {
            Gson gson = new Gson();
            gson.fromJson(output, JsonElement.class);
        } catch (Exception e) {
            throw new FuzzerSecurityIssueLow("Output is invalid JSON", e);
        }
    }
}
