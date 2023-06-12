import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        final Logger logger = LoggerFactory.getLogger(LoggingFuzzer.class);
        logger.info(SMFData);
    }
}
