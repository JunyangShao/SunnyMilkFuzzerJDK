import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

public class XMLConfigurationLoadFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        final File tempFile;
        try {
            tempFile = File.createTempFile("XMLConfiguration", ".xml");
        } catch (IOException ioe) {
            return;
        }
        final String absoluteFilepath = tempFile.getAbsolutePath();
        try {
            final FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(SMFData);
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
        } catch (ConfigurationException ignored) {
        } finally {
            tempFile.delete();
        }
    }
}
