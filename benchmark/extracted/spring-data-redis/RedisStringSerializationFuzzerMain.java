import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RedisStringSerializationFuzzerMain {

    public static void FuzzOne(String SMFData) {
        int value = data.consumeInt(0, 2);
        String original_input = SMFData;
        String deserialized_input = null;
        byte[] serialized_input = null;
        String format = null;
        switch(value) {
            case 0:
                format = "US_ASCII";
                serialized_input = StringRedisSerializer.US_ASCII.serialize(original_input);
                deserialized_input = StringRedisSerializer.US_ASCII.deserialize(serialized_input);
            case 1:
                format = "ISO_8859_1";
                serialized_input = StringRedisSerializer.ISO_8859_1.serialize(original_input);
                deserialized_input = StringRedisSerializer.ISO_8859_1.deserialize(serialized_input);
            case 2:
                format = "UTF_8";
                serialized_input = StringRedisSerializer.UTF_8.serialize(original_input);
                deserialized_input = StringRedisSerializer.UTF_8.deserialize(serialized_input);
        }
        if (!deserialized_input.equals(original_input)) {
            throw new IllegalStateException("Failed to recover\n" + original_input + "\ndeserialized with " + format + ", got:\n" + deserialized_input + "\n\n");
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
