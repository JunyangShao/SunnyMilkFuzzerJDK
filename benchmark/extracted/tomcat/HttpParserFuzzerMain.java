import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import org.apache.tomcat.util.http.parser.HttpParser;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        HttpParser.unquote(input);
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
