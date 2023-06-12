import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.tool.LexerGrammar;
import org.antlr.v4.tool.Grammar;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GrammarFuzzerMain {

    public static void FuzzOne(String SMFData) {
        LexerGrammar lg;
        Grammar g;
        try {
            lg = new LexerGrammar(data.consumeString(1000));
            g = new Grammar(data.consumeString(1000));
        } catch (org.antlr.runtime.RecognitionException | UnsupportedOperationException e) {
            return;
        }
        LexerInterpreter lexEngine = lg.createLexerInterpreter(CharStreams.fromString(SMFData));
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = g.createParserInterpreter(tokens);
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
