import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

public class XMLConfigurationWriteFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        final File tempFile;
        try {
            tempFile = File.createTempFile("XMLConfiguration", ".xml");
        } catch (IOException ioe) {
            return;
        }
        final String absoluteFilepath = tempFile.getAbsolutePath();
        try {
            final FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(data.consumeRemainingAsString());
            fileWriter.close();
        } catch (IOException ioe) {
            tempFile.delete();
            return;
        }
        final XMLConfiguration xmlConfig = new XMLConfiguration();
        xmlConfig.setLogger(null);
        final FileHandler fileHandler = new FileHandler(xmlConfig);
        fileHandler.setPath(absoluteFilepath);
        try {
            fileHandler.load();
            xmlConfig.write(new StringWriter());
        } catch (ConfigurationException | IOException ignored) {
        } finally {
            tempFile.delete();
        }
    }
}
