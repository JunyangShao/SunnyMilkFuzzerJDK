import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.net.HostSpecifier;
import java.text.ParseException;

public class HostSpecifierFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            HostSpecifier hs = HostSpecifier.from(data.consumeRemainingAsString());
            if (!HostSpecifier.isValid(hs.toString())) {
                throw new FuzzerSecurityIssueLow("toString() generated a poor host specifier");
            }
            hs.hashCode();
        } catch (ParseException e) {
        } catch (Exception e) {
            throw new FuzzerSecurityIssueLow("Undocumented Exception");
        }
    }
}
