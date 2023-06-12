import org.bson.json.JsonParseException;
import org.springframework.data.mongodb.util.json.ParameterBindingJsonReader;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParameterBindingJsonReaderFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try (ParameterBindingJsonReader reader = new ParameterBindingJsonReader(SMFData)) {
            reader.doReadDecimal128();
            reader.getMark();
            reader.readBsonType();
        } catch (SpelParseException | SpelEvaluationException | IllegalStateException | IllegalArgumentException | JsonParseException e) {
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
