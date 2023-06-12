import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.converters.ConversionException;

class FuzzObj {

    private String string1;

    public FuzzObj(String s1) {
        this.string1 = s1;
    }
}

public class XmlFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        XStream xstream;
        switch(data.consumeInt(1, 3)) {
            case 1:
                xstream = new XStream(new DomDriver());
                break;
            case 2:
                xstream = new XStream(new StaxDriver());
                break;
            case 3:
                xstream = new XStream();
                break;
            default:
                return;
        }
        try {
            FuzzObj fo = (FuzzObj) xstream.fromXML(SMFData);
        } catch (StreamException | CannotResolveClassException | ConversionException e) {
            return;
        }
    }
}
