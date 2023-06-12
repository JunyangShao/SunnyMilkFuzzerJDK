import org.slf4j.bridge.SLF4JBridgeHandler;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BridgeFuzzerMain {

    public static void FuzzOne(String SMFData) {
        SLF4JBridgeHandler.install();
        Logger julLogger = Logger.getLogger("fuzzLogger");
        julLogger.fine(SMFData);
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
