import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        final Logger logger = LoggerFactory.getLogger(LoggingFuzzer.class);
        logger.info(data.consumeRemainingAsString());
    }
}
