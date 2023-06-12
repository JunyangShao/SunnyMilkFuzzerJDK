import jakarta.mail.internet.HeaderTokenizer;
import jakarta.mail.internet.ParseException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HeaderTokenizerFuzzerMain {

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
