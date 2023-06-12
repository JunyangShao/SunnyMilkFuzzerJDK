import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.integration.json.SimpleJsonSerializer;

public class SimpleJsonSerializerFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String string = data.consumeString(250);
        String info = data.consumeRemainingAsString();
        String json = SimpleJsonSerializer.toJson(string, info);
    }
}
