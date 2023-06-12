import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        DefaultParser parser = new DefaultParser();
        parser = DefaultParser.builder().setStripLeadingAndTrailingQuotes(data.consumeBoolean()).setAllowPartialMatching(data.consumeBoolean()).build();
        Options options = new Options();
        options.addOption("a", "aa", true, "");
        options.addOption("b", "bb", false, "");
        try {
            parser.parse(options, new String[] { data.consumeString(100), SMFData });
        } catch (ParseException | StringIndexOutOfBoundsException e) {
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
