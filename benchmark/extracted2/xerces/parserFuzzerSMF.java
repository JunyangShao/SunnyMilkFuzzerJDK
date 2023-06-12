import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;

public class parserFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String feature = data.consumeString(100);
        boolean state = data.consumeBoolean();
        String property = data.consumeString(100);
        String value = data.consumeString(100);
        if (feature.isEmpty() | property.isEmpty()) {
            return;
        }
        String content = SMFData;
        try {
            DOMParser parser = new DOMParser();
            parser.setFeature(feature, state);
            parser.getFeature(feature);
            parser.setProperty(property, value);
            String getValue = (String) parser.getProperty(property);
            parser.parse(content);
            Document document = parser.getDocument();
        } catch (Exception e) {
        }
    }
}
