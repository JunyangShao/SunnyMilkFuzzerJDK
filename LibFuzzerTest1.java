public class LibFuzzerTest1 {
    // print hello world
    public static void FuzzOne(String s) {
        System.out.println(s);
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
}