import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.json.JsonSanitizer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class ValidJsonFuzzerMain {

    private static Gson gson = new Gson();

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        String output;
        try {
            output = JsonSanitizer.sanitize(input, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        try {
            Gson gson = new Gson();
            gson.fromJson(output, JsonElement.class);
        } catch (Exception e) {
            throw new FuzzerSecurityIssueLow("Output is invalid JSON", e);
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
