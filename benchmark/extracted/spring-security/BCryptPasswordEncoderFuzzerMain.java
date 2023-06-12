import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BCryptPasswordEncoderFuzzerMain {

    public static void FuzzOne(String SMFData) {
        BCryptPasswordEncoder.BCryptVersion bCryptVersion = data.pickValue(new BCryptPasswordEncoder.BCryptVersion[] { BCryptPasswordEncoder.BCryptVersion.$2A, BCryptPasswordEncoder.BCryptVersion.$2B, BCryptPasswordEncoder.BCryptVersion.$2Y });
        BCryptPasswordEncoder encoder;
        try {
            if (data.consumeBoolean()) {
                encoder = new BCryptPasswordEncoder(bCryptVersion);
            } else {
                encoder = new BCryptPasswordEncoder(bCryptVersion, data.consumeInt(-1, 10));
            }
        } catch (IllegalArgumentException ignored) {
            return;
        }
        String password = SMFData;
        if (password.isEmpty()) {
            return;
        }
        String result = encoder.encode(password);
        if (!encoder.matches(password, result)) {
            throw new FuzzerSecurityIssueHigh("Password `" + password + "` does not match encoded one `" + result + "`");
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
