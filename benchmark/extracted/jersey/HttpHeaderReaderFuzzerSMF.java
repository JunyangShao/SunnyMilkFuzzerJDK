import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.MatchingEntityTag;
import java.text.ParseException;

public class HttpHeaderReaderFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String value = SMFData;
        try {
            HttpHeaderReader.readMatchingEntityTag(value);
            HttpHeaderReader.readQualityFactor(value);
            HttpHeaderReader.readDate(value);
            HttpHeaderReader.readAcceptToken(value);
            HttpHeaderReader.readAcceptLanguage(value);
            HttpHeaderReader.readStringList(value);
            HttpHeaderReader.readCookie(value);
            HttpHeaderReader.readCookies(value);
            HttpHeaderReader.readNewCookie(value);
        } catch (ParseException e) {
        }
    }
}
