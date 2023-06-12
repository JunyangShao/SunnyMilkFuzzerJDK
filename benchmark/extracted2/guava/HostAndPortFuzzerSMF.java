import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.net.HostAndPort;
import java.lang.IllegalArgumentException;

public class HostAndPortFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            HostAndPort hap = HostAndPort.fromString(SMFData);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new FuzzerSecurityIssueLow("Undocumented Exception");
        }
    }
}
