import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class JSONParserFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        JSONParser jp = new JSONParser(JSONParser.MODE_PERMISSIVE);
        try {
            jp.parse(input);
        } catch (ParseException | NumberFormatException e) {
            return;
        }
    }
}
