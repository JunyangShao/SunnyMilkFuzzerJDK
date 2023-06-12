import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;
import java.io.StringReader;
import org.jdom2.JDOMException;
import java.io.IOException;
import org.jdom2.IllegalNameException;
import org.jdom2.IllegalTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SAXBuilderFuzzerMain {

    public static void FuzzOne(String SMFData) {
        SAXBuilder sax = new SAXBuilder();
        sax.setValidation(data.consumeBoolean());
        sax.setIgnoringElementContentWhitespace(data.consumeBoolean());
        sax.setIgnoringBoundaryWhitespace(data.consumeBoolean());
        StringReader xml_input = new StringReader(SMFData);
        try {
            Document doc = sax.build(xml_input);
        } catch (JDOMException | IOException | IllegalNameException | IllegalTargetException e) {
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
