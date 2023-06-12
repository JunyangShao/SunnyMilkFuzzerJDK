import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FuzzReaderMain {

    public static void FuzzOne(String SMFData) {
        TypeAdapter<JsonElement> adapter = new Gson().getAdapter(JsonElement.class);
        boolean lenient = data.consumeBoolean();
        JsonReader reader = new JsonReader(new StringReader(SMFData));
        reader.setLenient(lenient);
        try {
            while (reader.peek() != JsonToken.END_DOCUMENT) {
                adapter.read(reader);
            }
        } catch (JsonParseException | IllegalStateException | NumberFormatException | IOException expected) {
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
