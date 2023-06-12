import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.osgi.framework.Version;

public class CoreVersionFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider fuzzedDataProvider) {
        Version v = null;
        try {
            v = new Version(fuzzedDataProvider.consumeRemainingAsString());
        } catch (IllegalArgumentException ex) {
            return;
        }
        v.getMajor();
        v.getMinor();
        v.getMicro();
        v.getQualifier();
        v.toString();
    }
}
