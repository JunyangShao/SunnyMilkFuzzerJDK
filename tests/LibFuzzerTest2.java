import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class LibFuzzerTest2 {
    public static void FuzzOne(String s) {
        try {
            JSON.parse(s);
        } catch (JSONException ignored) {
            return;
        }
    }
}