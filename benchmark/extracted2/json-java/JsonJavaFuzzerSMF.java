import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.json.JSONObject;
import org.json.JSONException;

public class JsonJavaFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            JSONObject jo = new JSONObject(SMFData);
        } catch (JSONException e) {
        }
    }
}
