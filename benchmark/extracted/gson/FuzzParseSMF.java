import java.io.*;
import com.google.gson.*;

public class FuzzParseSMF {

    public static void FuzzOne(String SMFData) {
        try {
            JsonParser.parseString(SMFData);
        } catch (JsonParseException expected) {
        }
    }
}
