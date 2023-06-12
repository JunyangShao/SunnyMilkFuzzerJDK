import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import jakarta.mail.internet.HeaderTokenizer;
import jakarta.mail.internet.ParseException;

public class HeaderTokenizerFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        HeaderTokenizer ht = new HeaderTokenizer(data.consumeRemainingAsString());
        HeaderTokenizer.Token tok;
        try {
            while ((tok = ht.next()).getType() != HeaderTokenizer.Token.EOF) {
            }
        } catch (ParseException e) {
            return;
        }
    }
}
