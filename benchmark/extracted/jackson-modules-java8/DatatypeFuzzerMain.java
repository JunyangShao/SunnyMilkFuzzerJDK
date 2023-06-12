import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import com.fasterxml.jackson.annotation.JsonMerge;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatatypeFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String content = data.consumeString(100);
        String merge = SMFData;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        try {
            Dummy dummy1 = mapper.readValue(content, Dummy.class);
            Dummy dummy2 = mapper.readValue(mapper.writeValueAsString(dummy1), Dummy.class);
            if (!dummy1.equals(dummy2)) {
                throw new FuzzerSecurityIssueLow("Different values " + dummy1.debug() + " != " + dummy2.debug());
            }
            mapper.readerForUpdating(merge);
        } catch (IOException e) {
        }
    }

    public static class Dummy {

        public Optional<String> value = Optional.empty();

        public Optional<Boolean> bool = Optional.empty();

        @JsonMerge
        public Optional<List<String>> list = Optional.empty();

        public OptionalInt optint = OptionalInt.empty();

        public OptionalLong optlong = OptionalLong.empty();

        public OptionalDouble optdouble = OptionalDouble.empty();

        public String debug() {
            return "%s %s %s %s %s %s".formatted(this.value, this.bool, this.list, this.optint, this.optlong, this.optdouble);
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
