import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SecureYamlFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            LoaderOptions options = new LoaderOptions();
            options.setAllowDuplicateKeys(false);
            options.setMaxAliasesForCollections(5);
            options.setAllowRecursiveKeys(false);
            options.setNestingDepthLimit(3);
            options.setCodePointLimit(5 * 1024);
            Yaml yaml = new Yaml(new SafeConstructor(options));
            yaml.load(SMFData);
        } catch (YAMLException | IllegalArgumentException e) {
            return;
        }
    }

    public static void main(String[] args) {
        File folder = new File("./fuzzerOut");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileAsString(file.getAbsolutePath());
                    FuzzOne(content);
                }
            }
        } else {
            System.out.println("The directory is empty or it does not exist.");
        }
    }

    private static String readFileAsString(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            return "";
        }
    }
}
