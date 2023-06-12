import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.springframework.security.acls.domain.AclFormattingUtils;

public class AclFormattingUtilsFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String origin;
        String bits;
        int mask;
        char code;
        char off;
        mask = data.consumeInt();
        code = data.consumeChar();
        origin = data.consumeString(250);
        bits = data.consumeRemainingAsString();
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
