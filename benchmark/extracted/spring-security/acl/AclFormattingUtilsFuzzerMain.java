import org.springframework.security.acls.domain.AclFormattingUtils;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AclFormattingUtilsFuzzerMain {

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
