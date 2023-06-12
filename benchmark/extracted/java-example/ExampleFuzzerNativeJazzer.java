import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class ExampleFuzzerNativeJazzer {

    static {
        System.loadLibrary("native");
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        int val = data.consumeInt();
        String stringData = data.consumeRemainingAsString();
        if (val == 17759716 && stringData.length() > 10 && stringData.contains("jazzer")) {
            parse(stringData);
        }
    }

    private static native boolean parse(String bytes);
}
