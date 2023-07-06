import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;

public class XmlFuzzerJazzer {
    private static long time_elapsed = 0;
    private static long start = 0;
    public static void fuzzerInitialize() {
      time_elapsed = 0;
      // Optional initialization to be run before the first call to fuzzerTestOneInput.
      start = System.nanoTime();
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        // long before = System.nanoTime();
        Jsoup.parse(data.consumeRemainingAsString(), "", Parser.xmlParser());
        // long after = System.nanoTime();
        // time_elapsed += after - before;
        // if (after - start > 150e+10) {
        //     System.out.println(before - start);
        //     System.out.println(time_elapsed);
        //     throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
        // }
    }
}
