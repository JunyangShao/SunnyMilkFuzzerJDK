import org.springframework.cloud.context.encrypt.EncryptorFactory;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import java.nio.charset.Charset;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import java.math.BigInteger;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.cloud.context.encrypt.KeyFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptionIntegrationFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String keyStr = data.consumeString(100);
        String salt = data.consumeString(50);
        if (keyStr.isEmpty() || salt.isEmpty()) {
            return;
        }
        String content = SMFData;
        TextEncryptor encryptor;
        try {
            encryptor = new EncryptorFactory(salt).create(keyStr);
        } catch (KeyFormatException | IllegalArgumentException e) {
            return;
        }
        String encrypted = encryptor.encrypt(content);
        String decrypted = encryptor.decrypt(encrypted);
        if (!decrypted.equals(content)) {
            throw new FuzzerSecurityIssueHigh("Different result when encrypting & decrypting: " + decrypted + " != " + content);
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
