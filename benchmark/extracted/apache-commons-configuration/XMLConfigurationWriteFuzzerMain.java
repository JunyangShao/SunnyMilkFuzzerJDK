import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLConfigurationWriteFuzzerMain {

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
            xmlConfig.write(new StringWriter());
        } catch (ConfigurationException | IOException ignored) {
        } finally {
            tempFile.delete();
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
