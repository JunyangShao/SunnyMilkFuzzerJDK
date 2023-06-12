import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.*;
import com.google.gson.*;

public class FuzzParseJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            JsonParser.parseString(data.consumeRemainingAsString());
        } catch (JsonParseException expected) {
        }
    }
}
