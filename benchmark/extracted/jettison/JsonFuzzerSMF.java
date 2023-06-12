import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

public class JsonFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            JSONObject obj = new JSONObject(SMFData);
        } catch (JSONException e) {
            return;
        }
    }
}
