import jakarta.mail.internet.HeaderTokenizer;
import jakarta.mail.internet.ParseException;

public class HeaderTokenizerFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        HeaderTokenizer ht = new HeaderTokenizer(SMFData);
        HeaderTokenizer.Token tok;
        try {
            while ((tok = ht.next()).getType() != HeaderTokenizer.Token.EOF) {
            }
        } catch (ParseException e) {
            return;
        }
    }
}
