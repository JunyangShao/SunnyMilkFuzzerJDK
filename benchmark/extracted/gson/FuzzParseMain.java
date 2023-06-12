import java.io.*;
import com.google.gson.*;

public class FuzzParseMain {
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
      StringBuilder stringBuilder = new StringBuilder();

      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
          String line;
          while ((line = reader.readLine()) != null) {
              stringBuilder.append(line);
              stringBuilder.append(System.lineSeparator());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      return stringBuilder.toString();
  }

  public static void FuzzOne(String data) {
    try {
      JsonParser.parseString(data);
    } catch (JsonParseException expected) { }
  }
}
