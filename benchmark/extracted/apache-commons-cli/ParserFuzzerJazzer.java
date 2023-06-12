import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ParserFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        DefaultParser parser = new DefaultParser();
        parser = DefaultParser.builder().setStripLeadingAndTrailingQuotes(data.consumeBoolean()).setAllowPartialMatching(data.consumeBoolean()).build();
        Options options = new Options();
        options.addOption("a", "aa", true, "");
        options.addOption("b", "bb", false, "");
        try {
            parser.parse(options, new String[] { data.consumeString(100), data.consumeRemainingAsString() });
        } catch (ParseException | StringIndexOutOfBoundsException e) {
            return;
        }
    }
}
