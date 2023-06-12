import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.net.HostSpecifier;
import java.text.ParseException;

public class HostSpecifierFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            HostSpecifier hs = HostSpecifier.from(SMFData);
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
