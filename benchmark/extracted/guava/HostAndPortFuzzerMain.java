import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.google.common.net.HostAndPort;
import java.lang.IllegalArgumentException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class HostAndPortFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            HostAndPort hap = HostAndPort.fromString(SMFData);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace(System.out);
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
