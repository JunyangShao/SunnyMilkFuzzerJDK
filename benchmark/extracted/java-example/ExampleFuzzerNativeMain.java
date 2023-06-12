import java.nio.file.Files;
import java.nio.file.Paths;

public class ExampleFuzzerNativeMain {

    static {
        System.loadLibrary("native");
    }

    public static void FuzzOne(String SMFData) {
        int val = data.consumeInt();
        String stringData = SMFData;
        if (val == 17759716 && stringData.length() > 10 && stringData.contains("jazzer")) {
            parse(stringData);
        }
    }

    private static native boolean parse(String bytes);

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
