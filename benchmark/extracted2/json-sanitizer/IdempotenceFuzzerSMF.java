import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.google.json.JsonSanitizer;

public class IdempotenceFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        String output;
        try {
            output = JsonSanitizer.sanitize(input, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        assert JsonSanitizer.sanitize(output).equals(output) : "Not idempotent";
    }
}
