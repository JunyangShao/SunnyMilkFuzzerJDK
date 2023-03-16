import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class JNIFuzzerTest1 extends SunnyMilkFuzzer {
    public void FuzzOne(String s) {
        try {
            JSON.parse(s);
        } catch (JSONException ignored) {
            return;
        }
    }

    public static void main(String[] args) {
        new JNIFuzzerTest1().Loop();
    }
}