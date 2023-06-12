import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.MatchingEntityTag;
import java.text.ParseException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpHeaderReaderFuzzerMain {

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
