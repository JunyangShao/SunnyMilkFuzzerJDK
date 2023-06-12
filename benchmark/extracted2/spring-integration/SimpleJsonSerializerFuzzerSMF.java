import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.integration.json.SimpleJsonSerializer;

public class SimpleJsonSerializerFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String string = data.consumeString(250);
        String info = SMFData;
        String json = SimpleJsonSerializer.toJson(string, info);
    }
}
