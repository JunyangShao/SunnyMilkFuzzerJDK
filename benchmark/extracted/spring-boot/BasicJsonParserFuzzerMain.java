import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParseException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class BasicJsonParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String content = SMFData;
        BasicJsonParser parser = new BasicJsonParser();
        try {
            parser.parseList(content);
            parser.parseMap(content);
        } catch (JsonParseException e) {
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
