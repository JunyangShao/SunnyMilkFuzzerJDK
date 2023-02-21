public class TestProgram2 extends SunnyMilkFuzzer {
    public void FuzzOne(String s) {
        if (s.length() > 0) {
            if (s.charAt(0) == 'f') {
                if (s.length() > 1) {
                    if (s.charAt(1) == 'u') {
                        if (s.length() > 2) {
                            if (s.charAt(2) == 'z') {
                                if (s.length() > 3) {
                                    if (s.charAt(3) == 'z') {
                                        System.err.println("Fuzzed!");
                                        System.exit(1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new TestProgram2().Loop();
    }
}