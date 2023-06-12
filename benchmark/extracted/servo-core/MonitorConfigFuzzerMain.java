import com.netflix.servo.monitor.*;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.SortedTagList;
import com.netflix.servo.tag.TagList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MonitorConfigFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String key = data.consumeString(500);
        String value = data.consumeString(500);
        String name = SMFData;
        if (key.isEmpty() || value.isEmpty()) {
            return;
        }
        TagList tags1 = new BasicTagList(SortedTagList.builder().withTag(key, value).build());
        MonitorConfig m = new MonitorConfig.Builder(name).withTags(tags1).build();
        m.getName();
        m.getTags();
        m.hashCode();
        m.getPublishingPolicy();
        new BasicCounter(m);
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
