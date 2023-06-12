import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ParserConfiguration;
import static com.github.javaparser.Providers.provider;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class parseFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        String datastring = SMFData;
        InputStream datastream = new ByteArrayInputStream(datastring.getBytes());
        try {
            ParserConfiguration configuration = new ParserConfiguration();
            final ParseResult<CompilationUnit> result = new JavaParser(configuration).parse(COMPILATION_UNIT, provider(datastream, configuration.getCharacterEncoding()));
        } catch (Exception e) {
            return;
        }
    }
}
