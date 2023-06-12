import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.IllegalArgumentException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.analysis.custom.CustomAnalyzer;

public class CustomAnalyzerFuzzerJazzer {

    static String[] tokenizerArray = { "standard", "simplePattern", "classic", "whitespace", "uax29UrlEmail", "pathHierarchy", "wikipedia", "nGram", "edgeNGram", "thai", "pattern", "simplePatternSplit", "letter", "keyword" };

    static String[] charFilterArray = { "htmlStrip", "cjkWidth", "mapping", "patternReplace", "persian" };

    static String[] tokenFilterArray = { "apostrophe", "arabicNormalization", "arabicStem", "asciiFolding", "bengaliNormalization", "bengaliStem", "brazilianStem", "bulgarianStem", "capitalization", "cjkBigram", "cjkWidth", "classic", "codepointCount", "commonGrams", "commonGramsQuery", "concatenateGraph", "czechStem", "dateRecognizer", "decimalDigit", "delimitedPayload", "delimitedTermFrequency", "dictionaryCompoundWord", "edgeNGram", "elision", "englishMinimalStem", "englishPossessive", "fingerprint", "finnishLightStem", "fixBrokenOffsets", "fixedShingle", "flattenGraph", "frenchLightStem", "frenchMinimalStem", "galicianMinimalStem", "galicianStem", "germanLightStem", "germanMinimalStem", "germanNormalization", "germanStem", "greekLowercase", "greekStem", "hindiNormalization", "hindiStem", "hungarianLightStem", "hunspellStem", "hyphenatedWords", "hyphenationCompoundWord", "indicNormalization", "indonesianStem", "irishLowercase", "italianLightStem", "kStem", "keepWord", "keywordMarker", "keywordRepeat", "latvianStem", "length", "limitTokenCount", "limitTokenOffset", "limitTokenPosition", "lowercase", "minHash", "nGram", "norwegianLightStem", "norwegianMinimalStem", "numericPayload", "patternCaptureGroup", "patternReplace", "persianNormalization", "porterStem", "portugueseLightStem", "portugueseMinimalStem", "portugueseStem", "protectedTerm", "removeDuplicates", "reverseString", "russianLightStem", "scandinavianFolding", "scandinavianNormalization", "serbianNormalization", "shingle", "snowballPorter", "soraniNormalization", "soraniStem", "spanishLightStem", "spanishMinimalStem", "stemmerOverride", "stop", "swedishLightStem", "synonym", "synonymGraph", "tokenOffsetPayload", "trim", "truncate", "turkishLowercase", "type", "typeAsPayload", "typeAsSynonym", "uppercase", "wordDelimiter", "wordDelimiterGraph" };

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        List<String> selectedTokenizers = data.pickValues(tokenizerArray, data.consumeInt(0, tokenizerArray.length));
        List<String> selectedCharFilters = data.pickValues(charFilterArray, data.consumeInt(0, charFilterArray.length));
        List<String> selectedTokenFilters = data.pickValues(tokenFilterArray, data.consumeInt(0, tokenFilterArray.length));
        String str0 = data.consumeString(100);
        String str1 = data.consumeRemainingAsString();
        CustomAnalyzer.Builder cb = CustomAnalyzer.builder();
        try {
            cb.withTokenizer(data.pickValue(tokenizerArray));
            for (String cf : selectedCharFilters) {
                cb.addCharFilter(cf);
            }
            for (String tf : selectedTokenFilters) {
                cb.addTokenFilter(tf);
            }
            Analyzer analyzer = cb.build();
            List<String> result = analyze(str0, str1, analyzer);
        } catch (IOException e) {
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e) {
        }
    }

    public static List<String> analyze(String field, String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(field, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }
}
