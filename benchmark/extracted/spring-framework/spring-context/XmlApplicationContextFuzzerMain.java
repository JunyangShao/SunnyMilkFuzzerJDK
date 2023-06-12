import java.util.*;
import java.nio.file.Files;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import java.nio.file.Path;
import java.io.IOException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import java.nio.file.Paths;

public class XmlApplicationContextFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String path = data.consumeString(50);
        try {
            Path tempFile = Files.createTempFile("dummy", ".xml");
            Files.writeString(tempFile, SMFData);
            ApplicationContext ctx = new FileSystemXmlApplicationContext("file:" + tempFile.toAbsolutePath().toString());
            ctx.getApplicationName();
            ctx.getDisplayName();
            ctx.getParent();
            ctx.getResource(path);
            ctx.getClassLoader();
            Files.delete(tempFile);
        } catch (IOException | BeanDefinitionStoreException e) {
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
