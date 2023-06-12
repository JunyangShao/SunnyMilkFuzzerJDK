import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.*;
import com.google.gson.*;

public class FuzzStreamParserSMF {

    public static void FuzzOne(String SMFData) {
        try {
            JsonStreamParser parser = new JsonStreamParser(SMFData);
            JsonElement element;
            while (parser.hasNext() == true) {
                element = parser.next();
            }
        } catch (JsonParseException expected) {
        }
    }
}
