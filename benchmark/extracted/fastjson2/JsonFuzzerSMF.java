import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;

public class JsonFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            JSON.parse(SMFData);
        } catch (JSONException ignored) {
        }
    }
}
