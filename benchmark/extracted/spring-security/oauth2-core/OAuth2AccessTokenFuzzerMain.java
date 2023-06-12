import org.springframework.security.oauth2.core.OAuth2AccessToken;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OAuth2AccessTokenFuzzerMain {

    private static final OAuth2AccessToken.TokenType TOKEN_TYPE = OAuth2AccessToken.TokenType.BEARER;

    private static final Instant ISSUED_AT = Instant.now();

    private static final Instant EXPIRES_AT = Instant.from(ISSUED_AT).plusSeconds(60);

    public static void FuzzOne(String SMFData) {
        Set<String> scope;
        String tmpScope;
        String value;
        boolean proceed = true;
        OAuth2AccessToken accessToken = null;
        boolean isScope = data.consumeBoolean();
        if (isScope) {
            tmpScope = data.consumeString(250);
            value = data.consumeString(250);
            scope = new LinkedHashSet<>(Arrays.asList(tmpScope));
            try {
                accessToken = new OAuth2AccessToken(TOKEN_TYPE, value, ISSUED_AT, EXPIRES_AT, scope);
            } catch (IllegalArgumentException iae) {
                proceed = false;
            }
        } else {
            value = SMFData;
            try {
                accessToken = new OAuth2AccessToken(TOKEN_TYPE, value, ISSUED_AT, EXPIRES_AT);
            } catch (IllegalArgumentException iae) {
                proceed = false;
            }
        }
        if (proceed) {
            String tokenValue = accessToken.getTokenValue();
            int hashCode = accessToken.hashCode();
            OAuth2AccessToken compareToken = new OAuth2AccessToken(TOKEN_TYPE, value, ISSUED_AT, EXPIRES_AT);
            boolean compareTokens = accessToken.equals(compareToken);
        }
    }

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
