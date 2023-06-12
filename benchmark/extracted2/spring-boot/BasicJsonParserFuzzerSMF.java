import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParseException;

public class BasicJsonParserFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String content = SMFData;
        BasicJsonParser parser = new BasicJsonParser();
        try {
            parser.parseList(content);
            parser.parseMap(content);
        } catch (JsonParseException e) {
        }
    }
}
