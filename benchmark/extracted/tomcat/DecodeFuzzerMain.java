import com.code_intelligence.jazzer.api.FuzzerSecurityIssueHigh;
import java.lang.StringBuilder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import org.apache.tomcat.util.buf.UEncoder;
import org.apache.tomcat.util.buf.UEncoder.SafeCharsSet;
import org.apache.tomcat.util.buf.UDecoder;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.catalina.util.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecodeFuzzerMain {

    static String[] encodings = { "US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16" };

    public static void FuzzOne(String SMFData) {
        int num = data.consumeInt(0, encodings.length - 1);
        String enc = encodings[num];
        String str = SMFData;
        try {
            String decodedData = UDecoder.URLDecode(str, Charset.forName(enc));
        } catch (IllegalArgumentException e) {
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
