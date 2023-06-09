import java.util.ArrayList;
public abstract class SunnyMilkFuzzer {
    static {
        System.loadLibrary("SunnyMilkFuzzer");
    }
    private long time_elapsed = 0;
    private long loop_start = 0;
    private native boolean HasNewCoverage();
    private native void SetCoverageTracing();
    private native void UnsetCoverageTracing();
    private native void ClearCoverageMap();
    private native String Mutate(String s, int size);
    public abstract void FuzzOne(String input);
    public void Loop() {
        // example json
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.add("");
        ClearCoverageMap();
        // loop_start = System.nanoTime();
        for(int i = 0; i < 1000000; i++) {
            if (i % 5000 == 0) {
                System.out.println("[" + i + "]\tMonitoring...");
            }
            String old_input = inputs.get(i % inputs.size());
            String new_input = Mutate(old_input, old_input.length());
            // long start = System.nanoTime();
            SetCoverageTracing();
            FuzzOne(new_input);
            UnsetCoverageTracing();
            // long finish = System.nanoTime();
            if (HasNewCoverage()) {
                System.out.print("[" + i + "]\t" + "New Input: ");
                System.out.println(new_input);
                inputs.add(new_input);
            }
            // time_elapsed += finish - start;
        }
        // System.out.println(time_elapsed);
        // System.out.println(System.nanoTime() - loop_start);
    }
}