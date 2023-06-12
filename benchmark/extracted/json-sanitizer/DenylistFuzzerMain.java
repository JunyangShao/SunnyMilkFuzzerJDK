import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import com.google.json.JsonSanitizer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DenylistFuzzerMain {

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

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
