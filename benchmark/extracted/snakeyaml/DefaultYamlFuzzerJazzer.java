import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;

public class DefaultYamlFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
            yaml.load(data.consumeRemainingAsString());
        } catch (YAMLException | IllegalArgumentException e) {
            return;
        }
    }
}
