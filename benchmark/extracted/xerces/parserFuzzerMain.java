import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import java.nio.file.Files;
import java.nio.file.Paths;

public class parserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String feature = data.consumeString(100);
        boolean state = data.consumeBoolean();
        String property = data.consumeString(100);
        String value = data.consumeString(100);
        if (feature.isEmpty() | property.isEmpty()) {
            return;
        }
        String content = SMFData;
        try {
            DOMParser parser = new DOMParser();
            parser.setFeature(feature, state);
            parser.getFeature(feature);
            parser.setProperty(property, value);
            String getValue = (String) parser.getProperty(property);
            parser.parse(content);
            Document document = parser.getDocument();
        } catch (Exception e) {
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
