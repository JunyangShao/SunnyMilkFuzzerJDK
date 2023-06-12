import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.shell.command.CommandOption;
import org.springframework.shell.command.CommandParser;
import java.util.Arrays;
import java.util.List;
import java.lang.Character;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommandParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String longName1 = data.consumeString(200);
        String longName2 = data.consumeString(5);
        String shortName1 = data.consumeString(100);
        String argument = data.consumeString(100);
        String description = SMFData;
        char[] c = shortName1.toCharArray();
        Character[] shortNameArray = new Character[c.length];
        int i = 0;
        for (char value : c) {
            shortNameArray[i++] = Character.valueOf(value);
        }
        CommandParser parser;
        ConversionService conversionService = new DefaultConversionService();
        parser = CommandParser.of(conversionService);
        CommandOption option1 = CommandOption.of(new String[] { longName1 }, shortNameArray, description);
        CommandOption option2 = CommandOption.of(new String[] { longName2 }, new Character[0], description);
        List<CommandOption> options = Arrays.asList(option1, option2);
        String[] args = new String[] { "--" + longName1, argument };
        CommandParser.CommandParserResults results = parser.parse(options, args);
        if (results.errors().size() > 0) {
            throw new FuzzerSecurityIssueMedium("Something went wrong while parsing");
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
