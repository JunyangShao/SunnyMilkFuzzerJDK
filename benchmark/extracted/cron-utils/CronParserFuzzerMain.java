import com.cronutils.parser.CronParser;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.CronType;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CronParserFuzzerMain {

    public static void FuzzOne(String SMFData) {
        CronType[] cronTypes = { CronType.CRON4J, CronType.QUARTZ, CronType.UNIX, CronType.SPRING, CronType.SPRING53 };
        CronType cronType = data.pickValue(cronTypes);
        CronDefinition cronDef = CronDefinitionBuilder.instanceDefinitionFor(cronType);
        CronParser parser = new CronParser(cronDef);
        try {
            parser.parse(SMFData);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
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
