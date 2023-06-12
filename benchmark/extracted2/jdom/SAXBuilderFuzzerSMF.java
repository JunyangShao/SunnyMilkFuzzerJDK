import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import java.io.StringReader;
import org.jdom2.JDOMException;
import java.io.IOException;
import org.jdom2.IllegalNameException;
import org.jdom2.IllegalTargetException;

public class SAXBuilderFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        SAXBuilder sax = new SAXBuilder();
        sax.setValidation(data.consumeBoolean());
        sax.setIgnoringElementContentWhitespace(data.consumeBoolean());
        sax.setIgnoringBoundaryWhitespace(data.consumeBoolean());
        StringReader xml_input = new StringReader(SMFData);
        try {
            Document doc = sax.build(xml_input);
        } catch (JDOMException | IOException | IllegalNameException | IllegalTargetException e) {
            return;
        }
    }
}
