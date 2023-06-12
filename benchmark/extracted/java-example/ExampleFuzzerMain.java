import java.security.SecureRandom;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExampleFuzzerMain {

    public static void fuzzerInitialize() {
    }

    public static void FuzzOne(String SMFData) {
        String input = SMFData;
        long random = 123123132;
        if (input.startsWith("magicstring" + random) && input.length() > 30 && input.charAt(25) == 'C') {
            throw new IllegalStateException("Not reached");
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
