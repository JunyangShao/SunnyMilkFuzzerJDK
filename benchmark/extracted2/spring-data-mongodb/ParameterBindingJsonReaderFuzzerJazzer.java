import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.bson.json.JsonParseException;
import org.springframework.data.mongodb.util.json.ParameterBindingJsonReader;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;

public class ParameterBindingJsonReaderFuzzerJazzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        try (ParameterBindingJsonReader reader = new ParameterBindingJsonReader(data.consumeRemainingAsString())) {
            reader.doReadDecimal128();
            reader.getMark();
            reader.readBsonType();
        } catch (SpelParseException | SpelEvaluationException | IllegalStateException | IllegalArgumentException | JsonParseException e) {
        }
    }
}
