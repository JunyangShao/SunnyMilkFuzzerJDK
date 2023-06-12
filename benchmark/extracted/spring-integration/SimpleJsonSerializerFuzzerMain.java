import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.integration.json.SimpleJsonSerializer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleJsonSerializerFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String string = data.consumeString(250);
        String info = SMFData;
        String json = SimpleJsonSerializer.toJson(string, info);
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
