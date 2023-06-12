import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.StringReader;
import java.io.IOException;
import java.lang.AssertionError;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.ScriptEvaluator;

public class ExpressionEvaluatorFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            ExpressionEvaluator.guessParameterNames(new Scanner(null, new StringReader(data.consumeRemainingAsString())));
        } catch (IOException | CompileException | AssertionError e) {
            return;
        }
    }
}
