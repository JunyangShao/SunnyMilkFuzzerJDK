import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JoranFuzzerMain {

    private final static Logger logger = LoggerFactory.getLogger(JoranFuzzer.class);

    private final static JoranConfigurator configurator = new JoranConfigurator();

    public static void fuzzerInitialize() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        configurator.setContext(lc);
        lc.reset();
    }

    public static void FuzzOne(String SMFData) {
        String content = data.consumeString(1000);
        if (content.contains("class=\"")) {
            return;
        }
        InputStream xmlcontent = new ByteArrayInputStream(content.getBytes());
        try {
            configurator.doConfigure(xmlcontent);
            logger.debug(SMFData);
        } catch (JoranException e) {
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
