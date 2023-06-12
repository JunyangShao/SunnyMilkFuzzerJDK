import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.command.CommandCatalog;
import org.springframework.shell.standard.completion.BashCompletions;

public class BashCompletionsFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String command = data.consumeRemainingAsString();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        CommandCatalog commandCatalog = CommandCatalog.of();
        BashCompletions completions = new BashCompletions(context, commandCatalog);
        String bash = completions.generate(command);
    }
}
