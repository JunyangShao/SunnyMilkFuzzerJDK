import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class JSONParserFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String input = data.consumeRemainingAsString();
        JSONParser jp = new JSONParser(JSONParser.MODE_PERMISSIVE);
        try {
            jp.parse(input);
        } catch (ParseException | NumberFormatException e) {
            return;
        }
    }
}
