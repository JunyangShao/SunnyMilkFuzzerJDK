import org.springframework.boot.actuate.autoconfigure.cloudfoundry.*;

public class TokenFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String content = SMFData;
        try {
            Token t = new Token(content);
        } catch (CloudFoundryAuthorizationException e) {
        }
    }
}
