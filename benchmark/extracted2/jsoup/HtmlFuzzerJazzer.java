import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jsoup.Jsoup;

public class HtmlFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        Jsoup.parse(data.consumeRemainingAsString());
    }
}
