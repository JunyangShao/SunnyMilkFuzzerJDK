import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class TestProgram extends SunnyMilkFuzzer {
    public void FuzzOne(String s) {
        try {
            JSON.parse(s);
        } catch (JSONException ignored) {
            return;
        }
    }

    public static void main(String[] args) {
        new TestProgram().Loop();
    }
}