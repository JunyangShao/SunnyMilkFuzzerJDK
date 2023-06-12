import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.stringtemplate.v4.*;

public class StringFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        ST stringtemplate = new ST("Hello <name>");
        stringtemplate.add("name", SMFData);
        stringtemplate.render();
    }
}
