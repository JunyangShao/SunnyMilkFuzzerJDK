import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.slf4j.bridge.SLF4JBridgeHandler;
import java.util.logging.Logger;

public class BridgeFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        SLF4JBridgeHandler.install();
        Logger julLogger = Logger.getLogger("fuzzLogger");
        julLogger.fine(SMFData);
    }
}
