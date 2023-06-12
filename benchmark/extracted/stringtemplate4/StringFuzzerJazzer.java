import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.stringtemplate.v4.*;

public class StringFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        ST stringtemplate = new ST("Hello <name>");
        stringtemplate.add("name", data.consumeRemainingAsString());
        stringtemplate.render();
    }
}
