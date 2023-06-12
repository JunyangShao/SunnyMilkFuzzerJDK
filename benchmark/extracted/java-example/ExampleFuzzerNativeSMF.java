public class ExampleFuzzerNativeSMF {

    static {
        System.loadLibrary("native");
    }

    public static void FuzzOne(String SMFData) {
        int val = data.consumeInt();
        String stringData = SMFData;
        if (val == 17759716 && stringData.length() > 10 && stringData.contains("jazzer")) {
            parse(stringData);
        }
    }

    private static native boolean parse(String bytes);
}
