import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ParserConfiguration;
import static com.github.javaparser.Providers.provider;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;

public class parseFuzzerJazzer {
    private static long time_elapsed = 0;
    private static long start = 0;
    public static void fuzzerInitialize() {
      time_elapsed = 0;
      // Optional initialization to be run before the first call to fuzzerTestOneInput.
      start = System.nanoTime();
    }

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        // long before = System.nanoTime();
        String datastring = data.consumeRemainingAsString();
        InputStream datastream = new ByteArrayInputStream(datastring.getBytes());
        try {
            ParserConfiguration configuration = new ParserConfiguration();
            final ParseResult<CompilationUnit> result = new JavaParser(configuration).parse(COMPILATION_UNIT, provider(datastream, configuration.getCharacterEncoding()));
        } catch (Exception e) {
        }
        // long after = System.nanoTime();
        // time_elapsed += after - before;
        // if (after - start > 150e+10) {
        //     System.out.println(before - start);
        //     System.out.println(time_elapsed);
        //     throw new FuzzerSecurityIssueMedium("mustNeverBeCalled has been called");
        // }
    }
}
