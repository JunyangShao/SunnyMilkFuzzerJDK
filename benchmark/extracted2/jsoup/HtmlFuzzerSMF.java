import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jsoup.Jsoup;

public class HtmlFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        Jsoup.parse(SMFData);
    }
}
