import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;

public class SecureYamlFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try {
            LoaderOptions options = new LoaderOptions();
            options.setAllowDuplicateKeys(false);
            options.setMaxAliasesForCollections(5);
            options.setAllowRecursiveKeys(false);
            options.setNestingDepthLimit(3);
            options.setCodePointLimit(5 * 1024);
            Yaml yaml = new Yaml(new SafeConstructor(options));
            yaml.load(data.consumeRemainingAsString());
        } catch (YAMLException | IllegalArgumentException e) {
            return;
        }
    }
}
