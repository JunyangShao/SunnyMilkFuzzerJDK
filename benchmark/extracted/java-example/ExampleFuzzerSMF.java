import java.security.SecureRandom;

public class ExampleFuzzerSMF {

    public static void fuzzerInitialize() {
    }

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        long random = 123123132;
        if (input.startsWith("magicstring" + random) && input.length() > 30 && input.charAt(25) == 'C') {
            throw new IllegalStateException("Not reached");
        }
    }
}
