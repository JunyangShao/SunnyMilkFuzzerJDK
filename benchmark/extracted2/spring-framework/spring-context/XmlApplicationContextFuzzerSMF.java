import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.util.*;
import java.nio.file.Files;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import java.nio.file.Path;
import java.io.IOException;
import org.springframework.beans.factory.BeanDefinitionStoreException;

public class XmlApplicationContextFuzzerSMF {

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
}
