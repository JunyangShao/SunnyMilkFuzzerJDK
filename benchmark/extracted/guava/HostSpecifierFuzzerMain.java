import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.net.HostSpecifier;
import java.text.ParseException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class HostSpecifierFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            HostSpecifier hs = HostSpecifier.from(SMFData);
            if (!HostSpecifier.isValid(hs.toString())) {
                throw new FuzzerSecurityIssueLow("toString() generated a poor host specifier");
            }
            hs.hashCode();
        } catch (ParseException e) {
        } catch (Exception e) {
            throw new FuzzerSecurityIssueLow("Undocumented Exception");
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
