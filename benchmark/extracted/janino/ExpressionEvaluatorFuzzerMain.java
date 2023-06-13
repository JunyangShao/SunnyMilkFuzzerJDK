import java.io.StringReader;
import java.io.IOException;
import java.lang.AssertionError;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.ScriptEvaluator;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class ExpressionEvaluatorFuzzerMain {

    public static void FuzzOne(String SMFData) {
        try {
            ExpressionEvaluator.guessParameterNames(new Scanner(null, new StringReader(SMFData)));
        } catch (IOException | CompileException | AssertionError e) {
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
