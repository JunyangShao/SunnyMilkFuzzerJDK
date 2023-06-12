import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.slf4j.bridge.SLF4JBridgeHandler;
import java.util.logging.Logger;

public class BridgeFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        SLF4JBridgeHandler.install();
        Logger julLogger = Logger.getLogger("fuzzLogger");
        julLogger.fine(data.consumeRemainingAsString());
    }
}
