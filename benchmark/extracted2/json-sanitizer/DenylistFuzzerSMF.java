import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import com.google.json.JsonSanitizer;

public class DenylistFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        String output;
        try {
            output = JsonSanitizer.sanitize(input, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        assert !output.contains("</script") : new FuzzerSecurityIssueHigh("Output contains </script");
        assert !output.contains("]]>") : new FuzzerSecurityIssueHigh("Output contains ]]>");
        assert !output.contains("<script") : new FuzzerSecurityIssueMedium("Output contains <script");
        assert !output.contains("<!--") : new FuzzerSecurityIssueMedium("Output contains <!--");
    }
}
