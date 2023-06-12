import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

public class XmlFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        Jsoup.parse(data.consumeRemainingAsString(), "", Parser.xmlParser());
    }
}
