import org.springframework.security.acls.domain.AclFormattingUtils;

public class AclFormattingUtilsFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String origin;
        String bits;
        int mask;
        char code;
        char off;
        mask = data.consumeInt();
        code = data.consumeChar();
        origin = data.consumeString(250);
        bits = SMFData;
        String printBinary1;
        String printBinary2;
        String mergePatterns;
        String demergePatterns;
        try {
            printBinary1 = AclFormattingUtils.printBinary(mask);
            printBinary2 = AclFormattingUtils.printBinary(mask, code);
            mergePatterns = AclFormattingUtils.mergePatterns(origin, bits);
            demergePatterns = AclFormattingUtils.demergePatterns(origin, bits);
        } catch (IllegalArgumentException iae) {
        }
    }
}
