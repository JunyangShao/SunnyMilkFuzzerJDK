import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.core5.http.ContentType;

public class FileBodyWriteToFuzzerSMF {

    private static final int FILENAME_MAX_LENGTH = 255;

    public static void FuzzOne(String SMFData) {
        final ContentType contentType = data.pickValue(contentTypes);
        final String filename = data.consumeString(FILENAME_MAX_LENGTH);
        final String fileContent = SMFData;
        final File tempFile;
        try {
            tempFile = File.createTempFile("FileBody", ".bin");
        } catch (IOException ioe) {
            return;
        }
        try {
            final FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write(fileContent);
            fileWriter.close();
        } catch (IOException ioe) {
            tempFile.delete();
            return;
        }
        try {
            final FileBody fileBody = new FileBody(tempFile, contentType, filename);
            fileBody.writeTo(new ByteArrayOutputStream());
        } catch (IOException ignored) {
        } finally {
            tempFile.delete();
        }
    }

    private static final ContentType[] contentTypes = { ContentType.APPLICATION_ATOM_XML, ContentType.APPLICATION_FORM_URLENCODED, ContentType.APPLICATION_JSON, ContentType.APPLICATION_NDJSON, ContentType.APPLICATION_OCTET_STREAM, ContentType.APPLICATION_PDF, ContentType.APPLICATION_PROBLEM_JSON, ContentType.APPLICATION_PROBLEM_XML, ContentType.APPLICATION_RSS_XML, ContentType.APPLICATION_SOAP_XML, ContentType.APPLICATION_SVG_XML, ContentType.APPLICATION_XHTML_XML, ContentType.APPLICATION_XML, ContentType.DEFAULT_BINARY, ContentType.DEFAULT_TEXT, ContentType.IMAGE_BMP, ContentType.IMAGE_GIF, ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG, ContentType.IMAGE_SVG, ContentType.IMAGE_TIFF, ContentType.IMAGE_WEBP, ContentType.MULTIPART_FORM_DATA, ContentType.MULTIPART_MIXED, ContentType.MULTIPART_RELATED, ContentType.TEXT_EVENT_STREAM, ContentType.TEXT_HTML, ContentType.TEXT_MARKDOWN, ContentType.TEXT_PLAIN, ContentType.TEXT_XML, ContentType.WILDCARD };
}
