import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.cronutils.parser.CronParser;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.CronType;

public class CronParserFuzzerSMF {

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
}
