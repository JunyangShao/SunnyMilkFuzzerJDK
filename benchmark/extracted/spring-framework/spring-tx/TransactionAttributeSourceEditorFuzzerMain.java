import com.code_intelligence.jazzer.api.FuzzerSecurityIssueLow;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeEditor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSourceEditor;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TransactionAttributeSourceEditorFuzzerMain {

    public static void FuzzOne(String SMFData) {
        String source = SMFData;
        TransactionAttributeSourceEditor editor = new TransactionAttributeSourceEditor();
        try {
            editor.setAsText(source);
        } catch (IllegalArgumentException e) {
        }
        TransactionAttributeSource tas = (TransactionAttributeSource) editor.getValue();
        if (tas == null) {
            return;
        }
        TransactionAttribute ta = null;
        try {
            ta = tas.getTransactionAttribute(Object.class.getMethod("dummyMethod"), null);
        } catch (NoSuchMethodException e) {
        }
        if (ta == null) {
            return;
        }
        ta.getPropagationBehavior();
        ta.rollbackOn(new RuntimeException());
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
