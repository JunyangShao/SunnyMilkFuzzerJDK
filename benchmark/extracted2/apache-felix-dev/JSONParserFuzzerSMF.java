import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.felix.utils.json.JSONParser;

public class JSONParserFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        boolean switchConstructor = data.consumeBoolean();
        int switchEncoding = data.consumeInt(0, 2);
        String string = SMFData;
        try {
            JSONParser jSONParser;
            if (switchConstructor) {
                CharSequence charSeq = string;
                jSONParser = new JSONParser(charSeq);
            } else {
                String encoding = "UTF_8";
                switch(switchEncoding) {
                    case 0:
                        break;
                    case 1:
                        encoding = "UTF_16";
                        break;
                    case 2:
                        encoding = "UTF_32";
                        break;
                }
                InputStream inputStream = new ByteArrayInputStream(string.getBytes(encoding));
                jSONParser = new JSONParser(inputStream);
            }
            List<Object> res = jSONParser.getParsedList();
            Map<String, Object> parsed = jSONParser.getParsed();
        } catch (IllegalArgumentException | IOException e) {
        }
    }
}
