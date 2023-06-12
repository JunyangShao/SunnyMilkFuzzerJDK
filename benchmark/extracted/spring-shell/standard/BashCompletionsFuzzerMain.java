import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.command.CommandCatalog;
import org.springframework.shell.standard.completion.BashCompletions;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BashCompletionsFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String command = SMFData;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        CommandCatalog commandCatalog = CommandCatalog.of();
        BashCompletions completions = new BashCompletions(context, commandCatalog);
        String bash = completions.generate(command);
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
