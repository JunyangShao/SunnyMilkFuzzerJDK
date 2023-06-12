import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.*;
import com.google.gson.*;

public class FuzzStreamParserJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            JsonStreamParser parser = new JsonStreamParser(data.consumeRemainingAsString());
            JsonElement element;
            while (parser.hasNext() == true) {
                element = parser.next();
            }
        } catch (JsonParseException expected) {
        }
    }
}
