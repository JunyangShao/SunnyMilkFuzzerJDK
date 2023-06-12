import org.springframework.http.ContentDisposition;
import org.springframework.util.Assert;

public class ContentDispositionFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String value = SMFData;
        try {
            ContentDisposition content = ContentDisposition.parse(value);
        } catch (IllegalArgumentException e) {
        }
    }
}
