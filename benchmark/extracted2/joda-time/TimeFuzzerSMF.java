import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.joda.time.*;

public class TimeFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            DateTimeZone.forID(SMFData);
        } catch (IllegalArgumentException e) {
        }
        return;
    }
}
