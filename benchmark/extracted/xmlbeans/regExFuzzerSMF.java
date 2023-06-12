import org.apache.xmlbeans.impl.regex.RegularExpression;

public class regExFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String regExStr = data.consumeString(100);
        String parse = SMFData;
        try {
            RegularExpression regex = new RegularExpression(regExStr);
            regex.matches(parse);
        } catch (Exception e) {
        }
    }
}
