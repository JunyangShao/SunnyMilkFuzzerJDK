import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;

public class JSONParserFuzzerJazzer {
    private static long time_elapsed = 0;
    private static long start = 0;
    public static void fuzzerInitialize() {
      time_elapsed = 0;
      // Optional initialization to be run before the first call to fuzzerTestOneInput.
      start = System.nanoTime();
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        // long before = System.nanoTime();
        String input = data.consumeRemainingAsString();
        JSONParser jp = new JSONParser(JSONParser.MODE_PERMISSIVE);
        try {
            jp.parse(input);
        } catch (ParseException | NumberFormatException e) {
            return;
        }
        // long after = System.nanoTime();
        // time_elapsed += after - before;
        // if (after - start > 150e+10) {
        //     System.out.println(before - start);
        //     System.out.println(time_elapsed);
        //     throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
        // }
    }
}
