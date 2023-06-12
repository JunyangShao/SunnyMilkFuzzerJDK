import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.tool.LexerGrammar;
import org.antlr.v4.tool.Grammar;

public class GrammarFuzzerSMF {

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
}
