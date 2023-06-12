import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.automaton.TooComplexToDeterminizeException;
import java.nio.file.Paths;

public class IndexSearchFuzzerMain {

    static Analyzer analyzer = null;

    static Path indexPath = null;

    static Directory directory = null;

    static IndexWriterConfig config = null;

    static IndexWriter iwriter = null;

    static Document doc = null;

    static DirectoryReader ireader = null;

    static IndexSearcher isearcher = null;

    public static void fuzzerInitialize() {
        try {
            analyzer = new StandardAnalyzer();
            indexPath = Files.createTempDirectory("tempIndex");
            directory = FSDirectory.open(indexPath);
            config = new IndexWriterConfig(analyzer);
            iwriter = new IndexWriter(directory, config);
            doc = new Document();
            ireader = DirectoryReader.open(directory);
            isearcher = new IndexSearcher(ireader);
        } catch (IOException e) {
        }
    }

    public static void FuzzOne(String SMFData) {
        boolean commit = data.consumeBoolean();
        int n = data.consumeInt();
        String text = data.consumeString(500);
        String field = data.consumeString(500);
        String queryStr = SMFData;
        try {
            doc.add(new Field(field, text, TextField.TYPE_STORED));
            iwriter.addDocument(doc);
            iwriter.commit();
            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = isearcher.search(query, n).scoreDocs;
        } catch (IOException | ParseException e) {
        } catch (NullPointerException | IllegalArgumentException | TooComplexToDeterminizeException e) {
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
