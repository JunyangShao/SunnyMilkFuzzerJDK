import org.osgi.framework.Version;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CoreVersionFuzzerMain {

    public static void FuzzOne(String SMFData) {
        Version v = null;
        try {
            v = new Version(SMFData);
        } catch (IllegalArgumentException ex) {
            return;
        }
        v.getMajor();
        v.getMinor();
        v.getMicro();
        v.getQualifier();
        v.toString();
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
