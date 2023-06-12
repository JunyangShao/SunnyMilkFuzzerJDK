import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class FuzzReaderJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        TypeAdapter<JsonElement> adapter = new Gson().getAdapter(JsonElement.class);
        boolean lenient = data.consumeBoolean();
        JsonReader reader = new JsonReader(new StringReader(data.consumeRemainingAsString()));
        reader.setLenient(lenient);
        try {
            while (reader.peek() != JsonToken.END_DOCUMENT) {
                adapter.read(reader);
            }
        } catch (JsonParseException | IllegalStateException | NumberFormatException | IOException expected) {
        }
    }
}
