import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class FuzzReaderSMF {

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
}
