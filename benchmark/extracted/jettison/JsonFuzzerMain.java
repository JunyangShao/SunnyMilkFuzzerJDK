import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class JsonFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            JSONObject obj = new JSONObject(SMFData);
        } catch (JSONException e) {
            return;
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
