import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.json.JSONObject;
import org.json.JSONException;

public class JsonJavaFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            JSONObject jo = new JSONObject(data.consumeRemainingAsString());
        } catch (JSONException e) {
        }
    }
}
