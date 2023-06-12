import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import org.apache.tomcat.util.http.parser.HttpParser;

public class HttpParserFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        HttpParser.unquote(input);
    }
}
