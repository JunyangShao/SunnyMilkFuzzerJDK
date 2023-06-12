import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

public class XmlFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        Jsoup.parse(SMFData, "", Parser.xmlParser());
    }
}
