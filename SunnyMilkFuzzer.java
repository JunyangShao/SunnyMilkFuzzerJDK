public abstract class SunnyMilkFuzzer {
    static {
        System.loadLibrary("SunnyMilkFuzzer");
    }
    private native void PrintCoverage();
    public abstract void FuzzOne(String input);
    public void Loop() {
        String input = "";
        for(int i = 0; i < 100000; i++) {
            PrintCoverage();
            FuzzOne(input);
        }
    }
}