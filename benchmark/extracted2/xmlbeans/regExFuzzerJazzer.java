import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.apache.xmlbeans.impl.regex.RegularExpression;

public class regExFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String regExStr = data.consumeString(100);
        String parse = data.consumeRemainingAsString();
        try {
            RegularExpression regex = new RegularExpression(regExStr);
            regex.matches(parse);
        } catch (Exception e) {
        }
    }
}
