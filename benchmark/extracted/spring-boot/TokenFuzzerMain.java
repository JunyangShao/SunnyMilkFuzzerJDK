import org.springframework.boot.actuate.autoconfigure.cloudfoundry.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TokenFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String content = SMFData;
        try {
            Token t = new Token(content);
        } catch (CloudFoundryAuthorizationException e) {
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
