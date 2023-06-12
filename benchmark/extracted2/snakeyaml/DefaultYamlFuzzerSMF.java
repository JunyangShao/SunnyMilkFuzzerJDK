import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;

public class DefaultYamlFuzzerSMF {

    public static void FuzzOne(String SMFData) {
        try {
            Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
            yaml.load(SMFData);
        } catch (YAMLException | IllegalArgumentException e) {
            return;
        }
    }
}
