import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import com.google.common.net.MediaType;
import java.lang.IllegalArgumentException;

public class MediaTypeFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String value = SMFData;
        try {
            MediaType.create(value, value);
            MediaType.parse(value).type();
            MediaType.create(value, value).withParameter(value, value);
        } catch (IllegalArgumentException e) {
        }
    }
}
