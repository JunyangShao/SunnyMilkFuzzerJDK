import java.io.*;
import com.google.gson.*;

public class FuzzParseSMF {
  public static void FuzzOne(String data) {
    try {
      JsonParser.parseString(data);
    } catch (JsonParseException expected) { }
  }
}
