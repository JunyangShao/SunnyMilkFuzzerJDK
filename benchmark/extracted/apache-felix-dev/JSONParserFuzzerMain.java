import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.felix.utils.json.JSONParser;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        boolean switchConstructor = data.consumeBoolean();
        int switchEncoding = data.consumeInt(0, 2);
        String string = SMFData;
        try {
            JSONParser jSONParser;
            if (switchConstructor) {
                CharSequence charSeq = string;
                jSONParser = new JSONParser(charSeq);
            } else {
                String encoding = "UTF_8";
                switch(switchEncoding) {
                    case 0:
                        break;
                    case 1:
                        encoding = "UTF_16";
                        break;
                    case 2:
                        encoding = "UTF_32";
                        break;
                }
                InputStream inputStream = new ByteArrayInputStream(string.getBytes(encoding));
                jSONParser = new JSONParser(inputStream);
            }
            List<Object> res = jSONParser.getParsedList();
            Map<String, Object> parsed = jSONParser.getParsed();
        } catch (IllegalArgumentException | IOException e) {
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
