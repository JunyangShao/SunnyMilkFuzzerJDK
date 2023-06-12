import org.apache.xmlbeans.impl.regex.RegularExpression;
import java.nio.file.Files;
import java.nio.file.Paths;

public class regExFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String regExStr = data.consumeString(100);
        String parse = SMFData;
        try {
            RegularExpression regex = new RegularExpression(regExStr);
            regex.matches(parse);
        } catch (Exception e) {
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
